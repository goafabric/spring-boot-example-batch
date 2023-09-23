package org.goafabric.spring.boot.examplebatch.job.toycatalog;

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
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RegisterReflectionForBinding(Toy.class)
public class ToyImportConfiguration {

    @Bean
    public Job toyJob(@Qualifier("toyStep") Step toyStep, JobCompletionListener listener, JobRepository jobRepository) {
        return new JobBuilder("toyJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(toyStep).end()
                .build();
    }

    @Bean(name = "toyStep")
    public Step toyStep(ItemReader<Toy> toyItemReader,
                           ItemProcessor<Toy, Toy> toyItemProcessor,
                           ItemWriter<Toy> toyItemWriter,
                           JobRepository jobRepository,
                           PlatformTransactionManager ptm) {
        return new StepBuilder("toyStep", jobRepository)
                .<Toy, Toy>chunk(2, ptm)
                .reader(toyItemReader)
                .processor(toyItemProcessor)
                .writer(toyItemWriter)
                //.faultTolerant().skip(IncorrectTokenCountException.class)
                .build();
    }

    @Bean
    public ItemReader<Toy> toyItemReader() {
        return new FlatFileItemReaderBuilder<Toy>()
                .name("toyItemReader")
                .resource(new ClassPathResource("catalogdata/toy-catalog.csv"))
                .delimited()
                .names(new String[]{"id", "toyName", "price"})
                .fieldSetMapper(new RecordFieldSetMapper(Toy.class))
                .build();

    }

    @Bean
    @StepScope
    public ItemProcessor<Toy, Toy> toyItemProcessor() {
        return toy -> new Toy(
                toy.id(),
                toy.toyName().toLowerCase(),
                toy.price()
        );
    }

    @Bean
    public ItemWriter<Toy> toyItemWriter(DataSource dataSource) {
        final String sql = "INSERT INTO masterdata.toy_catalog (id, toy_name, price) VALUES (:id, :toyName, :price)";
        return new ToyItemWriter(dataSource, sql);
    }
}
