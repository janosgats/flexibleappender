package com.janosgats.flexibleappender.enableable;

public class CompositeOrEnableable extends CompositeEnableable {
    @Override
    public boolean isEnabled() {
        for (AbstractEnableable enableable : abstractEnableables) {
            if (enableable.isEnabled())
                return true;
        }
        return false;
    }
}
