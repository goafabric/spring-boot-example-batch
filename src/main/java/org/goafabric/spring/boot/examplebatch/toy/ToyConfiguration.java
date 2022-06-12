
package org.goafabric.spring.boot.examplebatch.toy;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class ToyConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job toyCatalogJob(JobCompletionListener listener) {
        return jobBuilderFactory.get("toyCatalogJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(toyCatalogStep()).end()
                .build();
    }

    @Bean
    public Step toyCatalogStep() {
        return stepBuilderFactory.get("toyCatalogStep")
                .<Toy, Toy> chunk(10)//defines how much data is written at a time
                .reader(toyItemReader()).processor(toyItemProcessor()).writer(toyItemWriter())
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
    @StepScope //needed for JobParams
    public ItemProcessor<Toy, Toy> toyItemProcessor() {
        return new ToyItemProcessor();
    }

    @Autowired
    private DataSource dataSource;
    @Bean
    @StepScope //needed for JobParams
    public ItemWriter<Toy> toyItemWriter() {
        final String sql = "INSERT INTO catalogs.toy_catalog (id, catalog_version, toy_name, price) VALUES (:id, :catalogVersion, :toyName, :price)";
        return new ToyItemWriter(dataSource, sql);
    }
}
