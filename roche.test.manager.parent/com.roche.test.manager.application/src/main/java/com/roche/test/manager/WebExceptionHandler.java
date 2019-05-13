package com.roche.test.manager;

import com.roche.test.manager.controller.exceptions.TestDataIncompleteException;
import com.roche.test.manager.core.exceptions.OperationsExecuteNotFound;
import com.roche.test.manager.core.exceptions.TestInvalidException;
import com.roche.test.manager.core.exceptions.TestOperationClassNotFoundException;
import com.roche.test.manager.core.exceptions.TestValidatorClassNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class WebExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

	@ExceptionHandler(TestDataIncompleteException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(final TestDataIncompleteException exception) {
		WebExceptionHandler.log.error(WebExceptionHandler.class.getName() + ": " + exception.getMessage());
		return this.error(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(OperationsExecuteNotFound.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(final OperationsExecuteNotFound exception) {
		WebExceptionHandler.log.error(WebExceptionHandler.class.getName() + ": " + exception.getMessage());
		return this.error(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(TestInvalidException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(final TestInvalidException exception) {
		WebExceptionHandler.log.error(WebExceptionHandler.class.getName() + ": " + exception.getMessage());
		return this.error(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
	}


	@ExceptionHandler(TestOperationClassNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(final TestOperationClassNotFoundException exception) {
		WebExceptionHandler.log.error(WebExceptionHandler.class.getName() + ": " + exception.getMessage());
		return this.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ExceptionHandler(TestValidatorClassNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(final TestValidatorClassNotFoundException exception) {
		WebExceptionHandler.log.error(WebExceptionHandler.class.getName() + ": " + exception.getMessage());
		return this.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}


	// Validation query params required
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(final MissingServletRequestParameterException exception) {
		WebExceptionHandler.log.error(WebExceptionHandler.class.getName() + ": " + exception.getMessage());
		return this.error(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	// Default
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handle(final Exception exception) {
		WebExceptionHandler.log.error("Unhandled exception caught", exception);
		return this.error((exception.getCause() != null ? exception.getCause() + " " : "") + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	private Map<String, Object> error(final String message, final int code) {
		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("error", message);
		errorMap.put("code", code);
		return errorMap;
	}

}
