package com.quizzingbricks.authentication;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.quizzingbricks.activities.FirstStartActivity;
import com.quizzingbricks.activities.LoginActivity;
import com.quizzingbricks.activities.menu.MainMenuActivity;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class AuthenticationManager extends Activity implements OnTaskCompleteAsync {

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
	
	public boolean hasToken()	{
		if(getToken() == null)	{
			return false;
		}
		else	{
			return true;
		}
	}
	
	public String getToken()	{
		return sharedPref.getString(KEY_TOKEN, null);
	}
	
	public void registerNewUser(String email, String password, String username)	{
		UserThreadedAPI userAPI = new UserThreadedAPI(context, false);
		userAPI.registerUser(email, username, password, this);
	}
	
	/**
	 * Checks if the users if authenticated (i.e. has a token) and changes activity to LoginActivity if not 
	 */
	public void checkAuthentication()	{
		if(!isLoggedIn())	{
			changeToFirstStartActivity();
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
			if(jsonResult == null)	{
				changeToLoginActivity("Empty response from server");
			}
			else if(jsonResult.has("token"))	{
				try {
					editor.putString(KEY_TOKEN, jsonResult.getString("token"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				editor.putBoolean(IS_LOGIN, true);
				editor.commit();
				changeToMainMenuActivity();
			}
			else if(jsonResult.has("errors"))	{
				handleJSONErrorMessage(jsonResult);
			}
			else	{
				changeToLoginActivity(jsonResult.toString());
			}
		}
		else	{
			handleExceptions(result.getException());
		}
	}
	
	private void handleExceptions(Exception exception)	{
		changeToLoginActivity(exception.getMessage());
	}
	
	//TODO: implement parsing of multiple error messages 
	private void handleJSONErrorMessage(JSONObject jsonObject)	{
		try	{
			JSONObject errorObject = jsonObject.getJSONArray("errors").getJSONObject(0);
			changeToLoginActivity(errorObject.getString("message"));
		}
		catch(JSONException je)	{
			je.printStackTrace();
		}
	}
	
	private void changeToMainMenuActivity()	{
		//Currently MainMenu instead of Menu
		Intent intent = new Intent(this.context, MainMenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.context.startActivity(intent);
	}
	
	private void changeToFirstStartActivity()	{
		Intent intent = new Intent(context, FirstStartActivity.class);
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
