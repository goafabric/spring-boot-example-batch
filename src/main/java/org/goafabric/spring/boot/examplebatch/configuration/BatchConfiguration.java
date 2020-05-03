package org.goafabric.spring.boot.examplebatch.configuration;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.goafabric.spring.boot.examplebatch.logic.PersonItemProcessor;
import org.goafabric.spring.boot.examplebatch.logic.GenericFileItemReader;
import org.goafabric.spring.boot.examplebatch.logic.GenericJdbcItemWriter;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public FlatFileItemReader<Person> personReader() {
        return new GenericFileItemReader<>(Person.class,
                "sample-data.csv", new String[]{"firstName", "lastName"});
    }

    @Bean
    public ItemProcessor<Person , Person> personProcessor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> personWriter() {
        final String sql = "INSERT INTO people (id, first_name, last_name) VALUES (:id, :firstName, :lastName)";
        return new GenericJdbcItemWriter<>(sql);
    }
}
