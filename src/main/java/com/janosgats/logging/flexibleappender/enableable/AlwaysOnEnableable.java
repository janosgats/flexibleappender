package com.janosgats.logging.flexibleappender.enableable;

/**
 * This Enabler is always enabled.
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public class AlwaysOnEnableable extends AbstractEnableable {
    @Override
    public boolean isEnabled() {
        return true;
    }
}
