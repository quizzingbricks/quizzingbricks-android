package com.quizzingbricks.communication.apiObjects.apiCalls;

import org.json.JSONObject;

import com.quizzingbricks.exceptions.APIException;
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
		catch(APIException e)	{
			return new AsyncTaskResult<JSONObject>(e);
		}
	}
}
