package org.goafabric.spring.boot.examplebatch.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String id;
    private String catalogVersion;

    private String lastName;
    private String firstName;
}
