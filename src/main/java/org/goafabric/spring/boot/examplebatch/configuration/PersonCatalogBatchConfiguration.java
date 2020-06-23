package org.goafabric.spring.boot.examplebatch.configuration;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.goafabric.spring.boot.examplebatch.logic.JobCompletionListener;
import org.goafabric.spring.boot.examplebatch.logic.generic.GenericItemProcessor;
import org.goafabric.spring.boot.examplebatch.logic.generic.GenericCsvItemReader;
import org.goafabric.spring.boot.examplebatch.logic.generic.GenericJdbcItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonCatalogBatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job personCatalogJob(JobCompletionListener listener) {
        return jobBuilderFactory.get("personJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(personCatalogStep()).end()
                .build();
    }

    @Bean
    public Step personCatalogStep() {
        return stepBuilderFactory.get("personStep")
                .<Person, Person> chunk(10)//defines how much data is written at a time
                .reader(personCatalogReader()).processor(personCatalogProcessor()).writer(personCatalogWriter())
                .build();
    }

    @Bean
    public ItemReader<Person> personCatalogReader() {
        return new GenericCsvItemReader<>(Person.class,
                "src/main/deploy/catalogdata/person-catalog.csv", new String[]{"firstName", "lastName"});
    }

    @Bean
    @StepScope //needed for JobParams
    public ItemProcessor<Person , Person> personCatalogProcessor() {
        return new GenericItemProcessor();
    }

    @Bean
    public ItemWriter<Person> personCatalogWriter() {
        final String sql = "INSERT INTO catalogs.person_catalog (id, catalog_version, first_name, last_name) VALUES (:id, :catalogVersion, :firstName, :lastName)";
        return new GenericJdbcItemWriter<>(sql);
    }
}
