package com.rokt.service;

import com.rokt.model.internal.FileRecord;

public interface FileParser {
    FileRecord parse(String input);
}
