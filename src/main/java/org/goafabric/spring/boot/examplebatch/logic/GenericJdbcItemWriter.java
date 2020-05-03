package org.goafabric.spring.boot.examplebatch.logic;

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

    @Autowired
    private VersionHandler versionHandler;

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

        versionHandler.ensureCatalogVersion(sql);
        super.afterPropertiesSet();
    }
}
