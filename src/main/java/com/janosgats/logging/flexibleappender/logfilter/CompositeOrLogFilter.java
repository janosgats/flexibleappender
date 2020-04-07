package com.janosgats.logging.flexibleappender.logfilter;


import org.apache.logging.log4j.core.LogEvent;

/**
 * Execute multiple enablers with a logical OR by using this enabler!
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public class CompositeOrLogFilter extends CompositeLogFilter {
    @Override
    public boolean shouldLogEventBeLogged(LogEvent logEvent) {
        for (AbstractLogFilter logFilter : abstractLogFilters) {
            if (logFilter.shouldLogEventBeLogged(logEvent))
                return true;
        }
        return false;
    }
}
