package com.janosgats.logging.flexibleappender.logfilter;

import org.apache.logging.log4j.Marker;

/**
 * This Enabler is always enabled.
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public abstract class MarkerLogFilter extends AbstractLogFilter {
    protected Marker marker;

    public MarkerLogFilter(Marker marker) {
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }
}
