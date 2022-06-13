package org.goafabric.spring.boot.examplebatch.toy;

import org.goafabric.spring.boot.examplebatch.domain.Toy;
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
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class ToyConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job toyCatalogJob(@Qualifier("toyCatalogStep") Step toyCatalogStep, JobCompletionListener listener) {
        return jobBuilderFactory.get("toyCatalogJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(toyCatalogStep).end()
                .build();
    }

    @Bean(name = "toyCatalogStep")
    public Step toyCatalogStep(ItemReader<Toy> toyItemReader,
                           ItemProcessor<Toy, Toy> toyItemProcessor,
                           ItemWriter<Toy> toyItemWriter) {
        return this.stepBuilderFactory.get("toyStep")
                .<Toy, Toy>chunk(2)
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
        //return new GenericCsvItemReader<>(Toy.class, new ClassPathResource("catalogdata/toy-catalog.csv"), new String[]{"id", "toyName", "price"});
    }

    @Bean
    @StepScope
    public ItemProcessor<Toy, Toy> toyItemProcessor() {
        return new ToyItemProcessor();
    }

    @Bean
    public ItemWriter<Toy> toyItemWriter(DataSource dataSource) {
        final String sql = "INSERT INTO catalogs.toy_catalog (id, catalog_version, toy_name, price) VALUES (:id, :catalogVersion, :toyName, :price)";
        return new ToyItemWriter(dataSource, sql);
    }
}
