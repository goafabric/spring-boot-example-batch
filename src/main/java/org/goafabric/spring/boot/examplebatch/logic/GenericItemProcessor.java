package org.goafabric.spring.boot.examplebatch.logic;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.UUID;

@Slf4j
public class GenericItemProcessor<T> implements ItemProcessor<T , T> {

    @Override
    public T process(T object) {
        final Field field = ReflectionUtils.findField(object.getClass(), "id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, object, UUID.randomUUID().toString());
        return object;
    }
}
