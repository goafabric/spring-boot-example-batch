package org.goafabric.spring.boot.examplebatch.job.toy;

import org.goafabric.spring.boot.examplebatch.domain.Toy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;

public class ToyItemWriter extends JdbcBatchItemWriter<Toy> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final DataSource dataSource;

    private final String sql;

    public ToyItemWriter(DataSource dataSource, String sql) {
        this.dataSource = dataSource;
        this.sql = sql;
    }

    @Override
    public void afterPropertiesSet() {
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        this.setDataSource(dataSource);
        this.setSql(sql);
        super.afterPropertiesSet();
    }

    public void write(final Chunk<? extends Toy> chunk) throws Exception {
        chunk.forEach(ch -> log.info("Writing item: {}", ch));
        super.write(chunk);
    }

}
