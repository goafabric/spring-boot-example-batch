package org.goafabric.spring.boot.examplebatch.job.personanonymizer;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "person", schema = "masterdata")
public record Person(
    @Id String id,
    //@Version Long version,
    String firstName,
    String lastName
) {}