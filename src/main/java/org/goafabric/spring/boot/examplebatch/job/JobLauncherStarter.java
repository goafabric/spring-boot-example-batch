package org.goafabric.spring.boot.examplebatch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobLauncherStarter implements CommandLineRunner {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job personCatalogJob;

    @Autowired
    private Job toyCatalogJob;

    @Value("${spring.batch.scheduler.enabled}")
    private Boolean schedulerEnabled;

    @Override
    public void run(String... args) throws Exception {
        if (!schedulerEnabled) {
            start();
            log.info("launched manually and wait 5 seconds");
            Thread.currentThread().join(5000);
        }
    }

    @Scheduled(fixedRateString = "${spring.batch.rate}")
    public void schedule() throws Exception {
        if (schedulerEnabled) {
            start();
        }
    }
    public void start() throws Exception {
        final JobParameters jobParameters = new JobParametersBuilder()
                .addString("catalogVersion", "10").toJobParameters();

        jobLauncher.run(personCatalogJob, jobParameters);
        jobLauncher.run(toyCatalogJob, jobParameters);
    }

}

