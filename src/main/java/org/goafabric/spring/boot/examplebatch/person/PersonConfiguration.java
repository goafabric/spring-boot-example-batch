package org.goafabric.spring.boot.examplebatch.person;


import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.goafabric.spring.boot.examplebatch.job.JobCompletionListener;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RegisterReflectionForBinding(Person.class)
public class PersonConfiguration {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager ptm;


    @Bean
    public Job personJob(@Qualifier("personStep") Step personStep, JobCompletionListener listener) {
        return new JobBuilder("personJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(personStep).end()
                .build();
    }

    @Bean(name = "personStep") //name needed for spring-native with Qualifier above + Injection of Reader / Writer .. only works like this, not via bean method call
    public Step personStep(ItemReader<Person> personItemReader,
                            ItemProcessor<Person, Person> personItemProcessor,
                            ItemWriter<Person> personItemWriter) {
        return new StepBuilder("personStep", jobRepository)
                .<Person, Person>chunk(2, ptm)
                .reader(personItemReader)
                .processor(personItemProcessor)
                .writer(personItemWriter)
                .build();
    }


    @Bean
    public ItemReader<Person> personItemReader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("catalogdata/person-catalog.csv"))
                .delimited()
                .names(new String[]{"id", "firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }}).build();
    }


    @Bean
    @StepScope
    public ItemProcessor<Person, Person> personItemProcessor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> personItemWriter(DataSource dataSource) {
        final String sql = "UPDATE catalogs.person SET first_name = :firstName, last_name = :lastName WHERE id = :id" ;
        //final String sql = "INSERT INTO catalogs.person_catalog (id, catalog_version, first_name, last_name) VALUES (:id, :catalogVersion, :firstName, :lastName)";
        return new PersonItemWriter(dataSource, sql);
    }

}