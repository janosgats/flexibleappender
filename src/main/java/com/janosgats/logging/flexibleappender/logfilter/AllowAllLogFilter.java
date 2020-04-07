package com.janosgats.logging.flexibleappender.logfilter;

import org.apache.logging.log4j.core.LogEvent;

/**
 * This Enabler is always enabled.
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public class AllowAllLogFilter extends AbstractLogFilter {
    @Override
    public boolean shouldLogEventBeLogged(LogEvent logEvent) {
        return true;
    }
}
