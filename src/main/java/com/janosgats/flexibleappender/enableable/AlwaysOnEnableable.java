package com.janosgats.flexibleappender.enableable;

public class AlwaysOnEnableable extends AbstractEnableable {
    @Override
    public boolean isEnabled() {
        return false;
    }
}
