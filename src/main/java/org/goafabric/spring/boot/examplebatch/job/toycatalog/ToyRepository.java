package org.goafabric.spring.boot.examplebatch.job.toycatalog;

import org.springframework.data.repository.CrudRepository;

public interface ToyRepository extends CrudRepository<Toy, String> {}
