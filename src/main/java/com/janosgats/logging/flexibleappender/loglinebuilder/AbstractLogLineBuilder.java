package com.janosgats.logging.flexibleappender.loglinebuilder;

import org.apache.logging.log4j.core.LogEvent;

/**
 * Feel free to override one of these Appenders to create your own!
 */
public abstract class AbstractLogLineBuilder {
    public abstract String buildLogLine(LogEvent logEvent);
}
