package com.rokt.service;

import com.google.inject.Inject;
import com.rokt.data.ReadFiles;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;

import java.io.IOException;
import java.util.stream.Stream;

public abstract class SearchInFiles {
    @Inject
    protected ReadFiles readFiles;
    @Inject
    protected RecordParser fileParser;
    @Inject
    protected DateTimeHelper dateTimeHelper;

    public abstract Stream<Record> searchInFile(SearchRequest searchRequest) throws IOException;

    protected boolean isInRange(SearchRequest searchRequest, Record a) {
        return !isRecordBeforeSearchRange(searchRequest, a)
                && !isRecordAfterSearchRange(searchRequest, a);
    }

    private boolean isRecordBeforeSearchRange(SearchRequest searchRequest, Record record) {
        return record.getDateTime().isBefore(searchRequest.getFromDateTime());
    }

    private boolean isRecordAfterSearchRange(SearchRequest searchRequest, Record record) {
        return record.getDateTime().isAfter(searchRequest.getToDateTime());
    }
}
