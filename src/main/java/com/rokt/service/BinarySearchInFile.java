package com.rokt.service;

import com.google.inject.Inject;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.model.internal.FileRecord;
import com.rokt.model.internal.SearchRequest;
import com.rokt.model.internal.SearchResponse;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Objects;

public class BinarySearchInFile implements SearchInFiles {
    @Inject
    private FileParser fileParser;
    @Inject
    private DateTimeHelper dateTimeHelper;
    @Override
    public List<SearchResponse> searchInFile(SearchRequest searchRequest) throws IOException {

        return null;
    }
    /*public boolean binarySearch(SearchRequest searchRequest) {
        String fileName = searchRequest.getFileName();
        DateTime from = searchRequest.getFromDateTime();
        DateTime to = searchRequest.getToDateTime();

        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "r");
            String line = raf.readLine();
            FileRecord record = fileParser.parse(line);
            DateTime recordDateTime = record.getDateTime();
            if (isInRange(searchRequest, record))
                return true;

            int count = (int) (raf.length() >> 2);
            int midIndex, midValue, endIndex = count - 1, startIndex = 0;

            while (startIndex <= endIndex) {
                midIndex = (endIndex + startIndex) >> 1;

                // move file pointer to midIndex
                raf.seek(midIndex * 4);

                midValue = raf.readInt();
                if (midValue == num) {
                    return true;
                } else {
                    if (midValue > num) {
                        endIndex = midIndex - 1;
                    } else {
                        startIndex = midIndex + 1;
                    }
                }
            }
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }*/
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
}
