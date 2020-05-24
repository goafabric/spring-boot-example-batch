package org.goafabric.spring.boot.examplebatch.logic.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class GenericItemProcessor<T> implements ItemProcessor<T , T> {
    @Autowired
    private GenericVersionHandler genericVersionHandler;

    @Override
    public T process(T object) {
        genericVersionHandler.setId(object);
        genericVersionHandler.setCatalogVersion(object);
        return object;
    }
}
