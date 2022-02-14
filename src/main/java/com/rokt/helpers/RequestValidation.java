package com.rokt.helpers;

import com.google.inject.Inject;
import com.rokt.model.api.PostRequest;
import com.rokt.model.internal.SearchRequest;

public class RequestValidation {
    @Inject
    private DateTimeHelper dateTimeHelper;

    public SearchRequest validateAndParsePostRequest(PostRequest request) throws IllegalArgumentException{
        String fileName = request.getFilename();
        String from = request.getFrom();
        String to = request.getTo();
        SearchRequest searchRequest = new SearchRequest(fileName,
                dateTimeHelper.convert(from),
                dateTimeHelper.convert(to));
        return searchRequest;
    }
}
