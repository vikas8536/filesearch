package com.rokt.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.DateTime;

@AllArgsConstructor
@Getter
@ToString
public class Record {
    DateTime dateTime;
    String email;
    String sessionId;
}
