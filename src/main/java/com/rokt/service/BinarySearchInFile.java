package com.rokt.service;

import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BinarySearchInFile extends SearchInFiles {
    @Override
    public Stream<Record> searchInFile(SearchRequest searchRequest) throws IOException {
        RandomAccessFile randomAccessFile = readFiles.readFileAsRAF(searchRequest.getFileName());
        return binarySearch(randomAccessFile, searchRequest);
    }

    public Stream<Record> binarySearch(RandomAccessFile raf, SearchRequest searchRequest) throws IOException {
        List<Record> recordList = new ArrayList<>();

        int count = (int) (raf.length() >> 2);
        int midIndex, endIndex = count - 1, startIndex = 0;

        Record midValue = null;
        while (startIndex <= endIndex) {
            midIndex = (endIndex + startIndex) >> 1;

            // move file pointer to midIndex
            raf.seek(midIndex * 4);
            raf.readLine();
            String currentLine = raf.readLine();
            System.out.println("Mid: " + currentLine);
            midValue = fileParser.parse(currentLine);

            if (midValue.getDateTime().isEqual(searchRequest.getFromDateTime())) {
                break;
            } else {
                if (midValue.getDateTime().isAfter(searchRequest.getFromDateTime())) {
                    endIndex = midIndex - 1;
                } else {
                    startIndex = midIndex + 1;
                }
            }
        }
        if(midValue == null) {
            return recordList.stream();
        }

        if(midValue.getDateTime().isEqual(searchRequest.getFromDateTime()) || startIndex > endIndex) {
            Record record = midValue;
            do {
                recordList.add(record);
                record = fileParser.parse(raf.readLine());
            } while(record.getDateTime().isBefore(searchRequest.getToDateTime())
                    || record.getDateTime().isEqual(searchRequest.getToDateTime()));
        }
        return recordList.stream();
    }
}
