package org.goafabric.spring.boot.examplebatch.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements CommandLineRunner {
    @Autowired
    private JobLauncherStarter jobLauncherStarter;

    @Override
    public void run(String... args) throws Exception {
        jobLauncherStarter.start();
    }
}
