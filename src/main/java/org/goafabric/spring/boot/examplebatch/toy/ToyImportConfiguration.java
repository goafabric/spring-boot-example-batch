package org.goafabric.spring.boot.examplebatch.toy;

import org.goafabric.spring.boot.examplebatch.domain.Toy;
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
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RegisterReflectionForBinding(Toy.class)
public class ToyImportConfiguration {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager ptm;

    @Bean
    public Job toyJob(@Qualifier("toyStep") Step toyStep, JobCompletionListener listener) {
        return new JobBuilder("toyJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(toyStep).end()
                .build();
    }

    @Bean(name = "toyStep")
    public Step toyStep(ItemReader<Toy> toyItemReader,
                           ItemProcessor<Toy, Toy> toyItemProcessor,
                           ItemWriter<Toy> toyItemWriter) {
        return new StepBuilder("toyStep", jobRepository)
                .<Toy, Toy>chunk(2, ptm)
                .reader(toyItemReader)
                .processor(toyItemProcessor)
                .writer(toyItemWriter)
                .build();
    }

    @Bean
    public ItemReader<Toy> toyItemReader() {
        return new FlatFileItemReaderBuilder<Toy>()
                .name("toyItemReader")
                .resource(new ClassPathResource("catalogdata/toy-catalog.csv"))
                .delimited()
                .names(new String[]{"id", "toyName", "price"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Toy>() {{
                    setTargetType(Toy.class);
                }}).build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Toy, Toy> toyItemProcessor() {
        return new ToyItemProcessor();
    }

    @Bean
    public ItemWriter<Toy> toyItemWriter(DataSource dataSource) {
        final String sql = "INSERT INTO masterdata.toy_catalog (id, catalog_version, toy_name, price) VALUES (:id, :catalogVersion, :toyName, :price)";
        return new ToyItemWriter(dataSource, sql);
    }
}
