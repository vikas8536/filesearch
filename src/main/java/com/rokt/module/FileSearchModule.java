package com.rokt.module;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.rokt.application.FileSearchConfiguration;

public class FileSearchModule extends AbstractModule {

    final private FileSearchConfiguration fileSearchConfiguration;
    final private MetricRegistry registry;

    public FileSearchModule(FileSearchConfiguration fileSearchConfiguration, MetricRegistry registry) {
        this.fileSearchConfiguration = fileSearchConfiguration;
        this.registry = registry;
    }

    @Override
    protected void configure() {
        bind(FileSearchConfiguration.class).toInstance(fileSearchConfiguration);
        bind(MetricRegistry.class).toInstance(registry);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper defaultTypingObjectMapper = new ObjectMapper();

        bind(ObjectMapper.class).annotatedWith(Names.named("jersey")).toInstance(objectMapper);
        bind(ObjectMapper.class).annotatedWith(Names.named("defaultTyping")).toInstance(defaultTypingObjectMapper);
    }
}
