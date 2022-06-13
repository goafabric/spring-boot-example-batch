package org.goafabric.spring.boot.examplebatch.toy;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Toy;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
public class ToyItemWriter extends JdbcBatchItemWriter<Toy> {
    private final DataSource dataSource;

    private final String sql;

    public ToyItemWriter(DataSource dataSource, String sql) {
        this.dataSource = dataSource;
        this.sql = sql;
    }

    //Generate efficient JDBC writer with batch update, with the given Pojo
    @Override
    public void afterPropertiesSet() {
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        this.setDataSource(dataSource);
        this.setSql(sql);
        super.afterPropertiesSet();
    }

    public void write(List<? extends Toy> items) throws Exception {
        items.forEach(item -> log.info("Writing item: {}", item));
        super.write(items);
    }
}
