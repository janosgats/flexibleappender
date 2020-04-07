package com.janosgats.logging.flexibleappender;

import com.janosgats.logging.flexibleappender.enableable.AlwaysOnEnableable;
import com.janosgats.logging.flexibleappender.logfilter.AbstractLogFilter;
import com.janosgats.logging.flexibleappender.logfilter.AllowWhenMarkerNameEqualsLogFilter;
import com.janosgats.logging.flexibleappender.logfilter.DenyWhenMarkerNameEqualsLogFilter;
import com.janosgats.logging.flexibleappender.loglinebuilder.specific.LocaldevConsoleLogLineBuilder;
import com.janosgats.logging.flexibleappender.loglineoutput.specific.StdOutLogLineOutput;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.time.MutableInstant;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        DummyAppender dummyAppender = new DummyAppender("DummyAppender",
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

        dummyAppender.append(logEvent);

        outContent.reset();
        dummyAppender.append(logEvent);
        String actualLog = outContent.toString().replace("\r", "");
        assertEquals(expectedLog, actualLog);
    }

    @Test
    public void parameterizedMessageIsFormattedCorrectly() {
        DummyAppender dummyAppender = new DummyAppender("DummyAppender",
                null,
                PatternLayout.createDefaultLayout(),
                false,
                new Property[0]);


        long epochMilliTime = 1575634457120L;

        MutableInstant instant = new org.apache.logging.log4j.core.time.MutableInstant();
        instant.initFromEpochMilli(epochMilliTime, 0);

        String expectedLog = "[06 12:14:17.120] [INFO] [testThread,1,5] [abc.abc.abcdef.abcdef] > a: X, b: Y \n";

        LogEvent logEvent = new Log4jLogEvent.Builder()
                .setInstant(instant)
                .setLevel(Level.INFO)
                .setThreadName("testThread")
                .setThreadId(1)
                .setThreadPriority(5)
                .setLoggerName("abcdef.abcdef.abcdef.abcdef")
                .setMessage(new ParameterizedMessage("a: {}, b: {}", new String[]{"X", "Y"}))
                .build();

        dummyAppender.append(logEvent);

        outContent.reset();
        dummyAppender.append(logEvent);
        String actualLog = outContent.toString().replace("\r", "");
        assertEquals(expectedLog, actualLog);
    }

    @Test
    public void logFilterTest_AllowWhenMarkerNameEqualsLogFilter() {
        Marker marker1 = new MarkerManager.Log4jMarker("marker1");
        Marker marker2 = new MarkerManager.Log4jMarker("marker2");

        DummyAppender dummyAppender = new DummyAppender("DummyAppender",
                null,
                PatternLayout.createDefaultLayout(),
                false,
                new Property[0]);
        AlwaysOnEnableable alwaysOnEnableable = new AlwaysOnEnableable();
        AbstractLogFilter logFilter = new AllowWhenMarkerNameEqualsLogFilter(marker1);
        LocaldevConsoleLogLineBuilder localdevConsoleLogLineBuilder = new LocaldevConsoleLogLineBuilder(DateTimeFormatter.ofPattern("dd HH:mm:ss.SSS").withZone(ZoneId.of("UTC")), 2);
        StdOutLogLineOutput mockStdOutLogLineOutput = mock(StdOutLogLineOutput.class);
        dummyAppender.setUpAppender(alwaysOnEnableable, logFilter, localdevConsoleLogLineBuilder, mockStdOutLogLineOutput);

        LogEvent logEvent = new Log4jLogEvent.Builder()
                .setLevel(Level.INFO)
                .setMarker(marker1)
                .build();

        dummyAppender.append(logEvent);
        verify(mockStdOutLogLineOutput, times(1)).doOutputLogLine(any(), any());
        reset(mockStdOutLogLineOutput);

        logEvent = new Log4jLogEvent.Builder()
                .setLevel(Level.INFO)
                .setMarker(marker2)
                .build();

        dummyAppender.append(logEvent);
        verify(mockStdOutLogLineOutput, times(0)).doOutputLogLine(any(), any());
        reset(mockStdOutLogLineOutput);

        logEvent = new Log4jLogEvent.Builder()
                .setLevel(Level.INFO)
                .build();

        dummyAppender.append(logEvent);
        verify(mockStdOutLogLineOutput, times(0)).doOutputLogLine(any(), any());
    }

    @Test
    public void logFilterTest_DenyWhenMarkerNameEqualsLogFilter() {
        Marker marker1 = new MarkerManager.Log4jMarker("marker1");
        Marker marker2 = new MarkerManager.Log4jMarker("marker2");

        DummyAppender dummyAppender = new DummyAppender("DummyAppender",
                null,
                PatternLayout.createDefaultLayout(),
                false,
                new Property[0]);
        AlwaysOnEnableable alwaysOnEnableable = new AlwaysOnEnableable();
        AbstractLogFilter logFilter = new DenyWhenMarkerNameEqualsLogFilter(marker1);
        LocaldevConsoleLogLineBuilder localdevConsoleLogLineBuilder = new LocaldevConsoleLogLineBuilder(DateTimeFormatter.ofPattern("dd HH:mm:ss.SSS").withZone(ZoneId.of("UTC")), 2);
        StdOutLogLineOutput mockStdOutLogLineOutput = mock(StdOutLogLineOutput.class);
        dummyAppender.setUpAppender(alwaysOnEnableable, logFilter, localdevConsoleLogLineBuilder, mockStdOutLogLineOutput);

        LogEvent logEvent = new Log4jLogEvent.Builder()
                .setLevel(Level.INFO)
                .setMarker(marker1)
                .build();

        dummyAppender.append(logEvent);
        verify(mockStdOutLogLineOutput, times(0)).doOutputLogLine(any(), any());
        reset(mockStdOutLogLineOutput);

        logEvent = new Log4jLogEvent.Builder()
                .setLevel(Level.INFO)
                .setMarker(marker2)
                .build();

        dummyAppender.append(logEvent);
        verify(mockStdOutLogLineOutput, times(1)).doOutputLogLine(any(), any());
        reset(mockStdOutLogLineOutput);

        logEvent = new Log4jLogEvent.Builder()
                .setLevel(Level.INFO)
                .build();

        dummyAppender.append(logEvent);
        verify(mockStdOutLogLineOutput, times(1)).doOutputLogLine(any(), any());
    }
}
