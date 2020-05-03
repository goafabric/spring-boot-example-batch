package org.goafabric.spring.boot.examplebatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToyCatalog {
    private String id;
    private String toyName;
    private String price;
}
