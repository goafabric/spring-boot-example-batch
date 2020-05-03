package org.goafabric.spring.boot.examplebatch.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.UUID;

@Slf4j
public class GenericItemProcessor<T> implements ItemProcessor<T , T> {
    private String catalogVersion = "1";

    @Value("#{jobParameters[catalogVersion]}")
    private String catalogVersion2;


    @Override
    public T process(T object) {
        setId(object);
        setCatalogVersion(object);
        return object;
    }

    private void setId(T object) {
        final Field fieldId = ReflectionUtils.findField(object.getClass(), "id");
        fieldId.setAccessible(true);
        ReflectionUtils.setField(fieldId, object, UUID.randomUUID().toString());
    }

    private void setCatalogVersion(T object) {
        final Field fieldCatalogVersion = ReflectionUtils.findField(object.getClass(), "catalogVersion");
        fieldCatalogVersion.setAccessible(true);
        ReflectionUtils.setField(fieldCatalogVersion, object, catalogVersion);
    }
}
