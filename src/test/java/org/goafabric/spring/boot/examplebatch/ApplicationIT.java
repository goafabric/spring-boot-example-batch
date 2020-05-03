package org.goafabric.spring.boot.examplebatch;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationIT {

    @Test
    public void test() {
        SpringApplication.run(Application.class, "");
    }
}