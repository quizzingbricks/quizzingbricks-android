package com.quizzingbricks.communication.apiObjects.asyncTasks;

import org.json.JSONObject;

import com.quizzingbricks.tools.AsyncTaskResult;

public interface OnTaskComplete {
	
	public abstract void onComplete(AsyncTaskResult<JSONObject> result);
}
