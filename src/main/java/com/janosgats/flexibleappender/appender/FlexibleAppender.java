package com.janosgats.flexibleappender.appender;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;

import java.io.Serializable;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public abstract class FlexibleAppender extends AbstractAppender {
    private boolean isEnabled;

    protected DateTimeFormatter dateTimeFormatter;

    protected FlexibleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
        enableInitially();
    }

    protected void enableInitially() {
        tryToEnableByEnvironmentVariable();
    }

    protected void tryToEnableByEnvironmentVariable() {
        try {
            this.setEnabled(Boolean.parseBoolean(System.getenv(getNameOfEnablerEnvironmentVariable())));
        } catch (Exception e) {
            this.setEnabled(false);
            System.out.println("Error reading and parsing value from env var: " + getNameOfEnablerEnvironmentVariable());
            e.printStackTrace();
        }
    }

    protected abstract String getNameOfEnablerEnvironmentVariable();

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
        System.out.println("FlexibleAppender Setup->" + this.getClass().getSimpleName() + "::isEnabled: " + this.isEnabled());

    }

    @Override
    public void append(LogEvent event) {
        if (!isEnabled())
            return;

        String timeCodeString = dateTimeFormatter.format(Instant.ofEpochMilli(event.getInstant().getEpochMillisecond()));

        doLogLogevent(event, timeCodeString);
    }

    protected abstract void doLogLogevent(LogEvent logEvent, String timeCodeString);
}
