package com.rokt.service;

import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;

import java.io.IOException;
import java.util.stream.Stream;

public abstract class SearchInFiles {

    public abstract Stream<Record> searchInFile(SearchRequest searchRequest) throws IOException;

    protected boolean isInRange(SearchRequest searchRequest, Record a) {
        return !isRecordBeforeSearchRange(searchRequest, a)
                && !isRecordAfterSearchRange(searchRequest, a);
    }

    protected boolean isRecordBeforeSearchRange(SearchRequest searchRequest, Record record) {
        return record.getDateTime().isBefore(searchRequest.getFromDateTime());
    }

    protected boolean isRecordAfterSearchRange(SearchRequest searchRequest, Record record) {
        return record.getDateTime().isAfter(searchRequest.getToDateTime());
    }
}
