package org.goafabric.spring.boot.examplebatch.configuration;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.goafabric.spring.boot.examplebatch.logic.JobCompletionNotificationListener;
import org.goafabric.spring.boot.examplebatch.logic.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemReader<Person> reader,
                      PersonItemProcessor processor,
                      JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("step1")
                .<Person, Person> chunk(10)//defines how much data is written at a time
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
