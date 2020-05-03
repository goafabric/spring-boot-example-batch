package org.goafabric.spring.boot.examplebatch.logic;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

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

    public void write(List<? extends T> items) throws Exception {
        items.forEach(item -> log.info("Writing item: {}", item));
        super.write(items);
    }

    //Generate efficient JDBC writer with batch update, with the given Pojo
    @Override
    public void afterPropertiesSet() {
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        this.setDataSource(dataSource);
        this.setSql(sql);
        super.afterPropertiesSet();

        doVersionhandling(sql);
    }

    @Value("#{jobParameters[catalogVersion]}")
    private String catalogVersion;

    private void doVersionhandling(String sql) {
        final String tableName = sql.split("INSERT INTO ")[1].split(" ")[0];
        Assert.notNull(tableName, "tablename should not be null");
        final int count = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + tableName + " WHERE catalog_version = :catalogVersion",
                new MapSqlParameterSource().addValue("catalogVersion", catalogVersion), Integer.class);
        Assert.isTrue(count > 0 , "Catalog already imported with version: " + catalogVersion);
    }
}
