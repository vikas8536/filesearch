package com.rokt.service;

import com.rokt.TestUtils;
import com.rokt.data.ReadFiles;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.helpers.RecordParser;
import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BinarySearchInFileTest {
    BinarySearchInFile searchInFile;
    String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    @Before
    public void setUp() {
        ReadFiles readFiles = TestUtils.getReadFiles();
        RecordParser recordParser = TestUtils.getRecordParser(format);
        searchInFile = new BinarySearchInFile(readFiles, recordParser);
    }

    @Test
    public void test_search_inrange_startdate_and_inrange_enddate_in_file() throws IOException {
        DateTimeHelper dateTimeHelper = TestUtils.getDateTimeHelper(format);
        SearchRequest searchRequest = new SearchRequest("sample0.txt",
                dateTimeHelper.convert("2000-01-01T23:59:04Z"),
                dateTimeHelper.convert("2000-01-02T21:00:55Z"));
        Stream<Record> recordStream = searchInFile.searchInFile(searchRequest);
        List<Record> actualOutput = recordStream.collect(Collectors.toList());
        assert actualOutput.size() == 3;
        assert actualOutput.get(0).getEmail().equals("abner@bartolettihills.com");
        assert actualOutput.get(2).getEmail().equals("casey.eichmann@hayes.us");
    }

    @Test
    public void test_search_lower_outofrange_in_file() throws IOException {
        DateTimeHelper dateTimeHelper = TestUtils.getDateTimeHelper(format);
        SearchRequest searchRequest = new SearchRequest("sample0.txt",
                dateTimeHelper.convert("1999-01-01T23:59:04Z"),
                dateTimeHelper.convert("1999-01-02T21:00:55Z"));
        Stream<Record> recordStream = searchInFile.searchInFile(searchRequest);
        List<Record> actualOutput = recordStream.collect(Collectors.toList());
        assert actualOutput.size() == 0;
    }

    @Test
    public void test_search_higher_outofrange_in_file() throws IOException {
        DateTimeHelper dateTimeHelper = TestUtils.getDateTimeHelper(format);
        SearchRequest searchRequest = new SearchRequest("sample0.txt",
                dateTimeHelper.convert("2999-01-01T23:59:04Z"),
                dateTimeHelper.convert("2999-01-02T21:00:55Z"));
        Stream<Record> recordStream = searchInFile.searchInFile(searchRequest);
        List<Record> actualOutput = recordStream.collect(Collectors.toList());
        assert actualOutput.size() == 0;
    }

    @Test
    public void test_search_outofrange_start_date_and_inrange_end_date_in_file() throws IOException {
        DateTimeHelper dateTimeHelper = TestUtils.getDateTimeHelper(format);
        SearchRequest searchRequest = new SearchRequest("sample0.txt",
                dateTimeHelper.convert("1999-01-01T23:59:04Z"),
                dateTimeHelper.convert("2000-01-02T21:00:55Z"));
        Stream<Record> recordStream = searchInFile.searchInFile(searchRequest);
        List<Record> actualOutput = recordStream.collect(Collectors.toList());
        assert actualOutput.size() == 4;
        assert actualOutput.get(0).getEmail().equals("dedric_strosin@adams.co.uk");
        assert actualOutput.get(3).getEmail().equals("casey.eichmann@hayes.us");
    }

    @Test
    public void test_search_inrange_start_date_and_outofrange_end_date_in_file() throws IOException {
        DateTimeHelper dateTimeHelper = TestUtils.getDateTimeHelper(format);
        SearchRequest searchRequest = new SearchRequest("sample0.txt",
                dateTimeHelper.convert("2000-01-10T14:52:25Z"),
                dateTimeHelper.convert("2001-01-10T14:52:25Z"));
        Stream<Record> recordStream = searchInFile.searchInFile(searchRequest);
        List<Record> actualOutput = recordStream.collect(Collectors.toList());
        assert actualOutput.size() == 3;
        assert actualOutput.get(0).getEmail().equals("jana@zboncak.ca");
        assert actualOutput.get(2).getEmail().equals("cassandre.okeefe@schuppe.ca");
    }

    @Test
    public void test_search_start_date_and_end_date_supersets_range_in_file() throws IOException {
        DateTimeHelper dateTimeHelper = TestUtils.getDateTimeHelper(format);
        SearchRequest searchRequest = new SearchRequest("sample0.txt",
                dateTimeHelper.convert("1999-01-10T14:52:25Z"),
                dateTimeHelper.convert("2001-01-10T14:52:25Z"));
        Stream<Record> recordStream = searchInFile.searchInFile(searchRequest);
        List<Record> actualOutput = recordStream.collect(Collectors.toList());
        assert actualOutput.size() == 20;
        assert actualOutput.get(0).getEmail().equals("dedric_strosin@adams.co.uk");
        assert actualOutput.get(19).getEmail().equals("cassandre.okeefe@schuppe.ca");
    }
}