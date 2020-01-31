package com.janosgats.logging.flexibleappender.loglinebuilder.specific;

import com.google.gson.Gson;
import com.janosgats.logging.flexibleappender.helper.LoggingHelper;
import com.janosgats.logging.flexibleappender.loglinebuilder.DateTimeFormatterLogLineBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

/**
 * Feel free to override one of these Appenders to create your own!
 */
public class SplunkLogLineBuilder extends DateTimeFormatterLogLineBuilder {
    protected final String logSourceType;

    protected final Gson gson = new Gson();

    public SplunkLogLineBuilder(String logSourceType, DateTimeFormatter dateTimeFormatter) {
        super(dateTimeFormatter);
        this.logSourceType = logSourceType;
    }


    @Override
    public String buildLogLine(LogEvent logEvent) {
        LinkedHashMap<String, String> orderedLogMap = createLogMapFromLogEvent(logEvent);
        orderedLogMap.put("sourcetype", logSourceType);

        return gson.toJson(orderedLogMap);
    }

    protected LinkedHashMap<String, String> createLogMapFromLogEvent(LogEvent logEvent) {
        LinkedHashMap<String, String> orderedLogMap = new LinkedHashMap<>();

        orderedLogMap.put("timestamp", getFormattedDateTimeStringFromLogEvent(logEvent));//This field has to be the first for Splunk!

        orderedLogMap.put("level", logEvent.getLevel().toString());
        orderedLogMap.put("message", logEvent.getMessage().getFormattedMessage());
        orderedLogMap.put("loggerName", logEvent.getLoggerName());
        orderedLogMap.put("timeMillis", String.valueOf(logEvent.getTimeMillis()));


        if (logEvent.getLevel() == Level.FATAL || logEvent.getLevel() == Level.ERROR || logEvent.getLevel() == Level.WARN) {
            orderedLogMap.put("threadId", String.valueOf(logEvent.getThreadId()));
            orderedLogMap.put("threadName", logEvent.getThreadName());
            if (logEvent.getMarker() != null)
                orderedLogMap.put("marker", logEvent.getMarker().getName());
            orderedLogMap.put("threadPriority", String.valueOf(logEvent.getThreadPriority()));
        }

        if (logEvent.getThrown() != null)
            orderedLogMap.put("thrown", LoggingHelper.getStackTraceAsString(logEvent.getThrown()));


        return orderedLogMap;
    }
}
