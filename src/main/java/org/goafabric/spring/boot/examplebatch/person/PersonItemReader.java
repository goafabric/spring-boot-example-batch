package org.goafabric.spring.boot.examplebatch.person;


import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.examplebatch.domain.Person;
import org.goafabric.spring.boot.examplebatch.job.LineMapperUtil;
import org.springframework.batch.item.file.FlatFileItemReader;
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
        this.setLineMapper(LineMapperUtil.createLineMapper(Person.class, fieldNames));
        super.afterPropertiesSet();
    }
}
