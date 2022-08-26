package com.paradigmadigital.poc.reactive.web.bind;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collection;

public interface PreviousRestApi {

    Collection<String> previous = new ArrayList<>();

    @Operation(summary = "Gets all deleted resources")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the resources",
                    content = @Content(mediaType = MediaType.TEXT_EVENT_STREAM_VALUE)),
            @ApiResponse(responseCode = "404", description = "resources not found",
                    content = @Content)})
    @GetMapping(path = "/previous/stream")
    @ResponseStatus(HttpStatus.OK)
    default Flux<String> getPrevious() {
        return Flux.fromIterable(previous);
    }

}
