package org.goafabric.spring.boot.examplebatch.logic;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JobLauncherStarter {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job personCatalogJob;

    @Autowired
    private Job toyCatalogJob;

    @Value("{spring.batch.rate}")
    private String batchRate;

    public void start() throws Exception {
        final JobParameters jobParameters = new JobParametersBuilder()
                .addString("catalogVersion", "10").toJobParameters();

        jobLauncher.run(personCatalogJob, jobParameters);
        jobLauncher.run(toyCatalogJob, jobParameters);
    }

}
