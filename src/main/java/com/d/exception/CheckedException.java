package com.d.exception;

/**
 * @author d
 */
public class CheckedException extends RuntimeException {
	public CheckedException(String message) {
		super(message);
	}

	public CheckedException(String message, Throwable cause) {
		super(message, cause);
	}
}
