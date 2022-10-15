package org.goafabric.spring.boot.examplebatch;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
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

            //proxies
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
                    AopProxyUtils.completeJdkProxyInterfaces(org.springframework.batch.core.explore.JobExplorer.class));

            //pojos
            hints.reflection().registerType(org.goafabric.spring.boot.examplebatch.domain.Person.class,
                    MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS);

            hints.reflection().registerType(org.goafabric.spring.boot.examplebatch.domain.Toy.class,
                    MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS);

            //resources
            hints.resources().registerPattern("db/migration/*.sql");
            hints.resources().registerPattern("catalogdata/*.csv");

        }
    }

}