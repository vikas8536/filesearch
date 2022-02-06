package com.rokt.resource;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.rokt.application.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Singleton
@Path("/")
@Api(value = "FileSearchResource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FileSearchResource {

    @GET
    @Path("/ping")
    @Metered(name = "metaAllMeter")
    @Timed(name = "metaAllTimer")
    @ExceptionMetered(name = "metaAllExceptions")
    @ApiOperation(value = "Ping Test")
    public Response fetchMeta(@HeaderParam(Constants.CLIENT_HEADER_KEY) String client)  {
        return Response.ok().entity("Success!!").build();
    }
}
