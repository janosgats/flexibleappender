package com.janosgats.logging.flexibleappender.logfilter;


import com.janosgats.logging.flexibleappender.enableable.AbstractEnableable;
import org.apache.logging.log4j.core.LogEvent;

/**
 * Execute multiple enablers with a logical OR by using this enabler!
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public class CompositeAndLogFilter extends CompositeLogFilter {
    @Override
    public boolean shouldLogEventBeLogged(LogEvent logEvent) {
        for (AbstractLogFilter logFilter : abstractLogFilters) {
            if (!logFilter.shouldLogEventBeLogged(logEvent))
                return false;
        }
        return true;
    }
}
