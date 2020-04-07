package com.janosgats.logging.flexibleappender;

import com.janosgats.logging.flexibleappender.enableable.AbstractEnableable;
import com.janosgats.logging.flexibleappender.helper.LoggingHelper;
import com.janosgats.logging.flexibleappender.logfilter.AbstractLogFilter;
import com.janosgats.logging.flexibleappender.logfilter.AllowAllLogFilter;
import com.janosgats.logging.flexibleappender.loglinebuilder.AbstractLogLineBuilder;
import com.janosgats.logging.flexibleappender.loglineoutput.AbstractLogLineOutput;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;

import java.io.Serializable;

/**
 * Override this Appender with a constructor that calls super(), than configures the three elements by setUpAppender()!
 * <br>
 * Then inject your appender by the log4j2.xml in your project!
 */
public abstract class FlexibleAppender extends AbstractAppender {
    private boolean isCurrentlyEnabled;
    private AbstractEnableable enableable;
    private AbstractLogFilter logFilter;
    private AbstractLogLineBuilder logLineBuilder;
    private AbstractLogLineOutput logLineOutput;

    protected FlexibleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    public void setUpAppender(AbstractEnableable enableable, AbstractLogFilter logFilter, AbstractLogLineBuilder logLineBuilder, AbstractLogLineOutput logLineOutput) {
        this.enableable = enableable;
        this.logFilter = logFilter;
        this.logLineBuilder = logLineBuilder;
        this.logLineOutput = logLineOutput;

        setCurrentlyEnabledByExecutingEnableable();
    }

    public void setUpAppender(AbstractEnableable enableable, AbstractLogLineBuilder logLineBuilder, AbstractLogLineOutput logLineOutput) {
        setUpAppender(enableable, new AllowAllLogFilter(), logLineBuilder, logLineOutput);
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

        if (!logFilter.shouldLogEventBeLogged(event))
            return;

        try {
            logLineOutput.doOutputLogLine(logLineBuilder, event);
        } catch (Exception e) {
            System.out.println("Exception while trying to log with FlexibleAppender: " + LoggingHelper.getExceptionInOneLine(e));
        }
    }


    public AbstractEnableable getEnableable() {
        return enableable;
    }

    public void setAndExecuteEnableable(AbstractEnableable enableable) {
        this.enableable = enableable;
        setCurrentlyEnabledByExecutingEnableable();
    }
}
