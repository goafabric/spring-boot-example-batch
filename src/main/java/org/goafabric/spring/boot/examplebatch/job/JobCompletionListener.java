package org.goafabric.spring.boot.examplebatch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobCompletionListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {

        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job finished: {}", jobExecution.getJobInstance().getJobName());
        } else {
            log.error("There was a problem with jor job: {}", jobExecution.getJobInstance().getJobName());
        }
    }

}
