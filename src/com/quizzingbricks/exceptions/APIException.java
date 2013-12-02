package com.quizzingbricks.exceptions;

@SuppressWarnings("serial")
public class APIException extends Exception {
	
	private String message;
	private int errorCode;
	private int httpErrorCode;
	
	public APIException() {
	}
	
	public APIException(String message) {
		this.message = message;
	}
	
	public APIException(String message, int errorCode) {
		this.message = message;
		this.errorCode = errorCode;
	}
	
	public APIException(String message, int errorCode, int httpErrorCode)	{
		this.message = message;
		this.errorCode = errorCode;
		this.httpErrorCode = httpErrorCode;
	}
	
	public String getMessage()	{
		return this.message;
	}
	
	public String toString()	{
		return this.message;
	}
	
	public int getHttpErrorCode()	{
		return this.errorCode;
	}
	
	public int getErrorCode()	{
		return this.errorCode;
	}
}
