package org.goafabric.spring.boot.examplebatch.job.person;

import com.github.javafaker.Faker;
import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    private final Faker faker;

    public PersonItemProcessor(Faker faker) {
        this.faker = faker;
    }

    @Override
    public Person process(Person person) {
        return new Person(
                person.id(),
                faker.name().firstName(),
                faker.name().lastName()
        );
    }
}
