package org.goafabric.spring.boot.examplebatch.toy;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.dto.Toy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class ToyItemProcessor implements ItemProcessor<Toy, Toy> {
    @Value("#{jobParameters[catalogVersion]}") //catalog version passed in via job params
    private String catalogVersion;

    @Override
    public Toy process(Toy toy) {
        return Toy.builder()
                .id(toy.getId())
                .toyName(toy.getToyName().toLowerCase())
                .price(toy.getPrice())
                .build();
    }

}
