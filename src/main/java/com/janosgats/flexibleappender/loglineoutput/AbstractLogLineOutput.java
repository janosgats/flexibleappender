package com.janosgats.flexibleappender.loglineoutput;

/**
 * Feel free to override one of these LogLineOutputs to create your own!
 */
public abstract class AbstractLogLineOutput {
    public abstract void doOutputLogLine(String logLine);
}
