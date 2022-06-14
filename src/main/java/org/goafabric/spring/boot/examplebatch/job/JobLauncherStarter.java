package org.goafabric.spring.boot.examplebatch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class JobLauncherStarter implements CommandLineRunner {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job personCatalogJob;

    @Autowired
    private Job toyCatalogJob;

    @Value("${spring.batch.scheduler.enabled}")
    private Boolean schedulerEnabled;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        if (!schedulerEnabled) {
            start(new JobParametersBuilder().addString("catalogVersion", "10").toJobParameters());
            log.info("launched manually and wait 5 seconds");
            Thread.currentThread().join(5000);
            SpringApplication.exit(applicationContext, () -> 0);
        }
    }

    @Scheduled(fixedRateString = "${spring.batch.scheduler.rate}")
    public void schedule() throws Exception {
        if (schedulerEnabled) {
            start(new JobParameters());
        }
    }
    public void start(JobParameters jobParameters) throws Exception {
        jobLauncher.run(personCatalogJob, jobParameters);
        jobLauncher.run(toyCatalogJob, jobParameters);
    }

}

