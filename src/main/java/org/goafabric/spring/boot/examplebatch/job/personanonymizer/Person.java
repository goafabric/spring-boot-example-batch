package org.goafabric.spring.boot.examplebatch.job.personanonymizer;

public record Person (
    String id,
    String firstName,
    String lastName
) {}