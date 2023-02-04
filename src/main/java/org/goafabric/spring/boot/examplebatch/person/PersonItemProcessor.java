package org.goafabric.spring.boot.examplebatch.person;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) {
        return Person.builder()
                .id(person.getId())
                .firstName(person.getFirstName().toLowerCase())
                .lastName(person.getLastName().toLowerCase())
                .build();
    }

}
