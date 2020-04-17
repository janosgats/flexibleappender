package com.janosgats.logging.flexibleappender.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileUtil {

    private FileUtil() {
    }

    public static File getFirstFileInDir(Path dir) {
        List<File> allFilesInDir = getAllFilesInDir(dir);
        if (allFilesInDir.isEmpty()) {
            throw new RuntimeException("No files in directory " + dir.toString());
        }
        return allFilesInDir.get(0);
    }

    public static List<File> getAllFilesInDir(Path dir) {
        Collection<File> files = FileUtils.listFiles(
                dir.toFile(),
                new RegexFileFilter("^(.*?)"),
                DirectoryFileFilter.DIRECTORY
        );
        return new ArrayList<>(files);
    }

}
