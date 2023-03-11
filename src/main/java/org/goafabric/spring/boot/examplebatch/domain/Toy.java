package org.goafabric.spring.boot.examplebatch.domain;

public record Toy (
    String id,
    String catalogVersion,
    String toyName,
    String price
) {}
