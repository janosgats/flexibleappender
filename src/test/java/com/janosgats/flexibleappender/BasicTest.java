package com.janosgats.flexibleappender;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.time.MutableInstant;
import org.apache.logging.log4j.message.SimpleMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BasicTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void basicTest() {
        TestAppender testAppender = new TestAppender("TestAppender",
                null,
                PatternLayout.createDefaultLayout(),
                false,
                new Property[0]);


        long epochMilliTime = 1575634457120L;

        MutableInstant instant = new org.apache.logging.log4j.core.time.MutableInstant();
        instant.initFromEpochMilli(epochMilliTime, 0);

        String expectedLog = "[06 12:14:17.120] [INFO] [testThread,1,5] [abc.abc.abcdef.abcdef] > Test message. \n";

        LogEvent logEvent = new Log4jLogEvent.Builder()
                .setInstant(instant)
                .setLevel(Level.INFO)
                .setThreadName("testThread")
                .setThreadId(1)
                .setThreadPriority(5)
                .setLoggerName("abcdef.abcdef.abcdef.abcdef")
                .setMessage(new SimpleMessage("Test message."))
                .build();

        testAppender.append(logEvent);

        outContent.reset();
        testAppender.append(logEvent);
        String actualLog = outContent.toString().replace("\r", "");
        assertEquals(expectedLog, actualLog);
    }
}
