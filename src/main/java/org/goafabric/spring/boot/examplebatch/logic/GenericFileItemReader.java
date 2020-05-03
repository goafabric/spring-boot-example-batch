package org.goafabric.spring.boot.examplebatch.logic;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

@Slf4j
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
        this.setResource(new ClassPathResource(fileName));
        this.setLineMapper(createLineMapper());
        super.afterPropertiesSet();
    }

    private DefaultLineMapper<T> createLineMapper() {
        final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames(fieldNames);

        final BeanWrapperFieldSetMapper<T> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(classType);

        final DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
