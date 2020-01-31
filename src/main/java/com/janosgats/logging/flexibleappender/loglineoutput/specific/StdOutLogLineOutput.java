package com.janosgats.logging.flexibleappender.loglineoutput.specific;

import com.janosgats.logging.flexibleappender.loglineoutput.AbstractLogLineOutput;

/**
 * Feel free to override one of these LogLineOutputs to create your own!
 */
public class StdOutLogLineOutput extends AbstractLogLineOutput {

    @Override
    public void doOutputLogLine(String logLine) {
        System.out.println(logLine);
    }
}
