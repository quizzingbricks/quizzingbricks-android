package com.quizzingbricks.communication.apiObjects.nonThreaded;

import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.RequestParser;

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
