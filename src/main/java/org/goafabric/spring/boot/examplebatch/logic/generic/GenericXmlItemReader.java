package org.goafabric.spring.boot.examplebatch.logic.generic;

import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.Map;

public class GenericXmlItemReader<T> extends StaxEventItemReader<T> {

    private String rootElementName;
    private Map<String, Class> aliases;
    private String fileName;

    public GenericXmlItemReader(String rootElementName, Map<String, Class> aliases, String fileName) {
        this.rootElementName = rootElementName;
        this.aliases = aliases;
        this.fileName = fileName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setResource(new ClassPathResource(fileName));
        this.setFragmentRootElementName(rootElementName);

        final XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);
        this.setUnmarshaller(marshaller);

        super.afterPropertiesSet();
    }


}
