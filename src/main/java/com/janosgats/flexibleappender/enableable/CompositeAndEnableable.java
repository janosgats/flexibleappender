package com.janosgats.flexibleappender.enableable;

/**
 * Execute multiple enablers with a logical AND by using this enabler!
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
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
