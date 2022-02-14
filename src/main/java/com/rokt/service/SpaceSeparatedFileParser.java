package com.rokt.service;

import com.rokt.helpers.DateTimeHelper;
import com.rokt.model.internal.FileRecord;

import javax.inject.Inject;

public class SpaceSeparatedFileParser implements FileParser{

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public FileRecord parse(String input) {
        String[] s = input.split(" ");
        if(s.length == 3) {
            return new FileRecord(dateTimeHelper.convert(s[0]), s[1], s[2]);
        }
        return null;
    }
}
