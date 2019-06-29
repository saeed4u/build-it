package com.esi.project.buildit.planthire.common.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

	protected static <T extends Response> ResponseEntity<T> createResponse(T result) {
		return new HttpResponse<>(result).get();
	}

	protected static <T extends Response> ResponseEntity<T> createResponse(T result, HttpStatus status) {
		return new HttpResponse<>(result, status).get();
	}
}
