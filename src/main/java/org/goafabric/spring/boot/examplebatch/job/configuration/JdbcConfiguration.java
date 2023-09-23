package org.goafabric.spring.boot.examplebatch.job.configuration;

import org.goafabric.spring.boot.examplebatch.Application;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "jdbc", matchIfMissing = true)
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration.class})
@EnableJdbcRepositories(considerNestedRepositories = true, basePackageClasses = Application.class)
public class JdbcConfiguration {
}
