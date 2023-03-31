package org.goafabric.spring.boot.examplebatch.job.toycatalog;

import org.goafabric.spring.boot.examplebatch.domain.Toy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

public class ToyItemProcessor implements ItemProcessor<Toy, Toy> {
    @Value("#{jobParameters[catalogVersion]}") //catalog version passed in via job params
    private String catalogVersion;

    @Override
    public Toy process(Toy toy) {
        return new Toy(
            toy.id(),
            toy.toyName().toLowerCase(),
            toy.price()
        );
    }

}
