package com.rokt.service;

import com.rokt.helpers.DateTimeHelper;
import com.rokt.model.internal.Record;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
            return new Record(dateTimeHelper.convert(s[0]), s[1], s[2]);
        }
        return null;
    }
}
