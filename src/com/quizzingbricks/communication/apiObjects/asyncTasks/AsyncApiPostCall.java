package com.quizzingbricks.communication.apiObjects.asyncTasks;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.RequestParser;
import com.quizzingbricks.communication.jsonObject.SimpleJsonObject;
import com.quizzingbricks.tools.AsyncTaskResult;


public class AsyncApiPostCall extends AsyncTask<BasicNameValuePair, Void, AsyncTaskResult<JSONObject>> {

	private RequestParser requestParser = new RequestParser();
	private OnTaskComplete onTaskCompleteClass;
	private String url = requestParser.getServerApiAddr();
	private String token;
	
	private Boolean popup = false;
	private ProgressDialog progressDialog;
	
	private SimpleJsonObject simpleJsonObject;
	
	public AsyncApiPostCall(String token)	{
		this.token = token;
	}
	
	public AsyncApiPostCall(String popUpTitle, String popUpMessage, Context context)	{
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(popUpTitle);
		progressDialog.setMessage(popUpMessage);
		this.popup = true;
		AuthenticationManager authManager = new AuthenticationManager(context);
		token = authManager.getToken();
		if(token == null)	{
			authManager.checkAuthentication();
		}
	}
	
	public String getServerUrl()	{
		return requestParser.getServerApiAddr();
	}
	
	public void addToTheEndOfUrl(String endOfUrl)	{
		this.url += endOfUrl;
	}
	
	public void addOnTaskComplete(OnTaskComplete onTaskCompleteClass)	{
		this.onTaskCompleteClass = onTaskCompleteClass;
	}
	
	public void addSimpleJsonObject(SimpleJsonObject simpleJsonObject)	{
		this.simpleJsonObject = simpleJsonObject;
	}
	
	@Override
	protected void onPreExecute() {
		if(popup == true)	{
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			final AsyncApiPostCall authTask = this; //Declared so that the class is in the scope of the OnClickListener 
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
	
	protected void onPostExecute(AsyncTaskResult<JSONObject> result)	{
		if(popup == true)	{
			progressDialog.dismiss();
		}
		onTaskCompleteClass.onComplete(result);
	}
}
