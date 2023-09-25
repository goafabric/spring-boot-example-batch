package org.goafabric.spring.boot.examplebatch.job.toycatalog;

import org.goafabric.spring.boot.examplebatch.job.JobCompletionListener;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RegisterReflectionForBinding(Toy.class)
public class ToyImportConfiguration {

    @Bean
    public Job toyJob(Step toyStep, JobCompletionListener listener, JobRepository jobRepository) {
        return new JobBuilder("toyJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(toyStep).end()
                .build();
    }

    @Bean(name = "toyStep")
    public Step toyStep(ItemReader<Toy> toyItemReader,
                           ItemWriter<Toy> toyItemWriter,
                           JobRepository jobRepository,
                           PlatformTransactionManager ptm) {
        return new StepBuilder("toyStep", jobRepository)
                .<Toy, Toy>chunk(2, ptm)
                .reader(toyItemReader)
                .writer(toyItemWriter)
                .build();
    }

    @Bean
    public ItemReader<Toy> toyItemReader() {
        return new FlatFileItemReaderBuilder<Toy>()
                .name("toyItemReader")
                .resource(new ClassPathResource("catalogdata/toy-catalog.csv"))
                .delimited()
                .names("toyName", "price")
                .fieldSetMapper(fieldSet -> new Toy(fieldSet.readString("toyName"), fieldSet.readString("price")))
                .build();

    }

    @Bean
    public ItemWriter<Toy> toyItemWriter(ToyRepository repository) {
        return chunk -> repository.saveAll(chunk.getItems());
    }

}
