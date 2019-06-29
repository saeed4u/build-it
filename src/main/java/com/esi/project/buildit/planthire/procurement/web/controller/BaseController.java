package com.esi.project.buildit.planthire.procurement.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;


public abstract class BaseController<T>{

	public ResponseEntity<T> createdResponse(T data, URI href){
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(href);
		return new ResponseEntity<>(data, headers, HttpStatus.CREATED);
	}


}
