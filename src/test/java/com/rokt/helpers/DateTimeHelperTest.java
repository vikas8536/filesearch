package com.rokt.helpers;

import com.rokt.TestUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

public class DateTimeHelperTest {

    @Test
    public void convert_valid_string_to_date() {
        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        DateTimeHelper dateTimeHelper = TestUtils
                .getDateTimeHelper(format);
        String input = "2000-01-05T11:10:31Z";
        DateTime actualOutput = dateTimeHelper.convert(input);
        DateTime expectedOutput = DateTime.parse(input,
                DateTimeFormat.forPattern(format));

        assert actualOutput.isEqual(expectedOutput);
    }

    @Test (expected = IllegalArgumentException.class)
    public void convert_invalid_string_to_date() {
        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        DateTimeHelper dateTimeHelper = TestUtils
                .getDateTimeHelper(format);
        String input = "2000-01-05T11:10:Z";
        DateTime actualOutput = dateTimeHelper.convert(input);
        DateTime expectedOutput = DateTime.parse(input,
                DateTimeFormat.forPattern(format));

        assert actualOutput.isEqual(expectedOutput);
    }

    @Test
    public void convert_valid_date_to_string() {
        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        DateTimeHelper dateTimeHelper = TestUtils
                .getDateTimeHelper(format);
        String expectedOutput = "2000-01-05T11:10:31Z";
        DateTime inputDateTime = DateTime.parse(expectedOutput,
                DateTimeFormat.forPattern(format));
        String actualOutput = dateTimeHelper.convert(inputDateTime);

        assert actualOutput.equals(expectedOutput);
    }
}