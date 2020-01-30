package com.janosgats.flexibleappender.loglineoutput.specific;

import com.janosgats.flexibleappender.enableable.AbstractEnableable;
import com.janosgats.flexibleappender.loglinebuilder.AbstractLogLineBuilder;
import com.janosgats.flexibleappender.loglineoutput.AbstractLogLineOutput;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.Property;

import java.io.Serializable;

/**
 * Feel free to override one of these Appenders to create your own!
 */
public class ConsoleLogLineOutput extends AbstractLogLineOutput {

    @Override
    public void doOutputLogLine(String logLine) {
        System.out.println(logLine);
    }
}
