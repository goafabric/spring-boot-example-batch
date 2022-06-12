package org.goafabric.spring.boot.examplebatch.person;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class PersonItemProcessor<T> implements ItemProcessor<Person, Person> {
    @Value("#{jobParameters[catalogVersion]}") //catalog version passed in via job params
    private String catalogVersion;

    @Override
    public Person process(Person person) {
        return person;
    }

}
