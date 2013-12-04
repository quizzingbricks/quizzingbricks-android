package com.quizzingbricks.communication.apiObjects;

import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.apiObjects.apiCalls.AsyncApiDeleteCall;
import com.quizzingbricks.communication.apiObjects.apiCalls.AsyncApiGetCall;
import com.quizzingbricks.communication.apiObjects.apiCalls.AsyncApiPostCall;

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
	
	public AbstractThreadedAPI(Context context, boolean sendWithToken, String token)	{
		this.token = token;
		postCall = new AsyncApiPostCall(token);
		getCall = new AsyncApiGetCall(token);
		deleteCall = new AsyncApiDeleteCall(token);
	}
	
	public void CancelRequest()	{
		postCall.cancelRequest();
		getCall.cancelRequest();
		deleteCall.cancelRequest();
		
		postCall.cancel(true);
		getCall.cancel(true);
		deleteCall.cancel(true);
	}
	
	protected String getToken()	{
		return token;
	}
}
