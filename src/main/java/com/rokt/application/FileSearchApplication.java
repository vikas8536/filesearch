package com.rokt.application;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.rokt.module.FileSearchModule;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.util.Arrays;

public class FileSearchApplication extends Application<FileSearchConfiguration> {
    public static void main(String[] args) throws Exception {
        new FileSearchApplication().run(args);
    }

    @Override
    public void run(FileSearchConfiguration fileSearchConfiguration, Environment environment) {
        MetricRegistry registry = environment.metrics();
        JmxReporter.forRegistry(registry).build().start();

        FileSearchModule module = new FileSearchModule(fileSearchConfiguration, registry);
        Injector injector = Guice.createInjector(module);
        environment.jersey().register(new ObjectMapperContextResolver(injector.getInstance(Key.get(ObjectMapper.class,
                Names.named("jersey")))));
    }
}
