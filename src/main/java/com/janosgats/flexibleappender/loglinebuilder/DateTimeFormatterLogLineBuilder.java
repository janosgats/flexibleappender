package com.janosgats.flexibleappender.loglinebuilder;

import org.apache.logging.log4j.core.LogEvent;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Feel free to override one of these Appenders to create your own!
 */
public abstract class DateTimeFormatterLogLineBuilder extends AbstractLogLineBuilder {
    protected DateTimeFormatter dateTimeFormatter;

    public DateTimeFormatterLogLineBuilder(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    protected String getFormattedDateTimeStringFromLogEvent(LogEvent logEvent) {
        return dateTimeFormatter.format(Instant.ofEpochMilli(logEvent.getInstant().getEpochMillisecond()));
    }
}
