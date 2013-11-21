package com.quizzingbricks.communication.apiObjects.asyncTasks;

import org.json.JSONObject;

import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.RequestParser;
import com.quizzingbricks.tools.AsyncTaskResult;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class AsyncApiGetCall extends AsyncTask<Void, Void, AsyncTaskResult<JSONObject>> {

	private RequestParser requestParser = new RequestParser();
	private OnTaskComplete onTaskCompleteClass;
	private String url = requestParser.getServerApiAddr();
	private String token;
	
	private Boolean popup = false;
	private ProgressDialog progressDialog;
	
	public AsyncApiGetCall(String token)	{
		this.token = token;
	}
	
	public AsyncApiGetCall(String popUpTitle, String popUpMessage, Context context)	{
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
	
	@Override
	protected void onPreExecute() {
		if(popup == true)	{
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			final AsyncApiGetCall authTask = this; //Declared so that the class is in the scope of the OnClickListener 
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
	protected AsyncTaskResult<JSONObject> doInBackground(Void... params) {
		try	{
			return new AsyncTaskResult<JSONObject>(this.requestParser.getServerEndpointInfo(url, token));
		}
		catch(Exception e)	{
			return new AsyncTaskResult<JSONObject>(e);
		}
	}
	
	@Override
	protected void onPostExecute(AsyncTaskResult<JSONObject> result)	{
		if(popup == true)	{
			progressDialog.dismiss();
		}
		onTaskCompleteClass.onComplete(result);
	}

}
