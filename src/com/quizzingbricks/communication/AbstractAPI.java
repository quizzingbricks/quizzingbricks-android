package com.quizzingbricks.communication;

import com.quizzingbricks.authentication.AuthenticationManager;

import android.content.Context;

public abstract class AbstractAPI {
	
	protected RequestParser requestParser = new RequestParser();
	protected String token = "RandomStringGoesHere"; //Token for testing
	
	public AbstractAPI()	{}
	
	public AbstractAPI(Context context)	{
		AuthenticationManager authManager = new AuthenticationManager(context);
		token = authManager.getToken();
		if(token == null)	{
			authManager.checkAuthentication();
		}
	}
	
	public void cancelRequest()	{
		requestParser.cancelRequest();
	}
}
