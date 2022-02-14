package com.rokt.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface LoadFiles {
    InputStream readFiles(String fileName) throws FileNotFoundException;
    int getSizeOfFile(String fileName);
}
