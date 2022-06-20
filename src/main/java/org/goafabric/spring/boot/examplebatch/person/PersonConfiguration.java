package org.goafabric.spring.boot.examplebatch.person;



import lombok.SneakyThrows;
import org.goafabric.spring.boot.examplebatch.domain.Person;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;

import javax.sql.DataSource;

@TypeHint(types = {Person.class}, access = { TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.PUBLIC_METHODS })
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
        return new PersonItemReader(new ClassPathResource("catalogdata/person-catalog.csv"), new String[]{"id", "firstName", "lastName"});
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