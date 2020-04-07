package com.janosgats.logging.flexibleappender.logfilter;

import org.apache.logging.log4j.core.LogEvent;

/**
 * LogFilters are evaluated every time when a new {@link org.apache.logging.log4j.core.LogEvent} comes into the Appender.
 * <p>
 * Feel free to override one of these LogFilters to create your own!
 */
public abstract class AbstractLogFilter {

    public abstract boolean shouldLogEventBeLogged(LogEvent logEvent);
}
