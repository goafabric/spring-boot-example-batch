package org.goafabric.spring.boot.examplebatch.person;

/*
 * Copyright 2021-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import lombok.SneakyThrows;
import org.goafabric.spring.boot.examplebatch.job.JobCompletionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * @author Michael Minella
 */
@Configuration(proxyBeanMethods = false) //needed for spring-native
public class PersonConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job personCatalogJob(@Qualifier("personStep") Step personStep, JobCompletionListener listener) {
        return jobBuilderFactory.get("personCatalogJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(personStep).end()
                .build();
    }

    @Bean(name = "personStep") //name needed for spring-native with Qualifier above + Injection of Reader / Writer .. only works like this, not via bean method call
    public Step personStep(ItemReader<Person> personItemReader,
                            ItemProcessor<Person, Person> personItemProcessor,
                            ItemWriter<Person> personItemWriter) {
        return this.stepBuilderFactory.get("personStep")
                .<Person, Person>chunk(2)
                .reader(personItemReader)
                .processor(personItemProcessor)
                .writer(personItemWriter)
                .build();
    }


    @Bean
    @SneakyThrows
    public ItemReader<Person> personItemReader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("catalogdata/person-catalog.csv"))
                .delimited()
                .names(new String[]{"id", "firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Person.class);
                }}).build();
        //return new GenericCsvItemReader<>(Person.class, new ClassPathResource("catalogdata/person-catalog.csv"), new String[]{"id", "firstName", "lastName"});
    }

    @Bean
    @StepScope
    public ItemProcessor<Person, Person> personItemProcessor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> personItemWriter(DataSource dataSource) {
        final String sql = "INSERT INTO catalogs.person_catalog (id, catalog_version, first_name, last_name) VALUES (:id, :catalogVersion, :firstName, :lastName)";
        return new PersonItemWriter(dataSource, sql);
    }

}