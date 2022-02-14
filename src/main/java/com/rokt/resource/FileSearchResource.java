package com.rokt.resource;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.rokt.helpers.RequestParser;
import com.rokt.helpers.ResponseParser;
import com.rokt.model.api.PostRequest;
import com.rokt.model.internal.Record;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private RequestParser requestParser;

    @Inject
    private ResponseParser responseParser;

    @POST
    @Metered(name = "searchInFileMeter")
    @Timed(name = "searchInFileTimer")
    @ExceptionMetered(name = "searchInFileException")
    @ApiOperation(value = "Stream search results")
    public Response searchInFile(PostRequest request) {
        List<SearchResponse> searchResponses = Collections.emptyList();
        try {
            SearchRequest searchRequest = requestParser.validate(request);
            Stream<Record> recordStream = searchInFiles.searchInFile(searchRequest);
            searchResponses = recordStream
                    .map(responseParser::convertToSearchResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage())
                    .build();
        } catch (FileNotFoundException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST.getStatusCode(),
                            request.getFilename() + " Not Found!")
                    .build();
        } catch (IOException e) {
            Response
                .status(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage())
                .build();
        }
        return Response.ok().entity(searchResponses).build();
    }
}
