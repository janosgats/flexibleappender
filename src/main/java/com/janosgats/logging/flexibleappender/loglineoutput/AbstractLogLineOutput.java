package com.janosgats.logging.flexibleappender.loglineoutput;

import com.janosgats.logging.flexibleappender.loglinebuilder.AbstractLogLineBuilder;
import org.apache.logging.log4j.core.LogEvent;

/**
 * Feel free to override one of these LogLineOutputs to create your own!
 */
public abstract class AbstractLogLineOutput {
    public abstract void doOutputLogLine(AbstractLogLineBuilder logLineBuilder, LogEvent logEvent);

    public final void doOutputLogLine(AbstractLogLineBuilder logLineBuilder) {
        doOutputLogLine(logLineBuilder, null);
    }
}
