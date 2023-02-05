package org.goafabric.spring.boot.examplebatch.job.person;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;

@Slf4j
public class PersonItemWriter extends JdbcBatchItemWriter<Person> {
    private final DataSource dataSource;

    private final String sql;

    public PersonItemWriter(DataSource dataSource, String sql) {
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

    public void write(final Chunk<? extends Person> chunk) throws Exception {
        chunk.forEach(ch -> log.info("Writing item: {}", ch));
        super.write(chunk);
    }
  
}
