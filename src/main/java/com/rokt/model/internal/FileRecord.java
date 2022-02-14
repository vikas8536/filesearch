package com.rokt.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.time.DateTime;

@AllArgsConstructor
@Getter
public class FileRecord {
    DateTime dateTime;
    String email;
    String sessionId;
}
