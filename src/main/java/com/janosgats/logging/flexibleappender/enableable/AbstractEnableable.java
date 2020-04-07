package com.janosgats.logging.flexibleappender.enableable;

/**
 * By default, Enableables are evaluated only once, when the Appender is constructed.
 *
 * Feel free to override one of these Enableables to create your own!
 */
public abstract class AbstractEnableable {

    public abstract boolean isEnabled();
}
