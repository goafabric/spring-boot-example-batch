package org.goafabric.spring.boot.examplebatch.logic.generic;

import org.goafabric.spring.boot.examplebatch.dto.Toy;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

public class GenericXmlItemReader<T> extends StaxEventItemReader<T> {

    private final Class classType;
    private String fileName;
    private String rootElementName;

    public GenericXmlItemReader(Class classType, String fileName, String rootElementName) {
        this.classType = classType;
        this.fileName = fileName;
        this.rootElementName = rootElementName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setResource(new ClassPathResource(fileName));
        this.setFragmentRootElementName(rootElementName);

        final XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(new HashMap<String, Class>() {{
            put(rootElementName, classType);
        }});
        this.setUnmarshaller(marshaller);

        super.afterPropertiesSet();
    }


}
