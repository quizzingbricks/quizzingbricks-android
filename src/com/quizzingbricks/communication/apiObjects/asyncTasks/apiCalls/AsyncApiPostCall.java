package com.quizzingbricks.communication.apiObjects.asyncTasks.apiCalls;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.quizzingbricks.exceptions.APIException;
import com.quizzingbricks.tools.AsyncTaskResult;


public class AsyncApiPostCall extends AbstractApiCall<BasicNameValuePair, Void, AsyncTaskResult<JSONObject>> {

	
	public AsyncApiPostCall(String token) {
		super(token);
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
		catch(APIException e)	{
			return new AsyncTaskResult<JSONObject>(e);
		}
		
	}
}
