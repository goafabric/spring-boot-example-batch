package org.goafabric.spring.boot.examplebatch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobLauncherStarter implements CommandLineRunner {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job personJob;

    @Autowired
    private Job toyCatalogJob;

    @Value("${spring.batch.rate:-1}")
    private String batchRate;

    @Override
    public void run(String... args) throws Exception {
        if (batchRate.equals("-1")) {
            start();
            log.info("launched manually and wait 5 seconds");
            Thread.currentThread().join(5000);
        }
    }

    public void start() throws Exception {
        final JobParameters jobParameters = new JobParametersBuilder()
                .addString("catalogVersion", "10").toJobParameters();

        jobLauncher.run(personJob, jobParameters);
        jobLauncher.run(toyCatalogJob, jobParameters);
    }

}

