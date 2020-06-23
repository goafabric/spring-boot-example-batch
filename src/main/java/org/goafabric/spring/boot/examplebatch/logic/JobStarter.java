package org.goafabric.spring.boot.examplebatch.logic;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JobStarter {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job personJob;

    @Autowired
    private Job toyCatalogJob;

    public void start() throws Exception {
        final JobParameters jobParameters = new JobParametersBuilder()
                .addString("catalogVersion", "10").toJobParameters();

        jobLauncher.run(personJob, jobParameters);
        jobLauncher.run(toyCatalogJob, jobParameters);
    }
}
