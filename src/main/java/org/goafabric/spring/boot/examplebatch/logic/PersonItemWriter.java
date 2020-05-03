package org.goafabric.spring.boot.examplebatch.logic;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class PersonItemWriter extends JdbcBatchItemWriter<Person> {
    private final String sql = "INSERT INTO people (id, first_name, last_name) VALUES (:id, :firstName, :lastName)";

    @Autowired
    private DataSource dataSource;

    @Override
    public void afterPropertiesSet() {
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        this.setDataSource(dataSource);
        this.setSql(sql);
        super.afterPropertiesSet();
    }
}
