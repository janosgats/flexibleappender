package com.janosgats.flexibleappender;

import com.janosgats.flexibleappender.enableable.AbstractEnableable;
import com.janosgats.flexibleappender.helper.LoggingHelper;
import com.janosgats.flexibleappender.loglinebuilder.AbstractLogLineBuilder;
import com.janosgats.flexibleappender.loglineoutput.AbstractLogLineOutput;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;

import java.io.Serializable;

/**
 * Feel free to override one of these Appenders to create your own!
 */
public abstract class FlexibleAppender extends AbstractAppender {
    private boolean isCurrentlyEnabled;
    private AbstractEnableable enableable;
    private AbstractLogLineBuilder logLineBuilder;
    private AbstractLogLineOutput logLineOutput;

    protected FlexibleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties,
                               AbstractEnableable enableable, AbstractLogLineBuilder logLineBuilder, AbstractLogLineOutput logLineOutput) {
        super(name, filter, layout, ignoreExceptions, properties);

        this.enableable = enableable;
        this.logLineBuilder = logLineBuilder;
        this.logLineOutput = logLineOutput;

        setCurrentlyEnabledByExecutingEnableable();
    }

    public void setCurrentlyEnabledByExecutingEnableable() {
        this.setCurrentlyEnabled(enableable.isEnabled());
    }

    public boolean isCurrentlyEnabled() {
        return isCurrentlyEnabled;
    }

    public void setCurrentlyEnabled(boolean currentlyEnabled) {
        this.isCurrentlyEnabled = currentlyEnabled;
        System.out.println("FlexibleAppender Setup->" + this.getClass().getSimpleName() + "::isCurrentlyEnabled: " + this.isCurrentlyEnabled());

    }

    @Override
    public void append(LogEvent event) {
        if (!isCurrentlyEnabled())
            return;

        try {
            logLineOutput.doOutputLogLine(logLineBuilder.buildLogLine(event));
        } catch (Exception e) {
            System.out.println("Exception while trying to log with FlexibleAppender: " + LoggingHelper.getExceptionInOneLine(e));
        }
    }


    public AbstractEnableable getEnableable() {
        return enableable;
    }

    public void setEnableable(AbstractEnableable enableable) {
        this.enableable = enableable;
    }
}