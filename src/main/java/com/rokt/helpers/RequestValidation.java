package com.rokt.helpers;

import com.google.inject.Inject;
import com.rokt.model.api.PostRequest;
import com.rokt.model.internal.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@NoArgsConstructor
@AllArgsConstructor
public class RequestValidation {
    @Inject
    private DateTimeHelper dateTimeHelper;

    public SearchRequest validate(PostRequest request) throws IllegalArgumentException{
        String fileName = request.getFilename();
        String from = request.getFrom();
        String to = request.getTo();
        DateTime fromDateTime = dateTimeHelper.convert(from);
        DateTime toDateTime = dateTimeHelper.convert(to);

        if(toDateTime.isBefore(fromDateTime)) throw new IllegalArgumentException("Invalid Time Range");

        return new SearchRequest(fileName, fromDateTime,
                toDateTime);
    }
}
