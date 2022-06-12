/*
package org.goafabric.spring.boot.examplebatch.logic;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "spring.batch.rate")
public class JobLauncherScheduler {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job personCatalogJob;

    @Autowired
    private Job toyCatalogJob;

    @Scheduled(fixedRateString = "${spring.batch.rate}")
    public void schedule() throws Exception {
        jobLauncher.run(personCatalogJob, new JobParameters());
        jobLauncher.run(toyCatalogJob, new JobParameters());
    }
}
*/