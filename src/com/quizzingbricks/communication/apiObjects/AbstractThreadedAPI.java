package com.quizzingbricks.communication.apiObjects;

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
	 * 
	 * @param context, the apps context for getting the token
	 * @param sendWithToken, if false the token header will just be a place holder (for the login function)
	 */
	public AbstractThreadedAPI(Context context, boolean sendWithToken)	{
		if(sendWithToken == true)	{
			AuthenticationManager authManager = new AuthenticationManager(context);
			token = authManager.getToken();
		}
		else	{
			this.token = "PlaceHolder";
		}
		postCall = new AsyncApiPostCall(token);
		getCall = new AsyncApiGetCall(token);
		deleteCall = new AsyncApiDeleteCall(token);
	}
	
	protected String getToken()	{
		return token;
	}
}
