package org.goafabric.spring.boot.examplebatch;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.HashMap;

//@EnableBatchProcessing
@SpringBootApplication
@ImportRuntimeHints(Application.ApplicationRuntimeHints.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            //resources
            hints.resources().registerPattern("catalogdata/*.csv");

            //hints for executioncontext on 2nd run
            hints.serialization().registerType(HashMap.class);
            hints.serialization().registerType(java.lang.Integer.class);
            hints.serialization().registerType(java.lang.Number.class);
            hints.serialization().registerType(java.lang.String.class);
        }
    }

}