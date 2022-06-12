
package org.goafabric.spring.boot.examplebatch.configuration;

import org.goafabric.spring.boot.examplebatch.dto.Toy;
import org.goafabric.spring.boot.examplebatch.logic.JobCompletionListener;
import org.goafabric.spring.boot.examplebatch.logic.generic.GenericItemProcessor;
import org.goafabric.spring.boot.examplebatch.logic.generic.GenericJdbcItemWriter;
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

@Configuration
public class ToyCatalogBatchConfiguration {
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
                .reader(toyCatalogReader()).processor(toyCatalogProcessor()).writer(toyCatalogWriter())
                .build();
    }

    @Bean
    public ItemReader<Toy> toyCatalogReader() {
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
    public ItemProcessor<Toy, Toy> toyCatalogProcessor() {
        return new GenericItemProcessor();
    }

    @Bean
    @StepScope //needed for JobParams
    public ItemWriter<Toy> toyCatalogWriter() {
        final String sql = "INSERT INTO catalogs.toy_catalog (id, catalog_version, toy_name, price) VALUES (:id, :catalogVersion, :toyName, :price)";
        return new GenericJdbcItemWriter<>(sql);
    }
}
