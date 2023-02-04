package org.goafabric.spring.boot.examplebatch.person;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Autowired
    private Faker faker;

    @Override
    public Person process(Person person) {
        return Person.builder()
                .id(person.getId())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
    }

}
