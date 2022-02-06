package com.rokt;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class FileSearchApplication extends Application<FileSearchConfiguration> {
    public static void main(String[] args) throws Exception {
        new FileSearchApplication().run(args);
    }

    @Override
    public void run(FileSearchConfiguration fileSearchConfiguration, Environment environment) throws Exception {

    }
}
