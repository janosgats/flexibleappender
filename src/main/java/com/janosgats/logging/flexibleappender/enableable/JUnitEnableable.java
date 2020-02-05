package com.janosgats.logging.flexibleappender.enableable;

public class JUnitEnableable extends AbstractEnableable {
    @Override
    public boolean isEnabled() {
        return isJUnitTestInProgress();
    }

    private static boolean isJUnitTestInProgress() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
