package com.janosgats.logging.flexibleappender.loglinebuilder.specific;

import com.janosgats.logging.flexibleappender.helper.LoggingHelper;
import com.janosgats.logging.flexibleappender.loglinebuilder.DateTimeFormatterLogLineBuilder;
import org.apache.logging.log4j.core.LogEvent;

import java.time.format.DateTimeFormatter;

/**
 * Feel free to override one of these LogLineBuilders to create your own!
 */
public class LocaldevConsoleLogLineBuilder extends DateTimeFormatterLogLineBuilder {
    protected final StringBuilder stringBuilder = new StringBuilder();
    private final int loggerNameLeaveAsIsLevel;

    public LocaldevConsoleLogLineBuilder(DateTimeFormatter dateTimeFormatter, int loggerNameLeaveAsIsLevel) {
        super(dateTimeFormatter);
        this.loggerNameLeaveAsIsLevel = loggerNameLeaveAsIsLevel;
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
                .append("] [");

        LoggingHelper.appendFormattedLoggerNameToStringBuilder(logEvent.getLoggerName(), loggerNameLeaveAsIsLevel, stringBuilder);

        stringBuilder.append("] ");

        if (logEvent.getMarker() != null) {
            stringBuilder.append("[")
                    .append(logEvent.getMarker())
                    .append("] ");
        }

        stringBuilder.append("> ")
                .append(logEvent.getMessage().toString())
                .append(" ");

        if (logEvent.getThrown() != null) {
            stringBuilder
                    .append(">>>>>>>>>>>>>>\n")
                    .append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n")
                    .append(">>>>>>>>>>>>>>>> A Thrown is present in the LogEvent at: ")
                    .append(timeCodeString)
                    .append(" >>>>>>>>>>>>>>>>\n\n")
                    .append(LoggingHelper.getStackTraceAsString(logEvent.getThrown()))
                    .append("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
        }

        return stringBuilder.toString();
    }
}
