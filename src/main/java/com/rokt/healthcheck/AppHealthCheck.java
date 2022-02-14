package com.rokt.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.servlets.tasks.Task;

import javax.inject.Inject;

public class AppHealthCheck extends HealthCheck {
    final private Task someTask;

    @Inject
    public AppHealthCheck(Task someTask) {
        this.someTask = someTask;
    }

    @Override
    protected Result check() throws Exception
    {
        if(someTask!=null){
        return Result.healthy();
    }
        return Result.unhealthy("Error message");
    }
}