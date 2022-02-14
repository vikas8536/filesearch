package com.rokt.helpers;

import com.rokt.application.FileSearchConfiguration;
import com.rokt.model.api.PostRequest;
import com.rokt.model.internal.SearchRequest;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RequestValidationTest {
    RequestValidation requestValidation;
    DateTimeHelper dateTimeHelper;
    @Before
    public void setup() {
        FileSearchConfiguration configuration = Mockito.mock(FileSearchConfiguration.class);
        dateTimeHelper = new DateTimeHelper(configuration);
        Mockito.when(configuration.getDateTimeFormat()).thenReturn("yyyy-MM-dd'T'HH:mm:ss'Z'");
        requestValidation = new RequestValidation(dateTimeHelper);
    }
    @Test
    public void test_for_valid_fromdate() {
        String from = "2004-08-01T13:13:29Z";
        DateTime fromDateExpected = dateTimeHelper.convert(from);
        PostRequest postRequest = new PostRequest("sample1.txt", from,"2004-08-01T13:13:29Z");
        SearchRequest searchRequest = requestValidation.validate(postRequest);
        assert searchRequest.getFromDateTime().isEqual(fromDateExpected);
    }

    @Test (expected = IllegalArgumentException.class)
    public void test_for_invalid_fromdate() {
        String from = "2004-08-01T13:13";
        PostRequest postRequest = new PostRequest("sample1.txt", from,"2004-08-01T13:13:29Z");
        requestValidation.validate(postRequest);
    }
    @Test
    public void test_for_valid_todate() {
        String to = "2004-08-01T13:13:29Z";
        DateTime toDateExpected = dateTimeHelper.convert(to);
        PostRequest postRequest = new PostRequest("sample1.txt", "2004-08-01T13:13:29Z", to);
        SearchRequest searchRequest = requestValidation.validate(postRequest);
        assert searchRequest.getToDateTime().isEqual(toDateExpected);
    }

    @Test (expected = IllegalArgumentException.class)
    public void test_for_invalid_todate_throws_exception() {
        String to = "2004-08-01";
        PostRequest postRequest = new PostRequest("sample1.txt", "2004-08-01T13:13:29Z", to);
        requestValidation.validate(postRequest);
    }

    @Test
    public void test_fromdate_before_todate() {
        String from = "2004-08-01T12:13:29Z";
        String to = "2004-08-01T13:13:29Z";
        PostRequest postRequest = new PostRequest("sample1.txt", from,to);
        requestValidation.validate(postRequest);
    }
    @Test (expected = IllegalArgumentException.class)
    public void test_fromdate_after_todate_throws_exception() {
        String from = "2004-08-01T12:13:29Z";
        String to = "2004-08-01T11:13:29Z";
        PostRequest postRequest = new PostRequest("sample1.txt", from,to);
        requestValidation.validate(postRequest);
    }
}