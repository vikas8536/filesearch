package com.rokt.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    String filename;
    //ISO DateTime parsing
    String from;
    String to;
}
