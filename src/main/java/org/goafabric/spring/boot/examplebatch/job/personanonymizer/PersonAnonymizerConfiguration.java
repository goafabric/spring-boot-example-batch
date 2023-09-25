package org.goafabric.spring.boot.examplebatch.job.personanonymizer;


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
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.UUID;

@Configuration
@RegisterReflectionForBinding(Person.class)
public class PersonAnonymizerConfiguration {

    @Bean
    public Job personJob(Step personStep, JobCompletionListener listener, JobRepository jobRepository) {
        return new JobBuilder("personJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(personStep).end()
                .build();
    }

    @Bean(name = "personStep")
    //name needed for spring-native with Qualifier above + Injection of Reader / Writer .. only works like this, not via bean method call
    public Step personStep(ItemReader<Person> personItemReader,
                           ItemProcessor<Person, Person> personItemProcessor,
                           ItemWriter<Person> personItemWriter,
                           JobRepository jobRepository,
                           PlatformTransactionManager ptm) {
        return new StepBuilder("personStep", jobRepository)
                .<Person, Person>chunk(2, ptm)
                .reader(personItemReader)
                .processor(personItemProcessor)
                .writer(personItemWriter)
                .build();
    }

    @Bean
    public ItemReader<Person> personItemReader(PersonRepository repository) {
        demoDataImport(repository);
        return new IteratorItemReader<>(repository.findAllBy().iterator());
    }

    @Bean
    @StepScope
    public ItemProcessor<Person, Person> personItemProcessor() {
        return person -> new Person(person.id(), person.version(), "anonymized firstname", "anonymized lastname");
    }

    @Bean
    public ItemWriter<Person> personItemWriter(PersonRepository repository) {
        return chunk -> repository.saveAll(chunk.getItems());
    }

    public void demoDataImport(PersonRepository repository) {
        repository.save(new Person(UUID.randomUUID().toString(), null, "Homer", "Simpson"));
        repository.save(new Person(UUID.randomUUID().toString(), null, "Bart", "Simpson"));
        repository.save(new Person(UUID.randomUUID().toString(), null, "Monty", "Burns"));
    }

}
