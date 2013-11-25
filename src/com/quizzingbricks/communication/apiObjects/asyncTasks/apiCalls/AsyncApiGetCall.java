package com.quizzingbricks.communication.apiObjects.asyncTasks.apiCalls;

import org.json.JSONObject;

import com.quizzingbricks.tools.AsyncTaskResult;

import android.content.Context;

public class AsyncApiGetCall extends AbstractApiCall<Void, Void, AsyncTaskResult<JSONObject>> {

	public AsyncApiGetCall(String token)	{
		super(token);
	}
	
	@Override
	protected AsyncTaskResult<JSONObject> doInBackground(Void... params) {
		try	{
			return new AsyncTaskResult<JSONObject>(this.requestParser.getServerEndpointInfo(url, token));
		}
		catch(Exception e)	{
			return new AsyncTaskResult<JSONObject>(e);
		}
	}
}
