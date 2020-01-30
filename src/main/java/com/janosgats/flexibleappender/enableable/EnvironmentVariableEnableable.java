package com.janosgats.flexibleappender.enableable;

/**
 * Set the corresponding Environment Variable's value to {@code true} to enable!
 * <br>
 * Feel free to override one of these Enableables to create your own!
 */
public class EnvironmentVariableEnableable extends AbstractEnableable {

    private String environmentVariableName;

    public EnvironmentVariableEnableable(String environmentVariableName) {
        this.environmentVariableName = environmentVariableName;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.parseBoolean(System.getenv(environmentVariableName));
    }

    public String getEnvironmentVariableName() {
        return environmentVariableName;
    }

    public void setEnvironmentVariableName(String environmentVariableName) {
        this.environmentVariableName = environmentVariableName;
    }
}
