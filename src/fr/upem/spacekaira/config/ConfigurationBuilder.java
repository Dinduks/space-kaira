package fr.upem.spacekaira.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ConfigurationBuilder {
    public static Configuration buildFrom(File file) throws ConfigurationParsingException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Configuration configuration =
                    (Configuration) jaxbUnmarshaller.unmarshal(file);

            return configuration;
        } catch (JAXBException e) {
            throw new ConfigurationParsingException(e.toString());
        }
    }
}
