package com.quizzingbricks.tools;

public class AsyncTaskResult<ResultType>	{
	private ResultType result;
	private Exception exception;
	
	public AsyncTaskResult(ResultType result){
		this.result = result;
	}
	public AsyncTaskResult(Exception exception)	{
		this.exception = exception;
	}
	
	public ResultType getResult()	{
		return this.result;
	}
	
	public Exception getException()	{
		return this.exception;
	}
}