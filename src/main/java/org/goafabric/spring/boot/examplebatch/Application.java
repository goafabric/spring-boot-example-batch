package org.goafabric.spring.boot.examplebatch;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

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
            hints.resources().registerPattern("en/*.yml"); //needed for stupid faker

            //hints.proxies().registerJdkProxy(AopProxyUtils.completeJdkProxyInterfaces(JobOperator.class));
        }
    }

}