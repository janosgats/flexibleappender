package com.janosgats.flexibleappender;

import com.janosgats.flexibleappender.enableable.AlwaysOnEnableable;
import com.janosgats.flexibleappender.loglinebuilder.specific.LocaldevConsoleLogLineBuilder;
import com.janosgats.flexibleappender.loglineoutput.specific.StdOutLogLineOutput;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Plugin(name = "TestAppender", category = "Core", elementType = "appender", printObject = true)
public class TestAppender extends FlexibleAppender {
    protected TestAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);

        AlwaysOnEnableable alwaysOnEnableable = new AlwaysOnEnableable();
        LocaldevConsoleLogLineBuilder localdevConsoleLogLineBuilder = new LocaldevConsoleLogLineBuilder(DateTimeFormatter.ofPattern("dd HH:mm:ss.SSS").withZone(ZoneId.of("UTC")), 2);
        StdOutLogLineOutput stdOutLogLineOutput = new StdOutLogLineOutput();

        super.setUpAppender(alwaysOnEnableable, localdevConsoleLogLineBuilder, stdOutLogLineOutput);
    }
}
