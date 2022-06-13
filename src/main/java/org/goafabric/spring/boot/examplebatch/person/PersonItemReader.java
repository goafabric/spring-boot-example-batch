package org.goafabric.spring.boot.examplebatch.person;


import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;

@Slf4j
public class PersonItemReader extends FlatFileItemReader<Person> {
    final Resource resource;
    final String[] fieldNames;

    public PersonItemReader(Resource resource, String[] fieldNames) {
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
    private DefaultLineMapper<Person> createLineMapper() {
        final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames(fieldNames);

        final BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);

        final DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
