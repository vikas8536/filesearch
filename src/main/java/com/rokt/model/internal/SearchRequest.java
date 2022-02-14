package com.rokt.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.DateTime;

@Getter
@AllArgsConstructor
@ToString
public class SearchRequest {
    String fileName;
    DateTime fromDateTime;
    DateTime toDateTime;
}
