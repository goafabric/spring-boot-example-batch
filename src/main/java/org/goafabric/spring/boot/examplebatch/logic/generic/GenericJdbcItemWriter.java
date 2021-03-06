package org.goafabric.spring.boot.examplebatch.logic.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
public class GenericJdbcItemWriter<T> extends JdbcBatchItemWriter<T> {
    @Autowired
    private DataSource dataSource;

    private final String sql;

    public GenericJdbcItemWriter(String sql) {
        this.sql = sql;
    }

    //Generate efficient JDBC writer with batch update, with the given Pojo
    @Override
    public void afterPropertiesSet() {
        this.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());
        this.setDataSource(dataSource);
        this.setSql(sql);
        super.afterPropertiesSet();
    }

    public void write(List<? extends T> items) throws Exception {
        items.forEach(item -> log.info("Writing item: {}", item));
        super.write(items);
    }
}
