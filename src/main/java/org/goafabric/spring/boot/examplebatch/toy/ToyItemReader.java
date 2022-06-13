package org.goafabric.spring.boot.examplebatch.toy;


import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Toy;
import org.goafabric.spring.boot.examplebatch.job.LineMapperUtil;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

@Slf4j
public class ToyItemReader extends FlatFileItemReader<Toy> {
    final Resource resource;
    final String[] fieldNames;

    public ToyItemReader(Resource resource, String[] fieldNames) {
        this.resource = resource;
        this.fieldNames = fieldNames;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setResource(resource);
        this.setLineMapper(LineMapperUtil.createLineMapper(Toy.class, fieldNames));
        super.afterPropertiesSet();
    }
}
