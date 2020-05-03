package org.goafabric.spring.boot.examplebatch.logic;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class PersonItemWriter<T> extends JdbcBatchItemWriter<T> {
    private final String sql;

    @Autowired
    private DataSource dataSource;

    public PersonItemWriter(String sql) {
        this.sql = sql;
    }

    @Override
    public void afterPropertiesSet() {
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        this.setDataSource(dataSource);
        this.setSql(sql);
        super.afterPropertiesSet();
    }
}
