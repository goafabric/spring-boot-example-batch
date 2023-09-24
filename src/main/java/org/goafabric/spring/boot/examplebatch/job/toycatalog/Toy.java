package org.goafabric.spring.boot.examplebatch.job.toycatalog;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "toy_catalog", schema = "masterdata")
@Document("toy_catalog")
public record Toy (
    @Id String id,
    @Version Long version,
    String toyName,
    String price
) {
    public Toy(String toyName, String price) {
        this(UUID.randomUUID().toString(), null, toyName, price);
    }
}
