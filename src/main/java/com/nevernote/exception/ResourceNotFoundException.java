package com.nevernote.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private ErrorDetails resp;

	public ResourceNotFoundException(final String message) {
		super(message);

	}
	public ResourceNotFoundException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
