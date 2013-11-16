package com.testing;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.renderscript.Sampler.Value;
import android.view.LayoutInflater;

import com.quizzingbricks.communication.LobbyAPI;
import com.quizzingbricks.exceptions.ServerConnectionException;

public class APITester {
	
	private Context context;
	
	public APITester(Context context)	{
		this.context = context;
	}
	
	public void testGetLobbies()	{
		EndPointTester task = new EndPointTester();
		System.out.println("Starting async task");
		task.execute("/api/games/lobby");
	}
	
	private class EndPointTester extends AsyncTask<String, Void, AsyncTaskResult<JSONObject>> {
		
		private LobbyAPI lobbyAPI = new LobbyAPI();
		private ProgressDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setTitle("Processing...");
			progressDialog.setMessage("Please wait.");
			progressDialog.setCancelable(false); //Controls the back button
			progressDialog.setIndeterminate(true);
			progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			    	lobbyAPI.cancelRequest();
			    	progressDialog.dismiss();
			    }
			});
			progressDialog.show();
		}
		
		/**
		 * @param params, param[0] is the api url path
		 */
		@Override
		protected AsyncTaskResult<JSONObject> doInBackground(String... params) {
			try {
				if(params[0] == "/api/games/lobby")	{
					JSONObject result = lobbyAPI.getGameLobbies();
					return new AsyncTaskResult<JSONObject>(result);
				}
				else	{
					return new AsyncTaskResult<JSONObject>(new ServerConnectionException("Error: no endpoint given"));
				}
			}
			catch(Exception e)	{
				e.printStackTrace();
				return new AsyncTaskResult<JSONObject>(e);
			}
		}
		
		protected void onPostExecute(AsyncTaskResult<JSONObject> result)	{
			progressDialog.dismiss();
			if(result.getException() != null)	{
				result.getException().printStackTrace();
			}
			else if(isCancelled())	{
				//Remove the pop up
			}
			else	{
				System.out.println("Return string: " + result.result.toString());
			}
		}
		
		@Override
        protected void onCancelled() {
			progressDialog.dismiss();
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
