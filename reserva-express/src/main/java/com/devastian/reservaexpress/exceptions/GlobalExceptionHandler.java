package com.devastian.reservaexpress.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devastian.reservaexpress.dto.Response;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	// ERROR 500 - INTERNAL SERVER ERROR
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response<?>> handleAllUnknownException(Exception ex){
		Response<?> response = Response.builder()
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// ERROR 404 - NOT FOUND
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Response<?>> handleNotFoundException(NotFoundException ex){
		Response<?> response = Response.builder()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	// ERROR 400 - BAD REQUEST
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Response<?>> handleBadRequestException(BadRequestException ex){
		Response<?> response = Response.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	// ERROR 400 - BAD REQUEST (ERRORES DE VALIDACIÓN)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(error -> {
	        errors.put(error.getField(), error.getDefaultMessage());
	    });

	    Response<Map<String, String>> response = Response.<Map<String, String>>builder()
	            .statusCode(HttpStatus.BAD_REQUEST.value())
	            .message("Errores de validación")
	            .data(errors)
	            .build();

	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
