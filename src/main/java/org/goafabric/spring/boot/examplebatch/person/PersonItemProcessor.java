package org.goafabric.spring.boot.examplebatch.person;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Value("#{jobParameters[catalogVersion]}") //catalog version passed in via job params
    private String catalogVersion;

    @Override
    public Person process(Person person) {
        return Person.builder()
                .id(person.getId())
                .firstName(person.getFirstName().toLowerCase())
                .lastName(person.getLastName().toLowerCase())
                .build();
    }

}
