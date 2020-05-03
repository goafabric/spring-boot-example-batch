package org.goafabric.spring.boot.examplebatch.configuration;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.goafabric.spring.boot.examplebatch.logic.JobCompletionNotificationListener;
import org.goafabric.spring.boot.examplebatch.logic.PersonItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
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
    public Job personJob(JobCompletionNotificationListener listener, Step personStep) {
        return jobBuilderFactory.get("personJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(personStep)
                .end()
                .build();
    }

    @Bean
    public Step personStep(ItemReader<Person> personReader,
                           ItemProcessor<Person , Person> personProcessor,
                           JdbcBatchItemWriter<Person> personWriter) {
        return stepBuilderFactory.get("personStep")
                .<Person, Person> chunk(10)//defines how much data is written at a time
                .reader(personReader)
                .processor(personProcessor)
                .writer(personWriter)
                .build();
    }
}
