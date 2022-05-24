package com.devsuperior.devcatalog.resources.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.devcatalog.services.exceptions.DataBaseException;
import com.devsuperior.devcatalog.services.exceptions.ResourcesNotFoundException;

@ControllerAdvice
public class ResourcesExceptionHandler {

	@ExceptionHandler(ResourcesNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourcesNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setMessage(e.getMessage());
		err.setStatus(status.value());
		err.setPath(request.getRequestURI());
		err.setError("Recurso não encontrado");
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> database(DataBaseException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setMessage(e.getMessage());
		err.setStatus(status.value());
		err.setPath(request.getRequestURI());
		err.setError("Database Exception");
		return ResponseEntity.status(status).body(err);
	}
	
}
