package org.goafabric.spring.boot.examplebatch.job.personanonymizer;

import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface PersonRepository extends CrudRepository<Person, String> {
    Stream<Person> findAllBy();
}
