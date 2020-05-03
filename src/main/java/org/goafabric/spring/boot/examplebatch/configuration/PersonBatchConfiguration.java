package org.goafabric.spring.boot.examplebatch.configuration;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.goafabric.spring.boot.examplebatch.logic.JobCompletionNotificationListener;
import org.goafabric.spring.boot.examplebatch.logic.PersonItemProcessor;
import org.goafabric.spring.boot.examplebatch.logic.GenericFileItemReader;
import org.goafabric.spring.boot.examplebatch.logic.GenericJdbcItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class PersonBatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new GenericFileItemReader<>(Person.class,
                "sample-data.csv", new String[]{"firstName", "lastName"});
    }

    @Bean
    public ItemProcessor<Person , Person> processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer() {
        final String sql = "INSERT INTO people (id, first_name, last_name) VALUES (:id, :firstName, :lastName)";
        return new GenericJdbcItemWriter<>(sql);
    }

    @Bean
    public Job personJob(JobCompletionNotificationListener listener, Step personStep) {
        return jobBuilderFactory.get("personJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(personStep).end()
                .build();
    }

    @Bean
    public Step personStep() {
        return stepBuilderFactory.get("personStep")
                .<Person, Person> chunk(10)//defines how much data is written at a time
                .reader(reader()).processor(processor()).writer(writer())
                .build();
    }
}
