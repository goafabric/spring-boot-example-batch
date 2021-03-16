/*
package org.goafabric.spring.boot.examplebatch.logic.generic;

import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;

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
        this.setResource(new FileSystemResource(fileName));
        this.setFragmentRootElementName(rootElementName);

        final XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(new HashMap<String, Class>() {{
            put(rootElementName, classType);
        }});
        this.setUnmarshaller(marshaller);

        super.afterPropertiesSet();
    }

}

 */
