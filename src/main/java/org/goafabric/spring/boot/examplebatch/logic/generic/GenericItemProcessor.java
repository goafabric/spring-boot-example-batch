package org.goafabric.spring.boot.examplebatch.logic.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.UUID;

@Slf4j
public class GenericItemProcessor<T> implements ItemProcessor<T , T> {

    @Override
    public T process(T object) {
        setId(object);
        return object;
    }

    private void setId(Object object) {
        final Field fieldId = ReflectionUtils.findField(object.getClass(), "id");
        fieldId.setAccessible(true);
        ReflectionUtils.setField(fieldId, object, UUID.randomUUID().toString());
    }
}
