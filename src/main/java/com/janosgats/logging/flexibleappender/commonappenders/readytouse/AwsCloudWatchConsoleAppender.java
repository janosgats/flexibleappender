package com.janosgats.logging.flexibleappender.commonappenders.readytouse;

import com.janosgats.logging.flexibleappender.FlexibleAppender;
import com.janosgats.logging.flexibleappender.commonappenders.CommonAppenderFactory;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@Plugin(name = "AwsCloudWatchConsoleAppender", category = "Core", elementType = "appender", printObject = true)
public class AwsCloudWatchConsoleAppender extends FlexibleAppender {
    public AwsCloudWatchConsoleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @PluginFactory
    public static AwsCloudWatchConsoleAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute(value = "enableableEnvironmentVariableName", defaultString = "LOGGING_ENABLE_LOCALDEV_CONSOLE_APPENDER") String enableableEnvironmentVariableName,
            @PluginAttribute(value = "enableableSystemPropertyName", defaultString = "LOGGING_ENABLE_LOCALDEV_CONSOLE_APPENDER") String enableableSystemPropertyName
    ) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        return (AwsCloudWatchConsoleAppender) new CommonAppenderFactory<AwsCloudWatchConsoleAppender>(AwsCloudWatchConsoleAppender.class)
                .usePluginFactoryParameters(name, layout, filter, null)
                .createForAwsCloudWatchConsole(enableableEnvironmentVariableName, enableableSystemPropertyName);
    }
}
