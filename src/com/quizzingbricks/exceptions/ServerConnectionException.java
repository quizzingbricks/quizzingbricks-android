package com.quizzingbricks.exceptions;

@SuppressWarnings("serial")
public class ServerConnectionException extends RuntimeException {
	
	private String message;
	private int errorCode;
	
	public ServerConnectionException() {
	}
	
	public ServerConnectionException(String message) {
		this.message = message;
	}
	
	public ServerConnectionException(String message, int errorCode) {
		this.message = message;
		this.errorCode = errorCode;
	}
	
	public String getMessage()	{
		return this.message;
	}
	
	public String toString()	{
		return this.message;
	}
}
