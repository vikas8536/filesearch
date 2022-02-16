package com.rokt.helpers;

import com.rokt.exception.RecordFormatException;
import com.rokt.model.internal.Record;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.inject.Inject;

@AllArgsConstructor
@NoArgsConstructor
public class SpaceSeparatedFileParser implements RecordParser {

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public Record parse(String input) {
        String[] s = input.split(" ");
        if(s.length == 3) {
            DateTime dateTime = dateTimeHelper.convert(s[0]);
            return new Record(dateTime, s[1], s[2]);
        }
        throw new RecordFormatException("Error in parsing record from file!");
    }
}
