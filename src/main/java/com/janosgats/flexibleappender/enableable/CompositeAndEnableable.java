package com.janosgats.flexibleappender.enableable;

public class CompositeAndEnableable extends CompositeEnableable {
    @Override
    public boolean isEnabled() {
        for (AbstractEnableable enableable : abstractEnableables) {
            if (!enableable.isEnabled())
                return false;
        }
        return true;
    }
}
