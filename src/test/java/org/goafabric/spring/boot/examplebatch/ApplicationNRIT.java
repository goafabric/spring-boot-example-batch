package org.goafabric.spring.boot.examplebatch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationNRIT {

    @Test
    public void test() {
        SpringApplication.run(Application.class, "");
    }
}