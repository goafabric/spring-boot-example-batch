package org.goafabric.spring.boot.examplebatch;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

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
            hints.resources().registerResource(new ClassPathResource("db/migration/V1__init.sql"));
            
            Arrays.stream(java.sql.Types.class.getDeclaredFields()).forEach(f -> hints.reflection().registerField(f));

            hints.proxies().registerJdkProxy(
                    org.springframework.batch.item.ItemProcessor.class,
                    org.springframework.aop.scope.ScopedObject.class,
                    java.io.Serializable.class,
                    org.springframework.aop.framework.AopInfrastructureBean.class,
                    org.springframework.aop.SpringProxy.class,
                    org.springframework.aop.framework.Advised.class,
                    org.springframework.core.DecoratingProxy.class
                    );

            hints.proxies().registerJdkProxy(
                    org.springframework.batch.core.explore.JobExplorer.class,
                    org.springframework.aop.SpringProxy.class,
                    org.springframework.aop.framework.Advised.class,
                    org.springframework.core.DecoratingProxy.class
            );

        }
    }

}