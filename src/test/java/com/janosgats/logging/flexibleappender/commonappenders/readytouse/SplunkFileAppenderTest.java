package com.janosgats.logging.flexibleappender.commonappenders.readytouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.janosgats.logging.flexibleappender.util.FileUtil.getFirstFileInDir;
import static com.janosgats.logging.flexibleappender.util.LoggerUtil.clearConfigFile;
import static com.janosgats.logging.flexibleappender.util.LoggerUtil.reconfigure;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SplunkFileAppenderTest {

    private static final String DIR_PROP = "SPLUNK_APPENDER_LOG_DIR";
    private static final String SOURCE_TYPE_PROP = "SPLUNK_APPENDER_LOG_SOURCE_TYPE";
    private static final String SOURCE_TYPE_PROP_VALUE = "test-log";
    private static final String ENABLER_PROP = "LOGGING_ENABLE_SPLUNK_FILE_APPENDER";
    private static final String CONFIG_FILE = "log4j2-splunk-test.xml";

    @TempDir
    static Path tempDir;

    private Logger logger = LogManager.getLogger("test");

    @BeforeAll
    public static void setup() {
        System.setProperty(DIR_PROP, tempDir.toAbsolutePath().toString());
        System.setProperty(SOURCE_TYPE_PROP, SOURCE_TYPE_PROP_VALUE);
        System.setProperty(ENABLER_PROP, "true");
        reconfigure(CONFIG_FILE);
    }

    @AfterAll
    static void afterAll() {
        System.clearProperty(ENABLER_PROP);
        clearConfigFile(CONFIG_FILE);
    }

    @Test
    void logFileContainsLogMessage() throws Exception {

        String logMessage = "hello there";
        logger.info(logMessage);

        File logFile = getFirstFileInDir(tempDir);
        String content = new String(Files.readAllBytes(logFile.toPath()));

        assertTrue(logFile.getName().contains(SOURCE_TYPE_PROP_VALUE));
        assertTrue(content.contains(SOURCE_TYPE_PROP_VALUE));
        assertTrue(content.contains(logMessage));
    }

}