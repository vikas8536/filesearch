package com.rokt.service;

import com.google.inject.Inject;
import com.rokt.data.ReadFiles;
import com.rokt.helpers.RecordParser;
import com.rokt.model.internal.Record;
import com.rokt.model.internal.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
public class BinarySearchInFile extends SearchInFiles {
    @Inject
    protected ReadFiles readFiles;
    @Inject
    protected RecordParser fileParser;

    @Override
    public Stream<Record> searchInFile(SearchRequest searchRequest) throws IOException {
        RandomAccessFile randomAccessFile = readFiles.readFileAsRAF(searchRequest.getFileName());
        long length = randomAccessFile.length();
        long startPosition = binarySearch(randomAccessFile, searchRequest.getFromDateTime());
        long endPosition = binarySearch(randomAccessFile, searchRequest.getToDateTime());
        randomAccessFile.close();
        if(startPosition > 0 && endPosition > 0) {
            return read(readFiles, startPosition, endPosition, searchRequest).stream();
        }
        if(startPosition > 0) {
            return read(readFiles, startPosition, length, searchRequest).stream();
        }
        if(endPosition > 0) {
            return read(readFiles, 0, endPosition, searchRequest).stream();
        }
        return Stream.<Record>builder().build();
    }

    private long binarySearch(RandomAccessFile raf, DateTime searchDateTime) throws IOException {

        int count = (int) (raf.length() >> 2) - 1;
        int midIndex, endIndex = count, startIndex = 0;

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
                if(endIndex < 0 ) {
                    return -1;
                }
                if(endIndex >= count || startIndex >= count) {
                    return Long.MAX_VALUE;
                }
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
        if(endIndex < 0 && startIndex == 0) {
            return Long.MIN_VALUE;
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
            String line = raf.readLine();
            if(line == null) {
                break;
            }
            Record record = fileParser.parse(line);
            recordList.add(record);
            currentPosition = raf.getFilePointer();
        }
        return recordList;
    }
}
