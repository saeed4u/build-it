package com.esi.project.buildit.planthire.common.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class HttpResponse<T extends Response> {

    private T response;
    private HttpStatus httpStatus;

    public HttpResponse(T response) {
        this.response = response;
    }

    public HttpResponse(T response, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.response = response;
    }

    private HttpStatus getHttpStatusCode() {
        return response.wasSuccessful() ? OK : httpStatus != null ? httpStatus : BAD_REQUEST;
    }

    public ResponseEntity<T> get() {
        return new ResponseEntity<>(response, getHttpStatusCode());
    }
}
