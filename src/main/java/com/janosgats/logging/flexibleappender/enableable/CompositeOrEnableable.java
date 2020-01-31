package com.janosgats.logging.flexibleappender.enableable;

/**
 * Execute multiple enablers with a logical OR by using this enabler!
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
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
