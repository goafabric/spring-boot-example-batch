package org.goafabric.spring.boot.examplebatch.logic;


import ch.qos.logback.core.subst.Tokenizer;
import org.goafabric.spring.boot.examplebatch.dto.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class PersonItemReader extends FlatFileItemReader<Person> {
    final String fileName;
    final String[] fieldNames;

    public PersonItemReader(String fileName, String[] fieldNames) {
        this.fileName = fileName;
        this.fieldNames = fieldNames;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames(fieldNames);
        final BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<Person>();
        fieldSetMapper.setTargetType(Person.class);

        final DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        this.setResource(new ClassPathResource(fileName));
        this.setLineMapper(lineMapper);
        super.afterPropertiesSet();
    }
}
