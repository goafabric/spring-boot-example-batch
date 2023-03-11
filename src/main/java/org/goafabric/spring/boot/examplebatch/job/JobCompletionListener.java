package org.goafabric.spring.boot.examplebatch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener implements JobExecutionListener {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterJob(JobExecution jobExecution) {

        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job finished: {}", jobExecution.getJobInstance().getJobName());
        } else {
            log.error("There was a problem with jor job: {}", jobExecution.getJobInstance().getJobName());
        }
    }

}
