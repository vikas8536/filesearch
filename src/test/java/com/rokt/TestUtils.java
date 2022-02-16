package com.rokt;

import com.rokt.application.FileSearchConfiguration;
import com.rokt.data.LoadFilesFromLocal;
import com.rokt.data.ReadFiles;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.helpers.RecordParser;
import com.rokt.helpers.SpaceSeparatedFileParser;
import org.joda.time.DateTime;
import org.mockito.Mockito;

import java.io.FileNotFoundException;

public class TestUtils {
    public static DateTimeHelper getDateTimeHelper(String format) {
        FileSearchConfiguration configuration = Mockito.mock(FileSearchConfiguration.class);
        Mockito.when(configuration.getDateTimeFormat()).thenReturn(format);
        return new DateTimeHelper(configuration);
    }
    public static ReadFiles getReadFiles() {
        FileSearchConfiguration configuration = Mockito.mock(FileSearchConfiguration.class);
        Mockito.when(configuration.getFilesLocation()).thenReturn("src/test/resources");
        return new LoadFilesFromLocal(configuration);
    }
    public static RecordParser getRecordParser(String format) {
        DateTimeHelper dateTimeHelper = getDateTimeHelper(format);
        return new SpaceSeparatedFileParser(dateTimeHelper);
    }
}
