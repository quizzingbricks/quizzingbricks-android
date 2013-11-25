package com.quizzingbricks.authentication;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.sax.StartElementListener;

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
	
	public String getToken()	{
		return sharedPref.getString(KEY_TOKEN, null);
	}
	
	/**
	 * Checks if the users if authenticated (i.e. has a token) and changes activity to LoginActivity if not 
	 */
	public void checkAuthentication()	{
		if(!isLoggedIn())	{
			changeToLoginActivity();
		}
	}
	
	public void logout()	{
		editor.clear();
		editor.commit();
	}

	public boolean isLoggedIn()	{
		return sharedPref.getBoolean(IS_LOGIN, false);
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
				changeToMainMenuActivity();
			}
			else if(jsonResult.has("error"))	{
				JSONObject error;
				String errorMessage = "Unknown error message from server";
				try {
					error = jsonResult.getJSONObject("error");
					if(error.getString("code") == "010")	{
						errorMessage = "Wrong username or password";
					}
					changeToLoginActivity(errorMessage);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else	{
				changeToLoginActivity("Unknown answer from server");
			}
		}
		else	{
			result.getException().printStackTrace();
		}
	}
	
	private void changeToMainMenuActivity()	{
		Intent intent = new Intent(context, MenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	private void changeToLoginActivity()	{
		Intent intent = new Intent(context, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	private void changeToLoginActivity(String message)	{
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra("Message", message);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
