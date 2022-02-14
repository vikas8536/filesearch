package com.rokt.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.rokt.healthcheck.AppHealthCheck;
import com.rokt.module.FileSearchModule;
import com.rokt.resource.FileSearchResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.ServerProperties;

public class FileSearchApplication extends Application<FileSearchConfiguration> {
    public static void main(String[] args) throws Exception {
        new FileSearchApplication().run(args);
    }

    @Override
    public void run(FileSearchConfiguration fileSearchConfiguration, Environment environment) {

        FileSearchModule module = new FileSearchModule(fileSearchConfiguration, environment.metrics());
        Injector injector = Guice.createInjector(module);
        environment.healthChecks().register("fileSearch", new AppHealthCheck());
        environment.jersey().register(injector.getInstance(FileSearchResource.class));
        environment.jersey().register(new ObjectMapperContextResolver(injector.getInstance(Key.get(ObjectMapper.class,
                Names.named("jersey")))));
    }
}
