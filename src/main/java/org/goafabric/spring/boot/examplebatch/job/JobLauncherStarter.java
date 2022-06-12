package org.goafabric.spring.boot.examplebatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobLauncherStarter implements CommandLineRunner {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job personCatalogJob;

    @Autowired
    private Job toyCatalogJob;

    @Value("${spring.batch.rate:-1}")
    private String batchRate;

    @Override
    public void run(String... args) throws Exception {
        if (batchRate.equals("-1")) {
            start();
            //Thread.currentThread().join(1000);
        }
    }

    public void start() throws Exception {
        final JobParameters jobParameters = new JobParametersBuilder()
                .addString("catalogVersion", "10").toJobParameters();

        jobLauncher.run(personCatalogJob, jobParameters);
        jobLauncher.run(toyCatalogJob, jobParameters);
    }

}
