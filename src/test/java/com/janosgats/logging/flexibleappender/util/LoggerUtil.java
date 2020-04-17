package com.janosgats.logging.flexibleappender.util;

import org.apache.logging.log4j.core.config.Configurator;

import java.net.URI;
import java.net.URISyntaxException;

public class LoggerUtil {

    private static final String CONFIG_FILE_PROP = "log4j.configurationFile";

    private LoggerUtil() {
    }

    public static void setConfigFile(String file) {
        System.setProperty(CONFIG_FILE_PROP, file);
    }

    public static void clearConfigFile(String file) {
        System.clearProperty(CONFIG_FILE_PROP);
    }

    public static void reconfigure(String file) {
        try {
            Configurator.reconfigure(new URI(file));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
//        ((org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false)).reconfigure();
    }

}
