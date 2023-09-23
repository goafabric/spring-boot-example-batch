package org.goafabric.spring.boot.examplebatch.job.toycatalog;

public record Toy (
    String id,
    String toyName,
    String price
) {}
