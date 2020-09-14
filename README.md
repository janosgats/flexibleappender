# Flexible Appender for extending Log4j functionality

#### Mix the four separated component of FlexibleAppender to create an appender that makes you happy!
The components: 
- Enableable (filter that runs when instantiating an appender)
- LogFilter (filter that runs for every log event)
- LogLineBuilder (constructs a loggable line)
- LogLineOutput (writes the log line into a stream/file/anything)

<br>
<br>

#### Maven import from central repository:
```
<dependencies>
      <dependency>
            <groupId>com.janosgats.logging</groupId>
            <artifactId>flexible-appender</artifactId>
      </dependency>
 </dependencies>
 ```

<br>
<br>

#### Factoring common appenders
To quickly create an appender for a common use case, you can use the `CommonAppenderFactory` class to spare some code in your repository:

```
@Plugin(name = "LocalDevConsoleAppender", category = "Core", elementType = "appender", printObject = true)
public class LocalDevConsoleAppender extends FlexibleAppender {
    public LocalDevConsoleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @PluginFactory
    public static LocalDevConsoleAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("otherAttribute") String otherAttribute) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        return (LocalDevConsoleAppender) new CommonAppenderFactory<>(LocalDevConsoleAppender.class)
                .usePluginFactoryParameters(name, layout, filter, null)
                .createForLocalDevConsole("LOGGING_ENABLE_LOCALDEV_CONSOLE_APPENDER","LOGGING_ENABLE_LOCALDEV_CONSOLE_APPENDER");
    }
}
```
