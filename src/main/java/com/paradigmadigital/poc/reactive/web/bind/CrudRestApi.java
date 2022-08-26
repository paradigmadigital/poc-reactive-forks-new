package com.paradigmadigital.poc.reactive.web.bind;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;

public interface CrudRestApi<T, I> {

    @Operation(summary = "Get all resources")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the resources",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "resources not found",
                    content = @Content)})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Flux<T> getAll();

    @Operation(summary = "Get all resources as stream data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the resources",
                    content = @Content(mediaType = MediaType.TEXT_EVENT_STREAM_VALUE)),
            @ApiResponse(responseCode = "404", description = "resources not found",
                    content = @Content)})
    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    default Flux<T> getAllAsStream() {
        return getAll()
                .delayElements(Duration.ofMillis(20));
    }

    @Operation(summary = "Get a resource by reference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the resource",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<T> getById(@PathVariable I id);

    @Operation(summary = "create a resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created the resource",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<T> insertOne(@Valid @RequestBody Mono<T> payload);

    @Operation(summary = "Alter a resource by reference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated the resource",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<T> updateById(@PathVariable I id, @Valid @RequestBody Mono<T> payload);

    @Operation(summary = "Delete a resource by reference")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed the resource",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> deleteById(@PathVariable I id);
}
