package org.goafabric.spring.boot.examplebatch.job.configuration;

import org.goafabric.spring.boot.examplebatch.Application;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "mongodb", matchIfMissing = false)
@EnableAutoConfiguration(exclude = {JdbcRepositoriesAutoConfiguration.class}) //we cannot disable DataSourceConfiguration because Spring Batch needs it for In Memory Metadata
@EnableMongoRepositories(considerNestedRepositories = true, basePackageClasses = Application.class)
public class MongoConfiguration {
}