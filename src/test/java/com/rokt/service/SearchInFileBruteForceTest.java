package com.rokt.service;

import com.rokt.helpers.DateTimeHelper;
import com.rokt.helpers.RequestValidation;
import com.rokt.model.api.PostRequest;
import com.rokt.model.internal.SearchRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

public class SearchInFileBruteForceTest {
    SearchInFileBruteForce searchInFileBruteForce;

    @Before
    public void setUp() throws Exception {
        searchInFileBruteForce = new SearchInFileBruteForce();
        SearchInFileBruteForce spy = Mockito.spy(searchInFileBruteForce);
    }

    @Test
    public void testSearchInFile() throws IOException {
        DateTimeHelper dateTimeHelper = new DateTimeHelper();

        SearchRequest searchRequest = new SearchRequest("sample1.txt",
                dateTimeHelper.convert("2004-08-01T13:13:29Z"),
                dateTimeHelper.convert("2004-09-01T13:13:29Z"));
        searchInFileBruteForce.searchInFile(searchRequest);
    }
}