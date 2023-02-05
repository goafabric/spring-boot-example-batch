package org.goafabric.spring.boot.examplebatch.job.person;


import com.github.javafaker.Faker;
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
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RegisterReflectionForBinding(Person.class)
public class PersonAnonymizerConfiguration {
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

    @Bean(name = "personStep")
    //name needed for spring-native with Qualifier above + Injection of Reader / Writer .. only works like this, not via bean method call
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
    public ItemReader<Person> personItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Person>()
                .name("personItemReader")
                .dataSource(dataSource)
                .beanRowMapper(Person.class)
                .sql("SELECT * FROM masterdata.person")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Person, Person> personItemProcessor(Faker faker) {
        return new PersonItemProcessor(faker);
    }

    @Bean
    public JdbcBatchItemWriter<Person> personItemWriter(DataSource dataSource) {
        final String sql = "UPDATE masterdata.person SET first_name = :firstName, last_name = :lastName WHERE id = :id";
        return new PersonItemWriter(dataSource, sql);
    }

}
