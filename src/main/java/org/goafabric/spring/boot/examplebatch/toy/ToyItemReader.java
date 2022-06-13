package org.goafabric.spring.boot.examplebatch.toy;


import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Toy;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
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
        this.setLineMapper(createLineMapper());
        super.afterPropertiesSet();
    }

    //generate LineMapper for CSV file, with "," delimiter and for the given ClassType
    private DefaultLineMapper<Toy> createLineMapper() {
        final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames(fieldNames);

        final BeanWrapperFieldSetMapper<Toy> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Toy.class);

        final DefaultLineMapper<Toy> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
