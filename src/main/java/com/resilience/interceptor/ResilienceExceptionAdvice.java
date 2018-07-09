package com.resilience.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.resilience.exception.UserException;
import com.resilience.utils.Utilities;

@ControllerAdvice
@RestController
public class ResilienceExceptionAdvice {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ObjectNode handleBaseException(Exception ex) {
		ObjectNode node = Utilities.createObjectNode();
		node.put("STATUS", "FAILURE");
		node.put("errorMessage", ex.getMessage());
		node.put("errorCode", "E9999");
		return node;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = UserException.class)
	public ObjectNode handleUserException(UserException ex) {
		ObjectNode node = Utilities.createObjectNode();
		node.put("STATUS", "FAILURE");
		node.put("errorMessage", ex.getMessage());
		node.put("errorCode", ex.getErrorCode());
		return node;
	}

}
