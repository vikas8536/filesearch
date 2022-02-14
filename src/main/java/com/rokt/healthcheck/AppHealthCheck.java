package com.rokt.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class AppHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}