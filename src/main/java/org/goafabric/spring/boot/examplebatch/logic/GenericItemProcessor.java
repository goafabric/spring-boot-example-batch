package org.goafabric.spring.boot.examplebatch.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class GenericItemProcessor<T> implements ItemProcessor<T , T> {
    @Autowired
    private VersionHandler versionHandler;

    @Override
    public T process(T object) {
        versionHandler.setId(object);
        versionHandler.setCatalogVersion(object);
        return object;
    }
}
