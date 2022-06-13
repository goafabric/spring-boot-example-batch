package org.goafabric.spring.boot.examplebatch;

import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.goafabric.spring.boot.examplebatch.domain.Toy;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;

@TypeHint(types = {Person.class, Toy.class}, access = { TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.PUBLIC_METHODS })
@EnableBatchProcessing
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}