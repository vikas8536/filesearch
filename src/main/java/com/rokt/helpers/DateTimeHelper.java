package com.rokt.helpers;

import com.rokt.application.FileSearchConfiguration;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.inject.Inject;


public class DateTimeHelper {

    @Inject
    FileSearchConfiguration configuration;

    public DateTime convert(String dateTime) {
       return DateTime.parse(dateTime, DateTimeFormat.forPattern(configuration.getDateTimeFormat()));
    }

    public String convert(DateTime dateTime) {
        return dateTime.toString(configuration.getDateTimeFormat());
    }
}
