package com.rokt.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.stream.Stream;

public interface ReadFiles {
    InputStream readFiles(String fileName) throws FileNotFoundException;
    Stream<String> readFileAsStream(String fileName) throws FileNotFoundException;
    RandomAccessFile readFileAsRAF(String fileName) throws FileNotFoundException;
    int getSizeOfFile(String fileName);
}
