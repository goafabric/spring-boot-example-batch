package org.goafabric.spring.boot.examplebatch.configuration;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.goafabric.spring.boot.examplebatch.logic.JobCompletionListener;
import org.goafabric.spring.boot.examplebatch.logic.GenericItemProcessor;
import org.goafabric.spring.boot.examplebatch.logic.GenericFileItemReader;
import org.goafabric.spring.boot.examplebatch.logic.GenericJdbcItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class PersonCatalotBatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Person> personReader() {
        return new GenericFileItemReader<>(Person.class,
                "person-catalog.csv", new String[]{"firstName", "lastName"});
    }

    @Bean
    public ItemProcessor<Person , Person> personProcessor() {
        return new GenericItemProcessor();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Person> personWriter() {
        final String sql = "INSERT INTO people (id, catalog_version, first_name, last_name) VALUES (:id, :catalogVersion, :firstName, :lastName)";
        return new GenericJdbcItemWriter<>(sql);
    }

    @Bean
    public Job personJob(JobCompletionListener listener) {
        return jobBuilderFactory.get("personJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(personStep()).end()
                .build();
    }

    @Bean
    public Step personStep() {
        return stepBuilderFactory.get("personStep")
                .<Person, Person> chunk(10)//defines how much data is written at a time
                .reader(personReader()).processor(personProcessor()).writer(personWriter())
                .build();
    }
}
