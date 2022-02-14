package com.rokt.service;

import com.rokt.application.FileSearchConfiguration;

import javax.inject.Inject;
import java.io.*;
import java.util.stream.Stream;


public class LoadFilesFromLocal implements ReadFiles {

    @Inject
    FileSearchConfiguration configuration;

    @Override
    public InputStream readFiles(String fileName) throws FileNotFoundException {
        String qualifiedFileName = configuration.getFilesLocation() + "/" + fileName;
        FileInputStream fileInputStream = new FileInputStream(qualifiedFileName);
        return fileInputStream;
    }

    @Override
    public Stream<String> readFileAsStream(String fileName) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(readFiles(fileName))).lines();
    }

    @Override
    public RandomAccessFile readFileAsRAF(String fileName) throws FileNotFoundException {
        return new RandomAccessFile(fileName, "r");
    }

    @Override
    public long getSizeOfFile(String fileName) throws IOException {
        return readFileAsRAF(fileName).length();
    }
}
