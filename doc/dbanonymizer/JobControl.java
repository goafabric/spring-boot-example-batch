package org.goafabric.anonymizer.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobControl {
    @Autowired
    private List<AnonymizerJob> jobs;

    public void run() {
        for (AnonymizerJob job : jobs) {
            job.run();
        }
        //todo: write processed jobs to a table and skip on next run, so that during a crash we won't start all over again
    }
}
