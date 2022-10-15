package org.goafabric.spring.boot.examplebatch;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

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
            Arrays.stream(java.sql.Types.class.getDeclaredFields()).forEach(f -> hints.reflection().registerField(f));

            hints.proxies().registerJdkProxy(AopProxyUtils.completeJdkProxyInterfaces(ItemProcessor.class));
            /*
            hints.proxies().registerJdkProxy(
                    org.springframework.batch.item.ItemProcessor.class,
                    org.springframework.aop.scope.ScopedObject.class,
                    org.springframework.aop.framework.AopInfrastructureBean.class,
                    org.springframework.aop.SpringProxy.class,
                    org.springframework.aop.framework.Advised.class,
                    org.springframework.core.DecoratingProxy.class
                    );

             */
        }
    }

}