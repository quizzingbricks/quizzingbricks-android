package com.quizzingbricks.tools;

import com.quizzingbricks.exceptions.APIException;

public class AsyncTaskResult<ResultType>        {
    private ResultType result;
    private APIException exception;
    
    public AsyncTaskResult(ResultType result){
        this.result = result;
    }
    public AsyncTaskResult(APIException exception)        {
        this.exception = exception;
    }
    
    public ResultType getResult()        {
        return this.result;
    }
    
    public Exception getException()        {
        return this.exception;
    }
    
    public boolean hasException()	{
    	if(this.exception == null)	{
    		return false;
    	}
    	else	{
    		return true;
    	}
    }
    
    public boolean hasResult()	{
    	if(this.result == null)	{
    		return false;
    	}
    	else	{
    		return true;
    	}
    }
}