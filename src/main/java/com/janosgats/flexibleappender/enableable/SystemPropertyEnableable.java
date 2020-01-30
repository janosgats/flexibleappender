package com.janosgats.flexibleappender.enableable;

/**
 * Set the corresponding System Property's value to {@code "true"} to enable!
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public class SystemPropertyEnableable extends AbstractEnableable {

    private String systemPropertyName;

    public SystemPropertyEnableable(String systemPropertyName) {
        this.systemPropertyName = systemPropertyName;
    }

    @Override
    public boolean isEnabled() {
        return "true".equals(System.getProperty(getSystemPropertyName()));
    }

    public String getSystemPropertyName() {
        return systemPropertyName;
    }

    public void setSystemPropertyName(String systemPropertyName) {
        this.systemPropertyName = systemPropertyName;
    }
}
