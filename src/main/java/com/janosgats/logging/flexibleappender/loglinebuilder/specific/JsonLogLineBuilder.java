package com.janosgats.logging.flexibleappender.loglinebuilder.specific;

import com.google.gson.Gson;
import com.janosgats.logging.flexibleappender.helper.LoggingHelper;
import com.janosgats.logging.flexibleappender.loglinebuilder.DateTimeFormatterLogLineBuilder;
import org.apache.logging.log4j.core.LogEvent;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Feel free to override one of these LogLineBuilders to create your own!
 */
public class JsonLogLineBuilder extends DateTimeFormatterLogLineBuilder {

    protected final Gson gson = new Gson();

    public JsonLogLineBuilder(DateTimeFormatter dateTimeFormatter) {
        super(dateTimeFormatter);
    }


    @Override
    public String buildLogLine(LogEvent logEvent) {
        HashMap<String, String> logMap = new LinkedHashMap<>();

        logMap.put("timestamp", getFormattedDateTimeStringFromLogEvent(logEvent));

        logMap.put("level", logEvent.getLevel().toString());
        logMap.put("message", logEvent.getMessage().getFormattedMessage());
        logMap.put("loggerName", logEvent.getLoggerName());
        logMap.put("timeMillis", String.valueOf(logEvent.getTimeMillis()));


        logMap.put("threadId", String.valueOf(logEvent.getThreadId()));
        logMap.put("threadName", logEvent.getThreadName());
        logMap.put("threadPriority", String.valueOf(logEvent.getThreadPriority()));
        if (logEvent.getMarker() != null)
            logMap.put("marker", logEvent.getMarker().getName());

        if (logEvent.getThrown() != null)
            logMap.put("thrown", LoggingHelper.getStackTraceAsString(logEvent.getThrown()));


        return gson.toJson(logMap);
    }
}
