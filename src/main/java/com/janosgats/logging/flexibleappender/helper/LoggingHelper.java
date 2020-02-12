package com.janosgats.logging.flexibleappender.helper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

public class LoggingHelper {
    private static final String errorFormat = "%s: %s";

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stackTrace = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stackTrace));
        return stackTrace.toString();
    }

    public static String getExceptionInOneLine(Throwable exception) {
        Gson gson = new Gson();
        JsonObject jsonRoot = new JsonObject();
        jsonRoot.add("errors", unwindCausesToList(exception));

        return gson.toJson(jsonRoot);
    }

    public static JsonObject getExceptionAsJsonDump(Throwable exception) {
        JsonObject jsonRoot = new JsonObject();
        jsonRoot.add("exception", unwindCausesToList(exception));
        return jsonRoot;
    }

    /**
     * @param exception Throwable with possible causes
     * @return List of descriptor objects of the exception and causes
     */
    protected static JsonArray unwindCausesToList(Throwable exception) {
        JsonArray errors = new JsonArray();
        while (exception != null) {
            addExceptionToJsonList(exception, errors);
            exception = exception.getCause();
        }
        return errors;
    }

    protected static void addExceptionToJsonList(Throwable exception, JsonArray list) {
        String errorMessage = String.format(errorFormat, exception.getClass().getName(), exception.getMessage());
        JsonArray traceJson = new JsonArray();
        Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).forEach(traceJson::add);
        JsonObject descriptorObject = new JsonObject();
        descriptorObject.add("error", new JsonPrimitive(errorMessage));
        descriptorObject.add("trace", traceJson);
        list.add(descriptorObject);
    }

    /**
     * Formats a dot separated (canonical) java class name and appends it to {@code logBuilder}.
     * <br><br>
     * <b>Formatting rules</b>:
     * <ul>
     * <li>Leaves the last {@code leaveAsIsLevel} name elements as is.</li>
     * <li>Shortens the remaining package names to their first 3 characters.</li>
     * </ul>
     *
     * @param loggerName     Fully qualified class name
     * @param leaveAsIsLevel Count of the last name elements to leave unshortened ({@code leaveAsIsLevel >= 0})
     * @param logBuilder     StringBuilder to append logger name to
     */
    public static void appendFormattedLoggerNameToStringBuilder(String loggerName, int leaveAsIsLevel, StringBuilder logBuilder) {
        String[] nameParts = loggerName.split("\\.");

        int i = 0;
        for (; i < nameParts.length - leaveAsIsLevel; ++i) {
            logBuilder.append(nameParts[i].charAt(0));

            if (nameParts[i].length() >= 2)
                logBuilder.append(nameParts[i].charAt(1));

            if (nameParts[i].length() >= 3)
                logBuilder.append(nameParts[i].charAt(2));

            if (i < nameParts.length - 1)
                logBuilder.append(".");
        }

        for (; i < nameParts.length - 1; ++i) {
            logBuilder
                    .append(nameParts[i])
                    .append(".");
        }

        if (i < nameParts.length)
            logBuilder
                    .append(nameParts[i]);
    }
}
