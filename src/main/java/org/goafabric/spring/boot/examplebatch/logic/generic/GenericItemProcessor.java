package org.goafabric.spring.boot.examplebatch.logic.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class GenericItemProcessor<T> implements ItemProcessor<T , T> {
    @Value("#{jobParameters[catalogVersion]}") //catalog version passed in via job params
    private String catalogVersion;

    @Override
    public T process(T object) {
        //setField(object, "id", UUID.randomUUID().toString());
        //setField(object, "catalogVersion", catalogVersion);
        return object;
    }

    /*
    private void setField(Object object, String fieldName, String fieldValue) {
        final Field fieldId = ReflectionUtils.findField(object.getClass(), fieldName);
        fieldId.setAccessible(true);
        ReflectionUtils.setField(fieldId, object, fieldValue);
    }
     */
}
