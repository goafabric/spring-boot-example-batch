package org.goafabric.spring.boot.examplebatch.logic;


import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

public class GenericFileItemReader<T> extends FlatFileItemReader<T> {
    final String fileName;
    final String[] fieldNames;
    final Class classType;

    public GenericFileItemReader(Class classType, String fileName, String[] fieldNames) {
        this.classType = classType;
        this.fileName = fileName;
        this.fieldNames = fieldNames;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames(fieldNames);
        final BeanWrapperFieldSetMapper<T> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(classType);

        final DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        this.setResource(new ClassPathResource(fileName));
        this.setLineMapper(lineMapper);
        super.afterPropertiesSet();
    }
}
