package com.rokt.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class SearchResponse {
    String eventTime;
    String email;
    String sessionId;
}
