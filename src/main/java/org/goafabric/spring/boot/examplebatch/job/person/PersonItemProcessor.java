package org.goafabric.spring.boot.examplebatch.job.person;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@RequiredArgsConstructor
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    private final Faker faker;

    @Override
    public Person process(Person person) {
        return Person.builder()
                .id(person.getId())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
    }

}
