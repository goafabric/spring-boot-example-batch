package org.goafabric.spring.boot.examplebatch.configuration;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.goafabric.spring.boot.examplebatch.dto.ToyCatalog;
import org.goafabric.spring.boot.examplebatch.logic.GenericFileItemReader;
import org.goafabric.spring.boot.examplebatch.logic.GenericItemProcessor;
import org.goafabric.spring.boot.examplebatch.logic.GenericJdbcItemWriter;
import org.goafabric.spring.boot.examplebatch.logic.JobCompletionNotificationListener;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class ToyCatalogBatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Person> toyCatalogReader() {
        return new GenericFileItemReader<>(ToyCatalog.class,
                "sample-data.csv", new String[]{"toyName", "price"});
    }

    @Bean
    public ItemProcessor<Person , Person> toyCatalogProcessor() {
        return new GenericItemProcessor();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Person> toyCatalogWriter() {
        final String sql = "INSERT INTO toy_catalog (id, catalog_version, toy_name, price) VALUES (:id, :catalogVersion, :toyName, :price)";
        return new GenericJdbcItemWriter<>(sql);
    }

    @Bean
    public Job toyCatalogJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("toyCatalogJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(toyCatalogStep()).end()
                .build();
    }

    @Bean
    public Step toyCatalogStep() {
        return stepBuilderFactory.get("toyCatalogStep")
                .<Person, Person> chunk(10)//defines how much data is written at a time
                .reader(toyCatalogReader()).processor(toyCatalogProcessor()).writer(toyCatalogWriter())
                .build();
    }
}
