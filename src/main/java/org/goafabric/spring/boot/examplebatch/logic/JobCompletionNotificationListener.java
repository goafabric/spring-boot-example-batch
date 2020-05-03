package org.goafabric.spring.boot.examplebatch.logic;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            /*
            jdbcTemplate.query("SELECT id, first_name, last_name FROM people",
                    (rs, row) -> new Person(
                            rs.getString("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"))
            ).forEach(person -> log.info("Found <" + person + "> in the database."));

             */
        }
    }

}
