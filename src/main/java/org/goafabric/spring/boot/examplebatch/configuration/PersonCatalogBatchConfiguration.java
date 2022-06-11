package org.goafabric.spring.boot.examplebatch.configuration;

import lombok.SneakyThrows;
import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.goafabric.spring.boot.examplebatch.logic.JobCompletionListener;
import org.goafabric.spring.boot.examplebatch.logic.generic.GenericCsvItemReader;
import org.goafabric.spring.boot.examplebatch.logic.generic.GenericItemProcessor;
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
import org.springframework.core.io.ClassPathResource;

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

    //Instead of using the Generic Wrapper classes, one could also use Spring builders directly: https://spring.io/guides/gs/batch-processing/
    //Benefit of the Generic classes is, that they come in handy if multiple Catalogs will be imported in that way, as we have more generall code and less bloated Configuration Classes

    @Bean
    @SneakyThrows
    public ItemReader<Person> personCatalogReader() {
        return new GenericCsvItemReader<>(Person.class,
                new ClassPathResource("catalogdata/person-catalog.csv"), new String[]{"id", "firstName", "lastName"});
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
