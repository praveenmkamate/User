package com.example.restfulwebservice.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler 
extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
		
		ExceptionReponse exceptionResponse = new ExceptionReponse(new Date(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionReponse exceptionResponse = new ExceptionReponse(new Date(), "Validation Failed" , ex.getBindingResult().toString());
		
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	/*
	 * @ExceptionHandler(UserNotFoundException.class) public final
	 * ResponseEntity<Object> handleUserNotFoundExceptions(UserNotFoundException ex,
	 * WebRequest request){
	 * 
	 * ExceptionReponse exceptionResponse = new ExceptionReponse(new Date(),
	 * ex.getMessage(), request.getDescription(false));
	 * 
	 * return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND); }
	 */
	
}
