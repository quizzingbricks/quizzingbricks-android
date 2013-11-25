package com.quizzingbricks.communication.apiObjects.asyncTasks.apiCalls;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.quizzingbricks.tools.AsyncTaskResult;


public class AsyncApiPostCall extends AbstractApiCall<BasicNameValuePair, Void, AsyncTaskResult<JSONObject>> {

	
	public AsyncApiPostCall(String token) {
		super(token);
	}
	
	public AsyncApiPostCall(String popUpTitle, String popUpMessage, Context context)	{
		super(popUpTitle, popUpMessage, context);
	}

	@Override
	protected AsyncTaskResult<JSONObject> doInBackground(BasicNameValuePair... params) {
		try	{
			if(simpleJsonObject != null)	{
				return new AsyncTaskResult<JSONObject>(this.requestParser.sendPostToServer(url, token, simpleJsonObject, params));
			}
			else	{
				return new AsyncTaskResult<JSONObject>(this.requestParser.sendPostToServer(url, token, params));
			}
		}
		catch(Exception e)	{
			return new AsyncTaskResult<JSONObject>(e);
		}
		
	}
}
