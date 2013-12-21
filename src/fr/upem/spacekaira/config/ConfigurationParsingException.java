package fr.upem.spacekaira.config;

public class ConfigurationParsingException extends Throwable {
    public ConfigurationParsingException(String stackTrace) {
        super("Error while parsing the configuration file: \n\n" + stackTrace);
    }
}
