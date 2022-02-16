package com.rokt.service;

import com.google.inject.Inject;
import com.rokt.data.ReadFiles;
import com.rokt.helpers.RecordParser;
import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
public class BruteForceSearchInFile extends SearchInFiles{
    @Inject
    protected ReadFiles readFiles;
    @Inject
    protected RecordParser fileParser;

    @Override
    public Stream<Record> searchInFile(SearchRequest searchRequest) throws FileNotFoundException {
        Stream<String> bufferedReaderStream = readFiles.readFileAsStream(searchRequest.getFileName());
        Stream<Record> recordStream = bufferedReaderStream.map(input -> fileParser.parse(input));
        return searchInStream(recordStream, searchRequest);
    }

    private Stream<Record> searchInStream(Stream<Record> input, SearchRequest searchRequest) {
        return input.filter(a -> isInRange(searchRequest, a))
                .sorted(Comparator.comparing(Record::getDateTime));
    }
}
