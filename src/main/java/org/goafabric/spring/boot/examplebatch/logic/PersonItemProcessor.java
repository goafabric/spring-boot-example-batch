package org.goafabric.spring.boot.examplebatch.logic;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.item.ItemProcessor;

import java.util.UUID;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person , Person> {

    @Override
    public Person process(Person person) {
        person.setId(UUID.randomUUID().toString());
        return person;
    }
}
