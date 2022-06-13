package org.goafabric.spring.boot.examplebatch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Toy {
    private String id;
    private String catalogVersion;

    private String toyName;
    private String price;

}
