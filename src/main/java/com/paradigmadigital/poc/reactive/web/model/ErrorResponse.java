package com.paradigmadigital.poc.reactive.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collection;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private String code;
    private String message;
    private Collection<String> details;
    private Level level;

    public enum Level {
        FATAL, WARNING, UNKNOWN;

        public static Level of(HttpStatus httpStatus) {
            if (httpStatus.is4xxClientError() || httpStatus.is3xxRedirection())
                return WARNING;
            if (httpStatus.is5xxServerError())
                return FATAL;

            return UNKNOWN;
        }
    }
}
