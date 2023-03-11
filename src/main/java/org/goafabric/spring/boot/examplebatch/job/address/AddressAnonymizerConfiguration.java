package org.goafabric.spring.boot.examplebatch.job.address;


import org.goafabric.spring.boot.examplebatch.domain.Address;
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
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RegisterReflectionForBinding(Address.class)
public class AddressAnonymizerConfiguration {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager ptm;


    @Bean
    public Job addressJob(@Qualifier("addressStep") Step addressStep, JobCompletionListener listener) {
        return new JobBuilder("addressJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener).flow(addressStep).end()
                .build();
    }

    @Bean(name = "addressStep") //name needed for spring-native with Qualifier above + Injection of Reader / Writer .. only works like this, not via bean method call
    public Step addressStep(ItemReader<Address> addressItemReader,
                            ItemProcessor<Address, Address> addressItemProcessor,
                            ItemWriter<Address> addressItemWriter) {
        return new StepBuilder("addressStep", jobRepository)
                .<Address, Address>chunk(2, ptm)
                .reader(addressItemReader)
                .processor(addressItemProcessor)
                .writer(addressItemWriter)
                .build();
    }

    @Bean
    public   ItemReader<Address> addressItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Address>()
                .name("addressItemReader")
                .dataSource(dataSource)
                .beanRowMapper(Address.class)
                .sql("SELECT * FROM masterdata.address")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Address, Address> addressItemProcessor() {
        return new ItemProcessor<Address, Address>() {

            @Override
            public Address process(Address item) {
                return new Address(
                        item.id(),
                        "fake-city",
                        "fake-street"
                );
            }
        };
    }

    @Bean
    public JdbcBatchItemWriter<Address> addressItemWriter(DataSource dataSource) {
        final String sql = "UPDATE masterdata.address SET street = :street, city = :city WHERE id = :id" ;
        return new JdbcBatchItemWriterBuilder<Address>()
                .dataSource(dataSource).sql(sql).beanMapped()
                .build();
    }

}