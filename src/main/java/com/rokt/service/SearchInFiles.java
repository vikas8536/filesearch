package com.rokt.service;

import com.google.inject.Inject;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;
import com.rokt.model.internal.SearchResponse;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public abstract class SearchInFiles {
    @Inject
    private ReadFiles readFiles;
    @Inject
    private FileParser fileParser;
    @Inject
    private DateTimeHelper dateTimeHelper;

    public abstract List<SearchResponse> searchInFile(SearchRequest searchRequest) throws IOException;

    private boolean isInRange(SearchRequest searchRequest, Record a) {
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
