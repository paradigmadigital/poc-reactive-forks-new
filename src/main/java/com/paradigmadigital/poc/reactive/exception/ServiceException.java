package com.paradigmadigital.poc.reactive.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception {

    public final HttpStatus httpStatus;

    private ServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static ServiceException newInstance(String message, HttpStatus httpStatus) {
        if (!httpStatus.isError()) throw new IllegalArgumentException("no valid ".concat(httpStatus.name()));

        return new ServiceException(message, httpStatus);
    }

    public static ServiceException newInstance4XX(String message) {
        return new ServiceException(message, HttpStatus.BAD_REQUEST);
    }

    public static ServiceException newInstance5XX(String message) {
        return new ServiceException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ServiceException notFound(T aId) {
        return new ServiceException("not found element with id '" + aId + "'", HttpStatus.NOT_FOUND);
    }

}
