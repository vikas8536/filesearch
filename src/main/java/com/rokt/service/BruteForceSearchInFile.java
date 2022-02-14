package com.rokt.service;

import com.google.inject.Inject;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;
import com.rokt.model.internal.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
public class BruteForceSearchInFile extends SearchInFiles{
    @Inject
    private ReadFiles readFiles;
    @Inject
    private RecordParser fileParser;
    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public Stream<Record> searchInFile(SearchRequest searchRequest) throws FileNotFoundException {
        Stream<String> bufferedReaderStream = readFiles.readFileAsStream(searchRequest.getFileName());
        Stream<Record> recordStream = bufferedReaderStream.map(fileParser::parse);
        return searchInStream(recordStream, searchRequest);
    }

    Stream<Record> searchInStream(Stream<Record> input, SearchRequest searchRequest) {
        return input.filter(a -> isInRange(searchRequest, a))
                .sorted(Comparator.comparing(Record::getDateTime));
    }

    private SearchResponse convertToSearchResponse(Record a) {
        String recordDateTime = dateTimeHelper.convert(a.getDateTime());
        return new SearchResponse(recordDateTime,
                a.getEmail(),
                a.getSessionId());
    }
}
