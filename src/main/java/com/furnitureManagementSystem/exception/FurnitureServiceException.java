package com.furnitureManagementSystem.exception;

import org.springframework.http.HttpStatus;

public class FurnitureServiceException extends RuntimeException {
	private static final long serialVersionUID = 8601674281650156365L;
	private HttpStatus status;

	public FurnitureServiceException(String message, HttpStatus status) {
		super(message);

		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
