package com.rokt.service;

import com.rokt.application.FileSearchConfiguration;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class LoadFilesFromLocal implements LoadFiles{

    @Inject
    FileSearchConfiguration configuration;

    @Override
    public InputStream readFiles(String fileName) throws FileNotFoundException {
        String qualifiedFileName = configuration.getFilesLocation() + "/" + fileName;
        return new FileInputStream(qualifiedFileName);
    }

    @Override
    public int getSizeOfFile(String fileName) {
        return 0;
    }
}
