package fr.upem.spacekaira.config;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public final class ConfigurationLoader {
    private ConfigurationLoader() {}

    /**
     * Builds a configuration from a level description file.
     *
     * @param file The level description file
     * @return     The build Configuration
     * @throws     ConfigurationParsingException
     */
    public static Configuration loadFrom(File file)
            throws ConfigurationParsingException {
        try {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);

            SchemaFactory sf = SchemaFactory.newInstance(
                    XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("level.xsd"));

            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new ConfigurationEventHandler());
            Configuration config = (Configuration) unmarshaller.unmarshal(file);

            return config;
        } catch (Exception e) {
            throw new ConfigurationParsingException(e.toString());
        }
    }

    /**
     * This event handler makes the marshalling crash if there's any error or
     * warning
     */
    public static class ConfigurationEventHandler implements ValidationEventHandler {
        @Override
        public boolean handleEvent(ValidationEvent event) {
            return false;
        }
    }
}
