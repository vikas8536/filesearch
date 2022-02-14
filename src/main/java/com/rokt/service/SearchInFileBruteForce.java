package com.rokt.service;

import com.google.inject.Inject;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.model.internal.SearchRequest;
import com.rokt.model.internal.SearchResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchInFileBruteForce implements SearchInFiles{
    @Inject
    private LoadFiles loadFiles;
    @Inject
    private FileParser fileParser;
    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public List<SearchResponse> searchInFile(SearchRequest searchRequest) throws IOException {
        InputStream inputStream = loadFiles.readFiles(searchRequest.getFileName());
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        return br.lines().map(fileParser::parse)
                .filter(a -> a.getDateTime().isAfter(searchRequest.getFromDateTime())
                        && a.getDateTime().isBefore(searchRequest.getToDateTime()))
                .map(a -> new SearchResponse(dateTimeHelper.convert(a.getDateTime()),a.getEmail(),a.getSessionId()))
                .sorted(Comparator.comparing(SearchResponse::getEventTime))
                .collect(Collectors.toList());
    }
}
