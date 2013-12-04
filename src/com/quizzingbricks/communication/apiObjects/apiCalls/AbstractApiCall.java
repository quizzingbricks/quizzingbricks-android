package com.quizzingbricks.communication.apiObjects.apiCalls;

import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.RequestParser;
import com.quizzingbricks.communication.apiObjects.OnTaskComplete;
import com.quizzingbricks.communication.jsonObject.SimpleJsonObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public abstract class AbstractApiCall<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	
	protected RequestParser requestParser = new RequestParser();
	protected OnTaskComplete<Result> onTaskCompleteClass;
	protected String url = requestParser.getServerApiAddr();
	protected String token;
	
	protected Boolean popup = false;
	protected ProgressDialog progressDialog;
	
	protected SimpleJsonObject simpleJsonObject;
	
	public AbstractApiCall(String token)	{
		this.token = token;
	}
	
	public String getServerUrl()	{
		return requestParser.getServerApiAddr();
	}
	
	public void addToTheEndOfUrl(String endOfUrl)	{
		this.url += endOfUrl;
	}
	
	public void addOnTaskComplete(OnTaskComplete<Result> onTaskCompleteClass)	{
		this.onTaskCompleteClass = onTaskCompleteClass;
	}
	
	public void addSimpleJsonObject(SimpleJsonObject simpleJsonObject)	{
		this.simpleJsonObject = simpleJsonObject;
	}
	
	public void addPopup(String popUpTitle, String popUpMessage, Context context)	{
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(popUpTitle);
		progressDialog.setMessage(popUpMessage);
		this.popup = true;
	}
	
	public void cancelRequest()	{
		requestParser.cancelRequest();
	}
	
	public void removePopup()	{
		this.popup = false;
	}
	
	@Override
	protected void onPreExecute() {
		if(popup == true)	{
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			final AbstractApiCall authTask = this; //Declared so that the class is in the scope of the OnClickListener 
			progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			    	requestParser.cancelRequest();
			    	progressDialog.dismiss();
			    	authTask.cancel(true);
			    }
			});
			progressDialog.show();
		}
	} 
	
	protected void onPostExecute(Result result)	{
		if(popup == true)	{
			progressDialog.dismiss();
		}
		onTaskCompleteClass.onComplete(result);
	}
}
