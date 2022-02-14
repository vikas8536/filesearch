package com.rokt.service;

import com.rokt.model.internal.Record;

public interface FileParser {
    Record parse(String input);
}
