package com.javaexpress.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorAPI> handleException(UserNotFoundException ex) {
		log.error("UserNot Found Exception {}",ex.getMessage());
		
		ErrorAPI errorAPI = new ErrorAPI();
		errorAPI.setLocalDateTime(LocalDateTime.now());
		errorAPI.setMessage(ex.getMessage());
		errorAPI.setError("Validation Error");
		errorAPI.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		
		return new ResponseEntity<>(errorAPI,HttpStatus.BAD_REQUEST);
		
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorAPI> handleException(Exception ex) {
		log.error("Internal Server Error {}",ex.getMessage());
		
		ErrorAPI errorAPI = new ErrorAPI();
		errorAPI.setLocalDateTime(LocalDateTime.now());
		errorAPI.setMessage(ex.getMessage());
		//errorAPI.setError("Something went wrong");
		errorAPI.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		return new ResponseEntity<>(errorAPI,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorAPI> handleException(MethodArgumentNotValidException ex) {
		
		String errors = ex.getBindingResult()
		  .getFieldErrors()
		  .stream()
		  .map(obj -> obj.getField()+":"+obj.getDefaultMessage())
		  .collect(Collectors.joining(","));
		
		
		ErrorAPI errorAPI = new ErrorAPI();
		errorAPI.setLocalDateTime(LocalDateTime.now());
		errorAPI.setMessage(errors);
		errorAPI.setError("Client Side Validation Error");
		errorAPI.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		
		return new ResponseEntity<>(errorAPI,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorAPI> handleException(HttpMessageNotReadableException ex) {
		log.error("HttpMessageNotReadableException Exception {}",ex.getMessage());
		
		ErrorAPI errorAPI = new ErrorAPI();
		errorAPI.setLocalDateTime(LocalDateTime.now());
		errorAPI.setMessage(ex.getMessage());
		errorAPI.setError("Malformed JSON Data");
		errorAPI.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
		
		return new ResponseEntity<>(errorAPI,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	
}
