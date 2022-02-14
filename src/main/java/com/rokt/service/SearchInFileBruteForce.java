package com.rokt.service;

import com.google.inject.Inject;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;
import com.rokt.model.internal.SearchResponse;
import org.joda.time.DateTime;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchInFileBruteForce extends SearchInFiles{
    @Inject
    private ReadFiles readFiles;
    @Inject
    private FileParser fileParser;
    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public List<SearchResponse> searchInFile(SearchRequest searchRequest) throws FileNotFoundException {
        Stream<String> bufferedReaderStream = readFiles.readFileAsStream(searchRequest.getFileName());
        Stream<Record> recordStream = bufferedReaderStream.map(fileParser::parse);
        Stream<Record> records = searchInStream(recordStream, searchRequest);
        return records.parallel()
                .map(this::convertToSearchResponse)
                .collect(Collectors.toList());
    }

    Stream<Record> searchInStream(Stream<Record> input, SearchRequest searchRequest) {
        return input.filter(a -> isInRange(searchRequest, a))
                .sorted(Comparator.comparing(Record::getDateTime));
    }

    private boolean isInRange(SearchRequest searchRequest, Record a) {
        DateTime recordDateTime = a.getDateTime();
        DateTime fromDateTime = searchRequest.getFromDateTime();
        DateTime toDateTime = searchRequest.getToDateTime();
        return (recordDateTime.isAfter(fromDateTime)
                || recordDateTime.isEqual(toDateTime))
                &&
                (a.getDateTime().isBefore(searchRequest.getToDateTime())
                        ||a.getDateTime().isEqual(searchRequest.getToDateTime()));
    }

    private SearchResponse convertToSearchResponse(Record a) {
        String recordDateTime = dateTimeHelper.convert(a.getDateTime());
        return new SearchResponse(recordDateTime,
                a.getEmail(),
                a.getSessionId());
    }
}
