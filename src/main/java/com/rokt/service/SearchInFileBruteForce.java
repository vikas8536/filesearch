package com.rokt.service;

import com.google.inject.Inject;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.model.internal.FileRecord;
import com.rokt.model.internal.SearchRequest;
import com.rokt.model.internal.SearchResponse;
import org.joda.time.DateTime;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchInFileBruteForce implements SearchInFiles{
    @Inject
    private ReadFiles readFiles;
    @Inject
    private FileParser fileParser;
    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public List<SearchResponse> searchInFile(SearchRequest searchRequest) throws IOException {
        Stream<String> bufferedReaderStream = readFiles.readFileAsStream(searchRequest.getFileName());
        return searchInStream(bufferedReaderStream, searchRequest);
    }

    private List<SearchResponse> searchInStream(Stream<String> filesAsStream, SearchRequest searchRequest) {
        return filesAsStream.map(fileParser::parse)
                .filter(a -> isInRange(searchRequest, a))
                .map(this::convertToSearchResponse)
                .sorted(Comparator.comparing(SearchResponse::getEventTime))
                .collect(Collectors.toList());
    }

    private boolean isInRange(SearchRequest searchRequest, FileRecord a) {
        DateTime recordDateTime = a.getDateTime();
        DateTime fromDateTime = searchRequest.getFromDateTime();
        DateTime toDateTime = searchRequest.getToDateTime();
        return (recordDateTime.isAfter(fromDateTime)
                || recordDateTime.isEqual(toDateTime))
                &&
                (a.getDateTime().isBefore(searchRequest.getToDateTime())
                ||a.getDateTime().isEqual(searchRequest.getToDateTime()));
    }

    private SearchResponse convertToSearchResponse(FileRecord a) {
        return new SearchResponse(dateTimeHelper.convert(a.getDateTime()), a.getEmail(), a.getSessionId());
    }
}
