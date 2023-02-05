package org.goafabric.anonymizer.job;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@Transactional
public class PersonJob implements AnonymizerJob {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Faker faker;

    final String tableName = "public.person";

    public void run() {
        log.info("anonymizing {}", tableName);
        read();
    }

    private void read() {
        jdbcTemplate.query(String.format("select * from %s",tableName), resultSet -> {
            do {
                log.info("processing {}", resultSet.getString("id"));
                write(resultSet.getString("id"));
            }  while (resultSet.next());
        });
    }

    private void write(String id) {
        final String fields = "first_name = ?, last_name = ?";
        jdbcTemplate.update(String.format("UPDATE %s SET %s WHERE id = ?", tableName, fields),
                faker.name().firstName(), faker.name().lastName(), id);
    }
}
