package com.rokt.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PostResponse {
    String eventTime;
    String email;
    String sessionId;
}
