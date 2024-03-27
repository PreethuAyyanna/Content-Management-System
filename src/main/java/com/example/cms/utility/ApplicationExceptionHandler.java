package com.example.cms.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.cms.exception.UserAlreadyExistByEmailException;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	private ErrorStructure<String> structure;
	

	public ApplicationExceptionHandler(ErrorStructure<String> structureList) {
		super();
		this.structure = structureList;
	}

	@ExceptionHandler(UserAlreadyExistByEmailException.class)
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyByEmailException(UserAlreadyExistByEmailException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST,ex.getMessage(),"User already exists with the given email ID");
	}

	private ResponseEntity<ErrorStructure<String>> errorStructure(HttpStatus status, String errorMessage,
			String rootCause) {

			return new ResponseEntity<ErrorStructure<String>>(structure.setStatus(status.value())
				.setErrorMessage(errorMessage)
				.setRootCause(rootCause),HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
			Map<String, String> messages=new HashMap<>();
			ex.getAllErrors().forEach(error->{

			messages.put(((FieldError)error).getField(),error.getDefaultMessage());
			});
			return ResponseEntity.badRequest().body(structure.setStatus(HttpStatus.BAD_REQUEST.value())
				.setErrorMessage("Invalid inputs")
				.setRootCause(messages));
	}
}


