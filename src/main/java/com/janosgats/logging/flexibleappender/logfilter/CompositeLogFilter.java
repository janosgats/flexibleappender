package com.janosgats.logging.flexibleappender.logfilter;

import java.util.LinkedList;

/**
 * Execute multiple enablers by using this enabler!
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public abstract class CompositeLogFilter extends AbstractLogFilter {

    protected LinkedList<AbstractLogFilter> abstractLogFilters = new LinkedList<>();

    public LinkedList<AbstractLogFilter> getAbstractLogFilters() {
        return abstractLogFilters;
    }

    public void setAbstractLogFilters(LinkedList<AbstractLogFilter> abstractLogFilters) {
        this.abstractLogFilters = abstractLogFilters;
    }
}
