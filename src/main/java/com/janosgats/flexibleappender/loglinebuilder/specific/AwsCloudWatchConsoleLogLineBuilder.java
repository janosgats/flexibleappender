package com.janosgats.flexibleappender.loglinebuilder.specific;

import com.janosgats.flexibleappender.helper.LoggingHelper;
import com.janosgats.flexibleappender.loglinebuilder.DateTimeFormatterLogLineBuilder;
import org.apache.logging.log4j.core.LogEvent;

import java.time.format.DateTimeFormatter;

/**
 * Feel free to override one of these Appenders to create your own!
 */
public class AwsCloudWatchConsoleLogLineBuilder extends DateTimeFormatterLogLineBuilder {
    protected final StringBuilder stringBuilder = new StringBuilder();

    public AwsCloudWatchConsoleLogLineBuilder(DateTimeFormatter dateTimeFormatter) {
        super(dateTimeFormatter);
    }

    @Override
    public String buildLogLine(LogEvent logEvent) {
        stringBuilder.setLength(0); // flush StringBuilder

        String timeCodeString = getFormattedDateTimeStringFromLogEvent(logEvent);

        stringBuilder
                .append("[")
                .append(timeCodeString)
                .append("] [")
                .append(logEvent.getLevel())
                .append("] [")
                .append(logEvent.getThreadName())
                .append(",")
                .append(logEvent.getThreadId())
                .append(",")
                .append(logEvent.getThreadPriority())
                .append("] [").append(logEvent.getLoggerName())
                .append("] ");

        if (logEvent.getMarker() != null) {
            stringBuilder.append("[")
                    .append(logEvent.getMarker())
                    .append("] ");
        }

        stringBuilder.append("> ")
                .append(logEvent.getMessage().toString()
                        .replace("\t", "\\t")
                        .replace("\r", "")
                        .replace("\n", "\\n"))
                .append(" ");

        if (logEvent.getThrown() != null) {
            stringBuilder.append(">>> Thrown >> ")
                    .append(LoggingHelper.getExceptionInOneLine(logEvent.getThrown()))
                    .append(" <<<<< ");
        }

        return stringBuilder.toString();
    }
}
