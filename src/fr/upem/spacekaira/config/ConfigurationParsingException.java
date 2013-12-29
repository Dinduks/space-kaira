package fr.upem.spacekaira.config;

/**
 * Exception that should be thrown when the level's configuration fails to be
 * loaded.
 * Its message should contain the message of a JAXB related exception.
 */
public class ConfigurationParsingException extends Throwable {
    public ConfigurationParsingException(String stackTrace) {
        super("Error while parsing the configuration file: \n\n" + stackTrace);
    }
}
