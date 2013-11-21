package com.quizzingbricks.communication.apiObjects.asyncTasks;

import com.quizzingbricks.authentication.AuthenticationManager;

import android.content.Context;

public abstract class AbstractThreadedAPI {
	
	protected AsyncApiPostCall postCall;
	protected AsyncApiGetCall getCall;
	private String token;
	
	/**
	 * Empty constructor for testing
	 */
	public AbstractThreadedAPI()	{
		this.token = "RandomStringGoesHere";
		postCall = new AsyncApiPostCall(this.token);
		getCall = new AsyncApiGetCall(token);
	}
	
	public AbstractThreadedAPI(Context context)	{
		AuthenticationManager authManager = new AuthenticationManager(context);
		token = authManager.getToken();
		if(token == null)	{
			authManager.checkAuthentication();
		}
		postCall = new AsyncApiPostCall(token);
		getCall = new AsyncApiGetCall(token);
	}
	
	protected String getToken()	{
		return token;
	}
}
