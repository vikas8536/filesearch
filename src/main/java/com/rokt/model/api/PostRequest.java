package com.rokt.model.api;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostRequest {
    String filename;
    //ISO DateTime parsing
    String from;
    String to;
}
