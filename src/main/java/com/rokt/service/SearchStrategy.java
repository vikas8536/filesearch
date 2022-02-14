package com.rokt.service;

import com.rokt.model.internal.SearchRequest;
import com.rokt.model.internal.SearchResponse;

import java.io.IOException;
import java.util.List;

public interface SearchStrategy<T> {
    List<SearchResponse> searchInStream(T input, SearchRequest searchRequest) throws IOException;
}
