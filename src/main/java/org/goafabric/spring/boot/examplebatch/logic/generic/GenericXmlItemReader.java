package org.goafabric.spring.boot.examplebatch.logic.generic;

import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.Map;

public class GenericXmlItemReader<T> extends StaxEventItemReader<T> {

    private String rootElementName;
    private Map<String, Class> aliases;
    private String fileName;

    public GenericXmlItemReader(String rootElementName, String fileName, Map<String, Class> aliases) {
        this.rootElementName = rootElementName;
        this.fileName = fileName;
        this.aliases = aliases;
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
