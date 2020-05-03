package org.goafabric.spring.boot.examplebatch.logic;

import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class GenericJdbcItemWriter<T> extends JdbcBatchItemWriter<T> {
    private final String sql;

    @Autowired
    private DataSource dataSource;

    public GenericJdbcItemWriter(String sql) {
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
