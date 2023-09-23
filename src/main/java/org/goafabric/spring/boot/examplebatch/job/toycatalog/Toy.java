package org.goafabric.spring.boot.examplebatch.job.toycatalog;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "toy_catalog", schema = "masterdata")
public record Toy (
    @Id String id,
    //@Version Long version,
    String toyName,
    String price
) {}
