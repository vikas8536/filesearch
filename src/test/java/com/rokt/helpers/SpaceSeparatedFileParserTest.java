package com.rokt.helpers;

import com.rokt.TestUtils;
import com.rokt.exception.RecordFormatException;
import com.rokt.model.internal.Record;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class SpaceSeparatedFileParserTest {
    SpaceSeparatedFileParser spaceSeparatedFileParser;
    DateTimeHelper dateTimeHelper;
    @Before
    public void setup() {

        dateTimeHelper = TestUtils.getDateTimeHelper("yyyy-MM-dd'T'HH:mm:ss'Z'");
        spaceSeparatedFileParser = new SpaceSeparatedFileParser(dateTimeHelper);
    }
    @Test
    public void parse_valid_record() throws RecordFormatException {
        String recordString = "2000-01-01T17:25:49Z dedric_strosin@adams.co.uk dfad33e7-f734-4f70-af29-c42f2b467142";
        Record record = spaceSeparatedFileParser.parse(recordString);
        DateTime expectedDateTime = dateTimeHelper.convert("2000-01-01T17:25:49Z");
        String expectedEmail = "dedric_strosin@adams.co.uk";
        String expectedSessionId = "dfad33e7-f734-4f70-af29-c42f2b467142";

        assert record.getDateTime().isEqual(expectedDateTime);
        assert record.getEmail().equals(expectedEmail);
        assert record.getSessionId().equals(expectedSessionId);
    }

    @Test (expected = RecordFormatException.class)
    public void parse_invalid_delimited_record() {
        String recordString = "2000-01-01T17:25:49Z\tdedric_strosin@adams.co.uk\tdfad33e7-f734-4f70-af29-c42f2b467142";
        spaceSeparatedFileParser.parse(recordString);
    }
}