package com.rokt.module;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.rokt.application.FileSearchConfiguration;
import com.rokt.data.LoadFilesFromLocal;
import com.rokt.data.ReadFiles;
import com.rokt.helpers.DateTimeHelper;
import com.rokt.helpers.RequestParser;
import com.rokt.helpers.ResponseParser;
import com.rokt.service.BinarySearchInFile;
import com.rokt.helpers.RecordParser;
import com.rokt.service.SearchInFiles;
import com.rokt.helpers.SpaceSeparatedFileParser;

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

        bind(ReadFiles.class).toInstance(new LoadFilesFromLocal());
        bind(RecordParser.class).toInstance(new SpaceSeparatedFileParser());
        bind(SearchInFiles.class).toInstance(new BinarySearchInFile());

        bind(DateTimeHelper.class).asEagerSingleton();
        bind(RequestParser.class).asEagerSingleton();
        bind(ResponseParser.class).asEagerSingleton();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper defaultTypingObjectMapper = new ObjectMapper();

        bind(ObjectMapper.class).annotatedWith(Names.named("jersey")).toInstance(objectMapper);
        bind(ObjectMapper.class).annotatedWith(Names.named("defaultTyping")).toInstance(defaultTypingObjectMapper);
    }
}
