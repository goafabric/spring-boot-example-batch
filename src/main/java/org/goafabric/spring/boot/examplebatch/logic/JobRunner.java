package org.goafabric.spring.boot.examplebatch.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements CommandLineRunner {
    @Autowired
    private JobLauncherStarter jobLauncherStarter;

    @Value("${spring.batch.rate:-1}")
    private String batchRate;

    @Override
    public void run(String... args) throws Exception {
        jobLauncherStarter.start();
    }
}
