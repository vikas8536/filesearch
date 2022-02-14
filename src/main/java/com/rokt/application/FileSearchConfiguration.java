package com.rokt.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
public class FileSearchConfiguration extends Configuration {
    @JsonProperty
    private long maxConcurrency;

    @JsonProperty
    @Valid
    @NotNull
    private String filesLocation;

    @JsonProperty
    @Valid
    @NotNull
    private String dateTimeFormat;
}
