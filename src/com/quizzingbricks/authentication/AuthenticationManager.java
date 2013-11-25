package com.quizzingbricks.authentication;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.quizzingbricks.activities.LoginActivity;
import com.quizzingbricks.activities.menu.MenuActivity;
import com.quizzingbricks.communication.apiObjects.asyncTasks.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.asyncTasks.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class AuthenticationManager implements OnTaskCompleteAsync {

	private Editor editor;
	private Context context;
	private SharedPreferences sharedPref;
	
	private final String IS_LOGIN = "isLoggedIn";
	private final String KEY_TOKEN = "token";
	
	public AuthenticationManager(Context context)	{
		this.context = context;
		this.sharedPref = context.getSharedPreferences("quizzingbricks-android", 0);
		this.editor = this.sharedPref.edit();
	}
	
	public void login(String email, String password)		{
		UserThreadedAPI userAPI = new UserThreadedAPI(context, false);
		userAPI.loginUserWithPopup(email, password, "Logging in", "Please wait...", context, this);
	}
	
	public void logout()	{
		editor.clear();
		editor.commit();
	}

	public boolean isLoggedIn()	{
		return sharedPref.getBoolean(IS_LOGIN, false);
	}
	
	public String getToken()	{
		return sharedPref.getString(KEY_TOKEN, null);
	}
	
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		if(result.getException() == null)	{
			JSONObject jsonResult = result.getResult();
			if(jsonResult.has("token"))	{
				try {
					editor.putString(KEY_TOKEN, jsonResult.getString("token"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				editor.putBoolean(IS_LOGIN, true);
				editor.commit();
				Intent intent = new Intent(context, MenuActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
			else if(jsonResult.has("error"))	{
				JSONObject error;
				String errorMessage = "Unknown error message from server";
				try {
					error = jsonResult.getJSONObject("error");
					if(error.getString("code") == "010")	{
						errorMessage = "Wrong username or password";
					}
					Intent intent = new Intent(context, LoginActivity.class);
					intent.putExtra("Message", errorMessage);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else	{
				Intent intent = new Intent(context, LoginActivity.class);
				intent.putExtra("Message", "Unknown answer from server");
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		}
		else	{
			result.getException().printStackTrace();
		}
	}
	
//	/**
//	 * Checks if the users if authenticated (i.e. has a token) and changes activity to LoginActivity if not 
//	 */
//	public void checkAuthentication()	{
//		if(!isLoggedIn())	{
//			Intent intent = new Intent(this.context, LoginActivity.class);
//			// Closing all the Activities
//			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			this.context.startActivity(intent);
//		}
//	}
//	
//	
//	private class AuthenticateTask extends AsyncTask<String, Void, AsyncTaskResult<String>> {
//
//		private String email;
//		private String password;
//		private ProgressDialog progressDialog;
//		
//		private HttpClient client = new DefaultHttpClient();
//		private HttpPost httppost = new HttpPost(serverUrl);
//		
//		@Override
//		protected void onPreExecute() {
//			progressDialog = new ProgressDialog(context);
//			progressDialog.setTitle("Processing...");
//			progressDialog.setMessage("Please wait.");
//			progressDialog.setCancelable(false);
//			progressDialog.setIndeterminate(true);
//			final AuthenticateTask authTask = this; //Declared so that the class is in the scope of the OnClickListener 
//			progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//			    @Override
//			    public void onClick(DialogInterface dialog, int which) {
//			    	client.getConnectionManager().shutdown();
//			    	authTask.cancel(true);
//			    	progressDialog.dismiss();
//			    }
//			});
//			progressDialog.show();
//			progressDialog.show();
//		}
//		
//		
//		@Override
//		protected AsyncTaskResult<String> doInBackground(String... params) {
//			
//			try {
//				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//				this.email = params[0];
//				this.password = params[1];
//				nameValuePairs.add(new BasicNameValuePair("email", this.email));
//				nameValuePairs.add(new BasicNameValuePair("password", this.password));
//				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//				HttpEntity entity = client.execute(httppost).getEntity();
//				
//				if(entity != null)	{
//					String response = EntityUtils.toString(entity); 
//			        entity.consumeContent();
//			        client.getConnectionManager().shutdown();
//			        JSONObject jsonObject = new JSONObject(response.trim());
//			        try {
//			        	return new AsyncTaskResult<String>(jsonObject.getString("token"));
//			        }
//			        catch (JSONException je){
//			        	return new AsyncTaskResult<String>(jsonObject.getString("error"));
//			        }
//				}
//				else	{
//					return new AsyncTaskResult<String>(new ServerConnectionException("No return message from server"));
//				}
//				
//			} 
//			catch (ClientProtocolException e) {
//				return new AsyncTaskResult<String>(new ServerConnectionException("Could not send HTTP Post to server"));
//			}
//			catch (Exception e)	{
//				return new AsyncTaskResult<String>(e);
//			}
//		}
//		
//		protected void onPostExecute(AsyncTaskResult<String> result)	{
//			progressDialog.dismiss();
//			if(result.getException() != null)	{
//				result.getException().printStackTrace();
//			}
//			else if(isCancelled())	{
//				//Remove the pop up
//			}
//			else	{
//				editor.putBoolean(IS_LOGIN, true);
//				editor.putString(KEY_EMAIL, this.email);
//				editor.putString(KEY_TOKEN, result.getResult());
//				editor.commit();
//				Intent intent = new Intent(context, MainScreenActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(intent);
//			}
//		}
//
//	}
//	/**
//	 *	Inspiration from http://stackoverflow.com/a/6312491 
//	 */
//	private class AsyncTaskResult<ResultType>	{
//		private ResultType result;
//		private Exception exception;
//		
//		public AsyncTaskResult(ResultType result){
//			this.result = result;
//		}
//		public AsyncTaskResult(Exception exception)	{
//			this.exception = exception;
//		}
//		
//		public ResultType getResult()	{
//			return this.result;
//		}
//		
//		public Exception getException()	{
//			return this.exception;
//		}
//	}
}
