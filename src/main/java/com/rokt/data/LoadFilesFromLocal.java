package com.rokt.data;

import com.rokt.application.FileSearchConfiguration;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import java.io.*;
import java.util.stream.Stream;


@AllArgsConstructor
@NoArgsConstructor
public class LoadFilesFromLocal implements ReadFiles {

    @Inject
    FileSearchConfiguration configuration;

    private String getQualifiedName(String fileName) {
        return configuration.getFilesLocation() + "/" + fileName;
    }

    @Override
    public InputStream readFiles(String fileName) throws FileNotFoundException {
        String qualifiedFileName = getQualifiedName(fileName);
        FileInputStream fileInputStream = new FileInputStream(qualifiedFileName);
        return fileInputStream;
    }

    @Override
    public Stream<String> readFileAsStream(String fileName) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(readFiles(fileName))).lines();
    }

    @Override
    public RandomAccessFile readFileAsRAF(String fileName) throws FileNotFoundException {
        String qualifiedFileName = getQualifiedName(fileName);
        return new RandomAccessFile(qualifiedFileName, "r");
    }

    @Override
    public long getSizeOfFile(String fileName) throws IOException {
        return readFileAsRAF(fileName).length();
    }
}
