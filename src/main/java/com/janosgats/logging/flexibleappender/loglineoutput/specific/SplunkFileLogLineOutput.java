package com.janosgats.logging.flexibleappender.loglineoutput.specific;

import com.janosgats.logging.flexibleappender.loglineoutput.AbstractLogLineOutput;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Feel free to override one of these LogLineOutputs to create your own!
 */
public class SplunkFileLogLineOutput extends AbstractLogLineOutput {
    protected final String logDirectoryBasePath;
    protected final String logSourceType;
    protected final TimeZone timeZoneForFilePathUpdate;

    protected File logFile;

    protected SplunkFileLogLineOutput(String logDirectoryBasePath, String logSourceType, TimeZone timeZoneForFilePathUpdate) {
        this.logDirectoryBasePath = logDirectoryBasePath;
        this.logSourceType = logSourceType;
        this.timeZoneForFilePathUpdate = timeZoneForFilePathUpdate;
    }

    @Override
    public void doOutputLogLine(String logLine) {
        updateSplunkLogFilePath();
        try {
            FileUtils.writeStringToFile(logFile, logLine, Charset.defaultCharset(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Override this to play with file names!
     */
    protected void updateSplunkLogFilePath() {
        Calendar calendar = Calendar.getInstance(timeZoneForFilePathUpdate);

        Path filePath = Paths.get(logDirectoryBasePath,
                logSourceType, String.valueOf(calendar.get(Calendar.YEAR)) + String.valueOf(calendar.get(Calendar.MONTH) + 1),
                logSourceType + "-" + calendar.get(Calendar.DAY_OF_MONTH) + ".log");

        if (logFile == null || !logFile.getAbsolutePath().equals(filePath.toString()))
            logFile = new File(filePath.toString());
    }

    public String getLogDirectoryBasePath() {
        return logDirectoryBasePath;
    }

    public String getLogSourceType() {
        return logSourceType;
    }

    public TimeZone getTimeZoneForFilePathUpdate() {
        return timeZoneForFilePathUpdate;
    }
}
