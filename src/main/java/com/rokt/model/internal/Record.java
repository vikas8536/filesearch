package com.rokt.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.time.DateTime;

@AllArgsConstructor
@Getter
public class Record {
    DateTime dateTime;
    String email;
    String sessionId;
}
