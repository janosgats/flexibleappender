package com.janosgats.logging.flexibleappender.loglineoutput.specific;

import com.janosgats.logging.flexibleappender.loglinebuilder.AbstractLogLineBuilder;
import com.janosgats.logging.flexibleappender.loglineoutput.AbstractLogLineOutput;
import org.apache.logging.log4j.core.LogEvent;

import java.util.Objects;

/**
 * Feel free to override one of these LogLineOutputs to create your own!
 */
public class StdOutLogLineOutput extends AbstractLogLineOutput {

    @Override
    public void doOutputLogLine(AbstractLogLineBuilder logLineBuilder, LogEvent logEvent) {
        Objects.requireNonNull(logLineBuilder);

        System.out.println(logLineBuilder.buildLogLine(logEvent));
    }
}
