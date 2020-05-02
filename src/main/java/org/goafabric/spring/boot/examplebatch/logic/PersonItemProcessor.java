package org.goafabric.spring.boot.examplebatch.logic;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.item.ItemProcessor;

import java.util.UUID;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person , Person> {

    @Override
    public Person process(Person person) throws Exception {
        //transforming the Person by making everything uppercase
        final Person transformedPerson = Person.builder()
                .id(UUID.randomUUID().toString())
                .firstName(person.getFirstName().toUpperCase())
                .lastName(person.getLastName().toUpperCase())
                .build();

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }
}
