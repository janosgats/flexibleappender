package com.janosgats.logging.flexibleappender.commonappenders.readytouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.janosgats.logging.flexibleappender.util.LoggerUtil.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AwsCloudWatchConsoleAppenderTest {

    private static final String ENABLER_PROP = "AWS_APPENDER_ENABLED";
    private static final String CONFIG_FILE = "log4j2-aws-test.xml";

    private Logger logger = LogManager.getLogger("test");
    private ByteArrayOutputStream outputStream;

    @BeforeAll
    public static void setup() {
        setConfigFile(CONFIG_FILE);
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(System.out);
        System.clearProperty(ENABLER_PROP);
        clearConfigFile(CONFIG_FILE);
    }

    @BeforeEach
    void setupStreams() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void loggerLogsWhenEnablerPropertyIsSet() {
        System.setProperty(ENABLER_PROP, "true");
        reconfigure(CONFIG_FILE);

        logger.info("hello there!");
        assertTrue(outputStream.toString().contains("hello there!"));
    }

    @Test
    void loggerDoesNotLogWhenEnablerPropertyIsNotSet() {
        System.clearProperty(ENABLER_PROP);
        reconfigure(CONFIG_FILE);

        logger.info("hello there!");
        assertFalse(outputStream.toString().contains("hello there!"));
    }

}