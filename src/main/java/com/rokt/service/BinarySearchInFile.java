package com.rokt.service;

import com.rokt.data.ReadFiles;
import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BinarySearchInFile extends SearchInFiles {
    @Override
    public Stream<Record> searchInFile(SearchRequest searchRequest) throws IOException {
        RandomAccessFile randomAccessFile = readFiles.readFileAsRAF(searchRequest.getFileName());
        long startPosition = binarySearch(randomAccessFile, searchRequest.getFromDateTime());
        long endPosition = binarySearch(randomAccessFile, searchRequest.getToDateTime());
        randomAccessFile.close();
        return read(readFiles, startPosition, endPosition, searchRequest).stream();
    }

    private long binarySearch(RandomAccessFile raf, DateTime searchDateTime) throws IOException {

        int count = (int) (raf.length() >> 2);
        int midIndex, endIndex = count - 1, startIndex = 0;

        Record midRecordValue = null;
        String midRecordString = null;
        long midPosition = 0;
        while (startIndex <= endIndex) {
            midIndex = (endIndex + startIndex) >> 1;

            // move file pointer to midIndex
            raf.seek(midIndex * 4);
            raf.readLine();
            midPosition = raf.getFilePointer();
            midRecordString = raf.readLine();
            if(midRecordString == null) {
                return -1;
            }
            midRecordValue = fileParser.parse(midRecordString);

            if (midRecordValue.getDateTime().isEqual(searchDateTime)) {
                break;
            } else {
                if (midRecordValue.getDateTime().isAfter(searchDateTime)) {
                    endIndex = midIndex - 1;
                } else {
                    startIndex = midIndex + 1;
                }
            }
        }
        if(midRecordValue == null) {
            return -1;
        }

        if(midRecordValue.getDateTime().isEqual(searchDateTime) || startIndex > endIndex) {
            return midPosition;
        }
        return -1;
    }

    protected List<Record> read(ReadFiles readFiles,
                                long startPositionInclusive,
                                long endPositionInclusive,
                                SearchRequest searchRequest) throws IOException {
        RandomAccessFile raf = readFiles.readFileAsRAF(searchRequest.getFileName());
        long currentPosition = startPositionInclusive;
        raf.seek(currentPosition);
        List<Record> recordList = new ArrayList<>();
        while(currentPosition <= endPositionInclusive) {
            Record record = fileParser.parse(raf.readLine());
            recordList.add(record);
            currentPosition = raf.getFilePointer();
        }
        return recordList;
    }
}
