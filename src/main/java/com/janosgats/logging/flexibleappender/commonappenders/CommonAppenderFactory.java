package com.janosgats.logging.flexibleappender.commonappenders;

import com.janosgats.logging.flexibleappender.FlexibleAppender;
import com.janosgats.logging.flexibleappender.enableable.CompositeOrEnableable;
import com.janosgats.logging.flexibleappender.enableable.EnvironmentVariableEnableable;
import com.janosgats.logging.flexibleappender.enableable.JUnitEnableable;
import com.janosgats.logging.flexibleappender.enableable.SystemPropertyEnableable;
import com.janosgats.logging.flexibleappender.exception.AppenderFactoryException;
import com.janosgats.logging.flexibleappender.logfilter.AbstractLogFilter;
import com.janosgats.logging.flexibleappender.logfilter.AllowAllLogFilter;
import com.janosgats.logging.flexibleappender.loglinebuilder.AbstractLogLineBuilder;
import com.janosgats.logging.flexibleappender.loglinebuilder.specific.CloudOneLineConsoleLogLineBuilder;
import com.janosgats.logging.flexibleappender.loglinebuilder.specific.JsonLogLineBuilder;
import com.janosgats.logging.flexibleappender.loglinebuilder.specific.LocaldevConsoleLogLineBuilder;
import com.janosgats.logging.flexibleappender.loglinebuilder.specific.SplunkLogLineBuilder;
import com.janosgats.logging.flexibleappender.loglineoutput.AbstractLogLineOutput;
import com.janosgats.logging.flexibleappender.loglineoutput.specific.SplunkFileLogLineOutput;
import com.janosgats.logging.flexibleappender.loglineoutput.specific.StdOutLogLineOutput;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public class CommonAppenderFactory<T extends FlexibleAppender> {
    private Constructor<T> appenderClassConstructor;

    private String name;
    private Filter filter;
    private Layout<? extends Serializable> layout;
    private Property[] otherProperties;

    /**
     * Instantiates the factory with the class of Appender it should produce.
     *
     * @param appenderClassToCreate
     * @throws NoSuchMethodException
     */
    public CommonAppenderFactory(Class<T> appenderClassToCreate) throws NoSuchMethodException {
        appenderClassConstructor = appenderClassToCreate.getDeclaredConstructor(String.class, Filter.class, Layout.class, boolean.class, Property[].class);
    }

    /**
     * Sets the factory's params from log4j's {@code @PluginFactory} annotated factory method.
     *
     * @param name       value of parameter originally annotated by @PluginAttribute("name")
     * @param filter     value of parameter originally annotated by @PluginElement("Filter")
     * @param layout     value of parameter originally annotated by @PluginElement("Layout")
     * @param properties extra properties for the appender
     */
    public CommonAppenderFactory usePluginFactoryParameters(String name, Layout<? extends Serializable> layout, final Filter filter, Property[] properties) {
        if (name == null) {
            throw new AppenderFactoryException("Appender name cannot be null!");
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();//A layout has to be provided to instantiate an appender
        }
        if (properties == null) {
            properties = new Property[0];
        }

        this.name = name;
        this.layout = layout;
        this.filter = filter;
        this.otherProperties = properties;

        return this;
    }

    public T createForKafkaLogsConsole(String appName, String enableableEnvironmentVariableName, String enableableSystemPropertyName) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Objects.requireNonNull(appName, "appName shouldn't be null!");

        CompositeOrEnableable compositeOrEnableable = new CompositeOrEnableable();
        compositeOrEnableable.getAbstractEnableables().add(new EnvironmentVariableEnableable(enableableEnvironmentVariableName));
        compositeOrEnableable.getAbstractEnableables().add(new SystemPropertyEnableable(enableableSystemPropertyName));

        AbstractLogFilter logFilter = new AllowAllLogFilter();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("UTC"));
        Map<String, String> kafkaAdditionalParameters = new HashMap<>();
        kafkaAdditionalParameters.put("appname", appName);
        AbstractLogLineBuilder logLineBuilder = new JsonLogLineBuilder(dateTimeFormatter, kafkaAdditionalParameters);

        AbstractLogLineOutput logLineOutput = new StdOutLogLineOutput();

        T flexibleAppender = appenderClassConstructor.newInstance(this.name, this.filter, this.layout, false, this.otherProperties);
        flexibleAppender.setUpAppender(compositeOrEnableable, logFilter, logLineBuilder, logLineOutput);
        return flexibleAppender;
    }

    public T createForLocalDevConsole(String enableableEnvironmentVariableName, String enableableSystemPropertyName) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return createForLocalDevConsole(enableableEnvironmentVariableName, enableableSystemPropertyName, 2);
    }

    public T createForLocalDevConsole(String enableableEnvironmentVariableName, String enableableSystemPropertyName, int loggerNameLeaveAsIsLevel) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        CompositeOrEnableable compositeOrEnableable = new CompositeOrEnableable();
        compositeOrEnableable.getAbstractEnableables().add(new EnvironmentVariableEnableable(enableableEnvironmentVariableName));
        compositeOrEnableable.getAbstractEnableables().add(new SystemPropertyEnableable(enableableSystemPropertyName));
        compositeOrEnableable.getAbstractEnableables().add(new JUnitEnableable());

        AbstractLogFilter logFilter = new AllowAllLogFilter();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());
        AbstractLogLineBuilder logLineBuilder = new LocaldevConsoleLogLineBuilder(dateTimeFormatter, loggerNameLeaveAsIsLevel);

        AbstractLogLineOutput logLineOutput = new StdOutLogLineOutput();

        T flexibleAppender = appenderClassConstructor.newInstance(this.name, this.filter, this.layout, false, this.otherProperties);
        flexibleAppender.setUpAppender(compositeOrEnableable, logFilter, logLineBuilder, logLineOutput);
        return flexibleAppender;
    }

    public T createForSplunkFile(String enableableEnvironmentVariableName, String enableableSystemPropertyName,
                                 String logDirectoryBasePath, String logSourceType) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Objects.requireNonNull(logDirectoryBasePath, "logDirectoryBasePath shouldn't be null!");
        Objects.requireNonNull(logSourceType, "logSourceType shouldn't be null!");

        CompositeOrEnableable compositeOrEnableable = new CompositeOrEnableable();
        compositeOrEnableable.getAbstractEnableables().add(new EnvironmentVariableEnableable(enableableEnvironmentVariableName));
        compositeOrEnableable.getAbstractEnableables().add(new SystemPropertyEnableable(enableableSystemPropertyName));

        AbstractLogFilter logFilter = new AllowAllLogFilter();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.SSS 'GMT'").withZone(ZoneId.of("GMT"));
        AbstractLogLineBuilder logLineBuilder = new SplunkLogLineBuilder(logSourceType, dateTimeFormatter);

        AbstractLogLineOutput logLineOutput = new SplunkFileLogLineOutput(logDirectoryBasePath, logSourceType, TimeZone.getTimeZone(dateTimeFormatter.getZone()));

        T flexibleAppender = appenderClassConstructor.newInstance(this.name, this.filter, this.layout, false, this.otherProperties);
        flexibleAppender.setUpAppender(compositeOrEnableable, logFilter, logLineBuilder, logLineOutput);
        return flexibleAppender;
    }

    public T createForAwsCloudWatchConsole(String enableableEnvironmentVariableName, String enableableSystemPropertyName) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        CompositeOrEnableable compositeOrEnableable = new CompositeOrEnableable();
        compositeOrEnableable.getAbstractEnableables().add(new EnvironmentVariableEnableable(enableableEnvironmentVariableName));
        compositeOrEnableable.getAbstractEnableables().add(new SystemPropertyEnableable(enableableSystemPropertyName));

        AbstractLogFilter logFilter = new AllowAllLogFilter();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("UTC"));
        AbstractLogLineBuilder logLineBuilder = new CloudOneLineConsoleLogLineBuilder(dateTimeFormatter);

        AbstractLogLineOutput logLineOutput = new StdOutLogLineOutput();

        T flexibleAppender = appenderClassConstructor.newInstance(this.name, this.filter, this.layout, false, this.otherProperties);
        flexibleAppender.setUpAppender(compositeOrEnableable, logFilter, logLineBuilder, logLineOutput);
        return flexibleAppender;
    }
}
