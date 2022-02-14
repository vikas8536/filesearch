package com.rokt.service;

import com.rokt.model.internal.SearchRequest;
import com.rokt.model.internal.SearchResponse;

import java.io.IOException;
import java.util.List;

public interface SearchInFiles {
    List<SearchResponse> searchInFile(SearchRequest searchRequest) throws IOException;
}
