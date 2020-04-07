package com.janosgats.logging.flexibleappender.logfilter;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;

/**
 * This Enabler is always enabled.
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public class DenyWhenMarkerNameEqualsLogFilter extends MarkerLogFilter {
    public DenyWhenMarkerNameEqualsLogFilter(Marker marker) {
        super(marker);
    }

    @Override
    public boolean shouldLogEventBeLogged(LogEvent logEvent) {
        if (logEvent.getMarker() == null || logEvent.getMarker().getName() == null)
            return true;

        return !logEvent.getMarker().getName().equals(this.getMarker().getName());
    }
}
