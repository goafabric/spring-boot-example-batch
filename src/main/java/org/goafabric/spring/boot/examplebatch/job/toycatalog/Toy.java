package org.goafabric.spring.boot.examplebatch.job.toycatalog;

public record Toy (
    String id,
    @Version Long version,
    String toyName,
    String price
) {}
