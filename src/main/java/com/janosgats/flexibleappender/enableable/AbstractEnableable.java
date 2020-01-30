package com.janosgats.flexibleappender.enableable;

import com.janosgats.flexibleappender.helper.LoggingHelper;

public abstract class AbstractEnableable {

    public abstract boolean isEnabled();

    /**
     * @return result of isEnable(). False if an exception occurs while executing isEnable().
     */
    public boolean tryToGetIsEnabled() {
        try {
            return isEnabled();
        } catch (Exception e) {
            System.out.println("Exception thrown while executing isEnable()! " + e.getMessage() + " stackTrace: " + LoggingHelper.getStackTraceAsString(e));
        }
        return false;
    }
}
