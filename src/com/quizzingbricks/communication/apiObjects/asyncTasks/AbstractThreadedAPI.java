package com.quizzingbricks.communication.apiObjects.asyncTasks;

import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.apiObjects.asyncTasks.apiCalls.AsyncApiDeleteCall;
import com.quizzingbricks.communication.apiObjects.asyncTasks.apiCalls.AsyncApiGetCall;
import com.quizzingbricks.communication.apiObjects.asyncTasks.apiCalls.AsyncApiPostCall;

import android.content.Context;

public abstract class AbstractThreadedAPI {
	
	protected AsyncApiPostCall postCall;
	protected AsyncApiGetCall getCall;
	protected AsyncApiDeleteCall deleteCall;
	private String token;
	
	/**
	 * Empty constructor for testing
	 */
	public AbstractThreadedAPI()	{
		this.token = "RandomStringGoesHere";
		postCall = new AsyncApiPostCall(this.token);
		getCall = new AsyncApiGetCall(token);
		deleteCall = new AsyncApiDeleteCall(token);
	}
	
	public AbstractThreadedAPI(Context context)	{
		AuthenticationManager authManager = new AuthenticationManager(context);
		token = authManager.getToken();
		if(token == null)	{
			authManager.checkAuthentication();
		}
		postCall = new AsyncApiPostCall(token);
		getCall = new AsyncApiGetCall(token);
		deleteCall = new AsyncApiDeleteCall(token);
	}
	
	protected String getToken()	{
		return token;
	}
}
