package com.testing;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.quizzingbricks.communication.LobbyAPI;
import com.quizzingbricks.exceptions.ServerConnectionException;

public class APITester {
	
	private LobbyAPI lobbyAPI;
	private Context context;
	
	public APITester(Context context)	{
		this.context = context;
	}
	
	public void testGetLobbies()	{
		EndPointTester task = new EndPointTester();
		task.doInBackground("/api/games/lobby");
	}
	
	private class EndPointTester extends AsyncTask<String, Void, AsyncTaskResult<String>> {
		
		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setTitle("Processing...");
			progressDialog.setMessage("Please wait.");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}
		
		@Override
		protected AsyncTaskResult doInBackground(String... params) {
			try {
				if(params[0] == "/api/games/lobby")	{
					return new AsyncTaskResult<String>(lobbyAPI.getGameLobbies());
				}
				else	{
					return new AsyncTaskResult<String>(new ServerConnectionException("Error: no endpoint given"));
				}
			}
			catch(Exception e)	{
				return new AsyncTaskResult<String>(e);
			}
		}
		
		protected void onPostExecute(AsyncTaskResult result)	{
			progressDialog.dismiss();
			if(result.getException() != null)	{
				result.getException().printStackTrace();
			}
			else if(isCancelled())	{
				//Remove the pop up
			}
			else	{
				
			}
		}
	}
	
	/**
	 *	Inspiration from http://stackoverflow.com/a/6312491 
	 */
	private class AsyncTaskResult<ResultType>	{
		private ResultType result;
		private Exception exception;
		
		public AsyncTaskResult(ResultType result){
			this.result = result;
		}
		public AsyncTaskResult(Exception exception)	{
			this.exception = exception;
		}
		
		public ResultType getResult()	{
			return this.result;
		}
		
		public Exception getException()	{
			return this.exception;
		}
	}
}
