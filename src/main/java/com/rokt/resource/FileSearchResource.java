package com.rokt.resource;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.rokt.helpers.RequestValidation;
import com.rokt.model.api.PostRequest;
import com.rokt.model.internal.SearchRequest;
import com.rokt.model.internal.SearchResponse;
import com.rokt.service.SearchInFiles;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Slf4j
@Singleton
@Path("/")
@Api(value = "FileSearchResource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@NoArgsConstructor
@AllArgsConstructor
public class FileSearchResource {

    @Inject
    private SearchInFiles searchInFiles;

    @Inject
    private RequestValidation requestValidation;

    @GET
    @Path("/ping")
    @Metered(name = "pingMeter")
    @Timed(name = "pingTimer")
    @ExceptionMetered(name = "metaAllExceptions")
    @ApiOperation(value = "Ping Test")
    public Response fetchMeta()  {
        return Response.ok().entity("Success!!").build();
    }

    @POST
    @Metered(name = "searchInFileMeter")
    @Timed(name = "searchInFileTimer")
    @ExceptionMetered(name = "searchInFileException")
    @ApiOperation(value = "Stream search results")
    public Response searchInFile(PostRequest request) {
        //System.out.println(request);
        List<SearchResponse> searchResponses;
        try {
            SearchRequest searchRequest = requestValidation.validateAndParsePostRequest(request);
            // Search
            searchResponses = searchInFiles.searchInFile(searchRequest);
        } catch (IllegalArgumentException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST.getStatusCode(),
                            e.getMessage())
                    .build();
        }
        catch (IOException e) {
            return Response.serverError().entity("File Does Not Exist!").build();
        }
        return Response.ok().entity(searchResponses).build();
    }
}
