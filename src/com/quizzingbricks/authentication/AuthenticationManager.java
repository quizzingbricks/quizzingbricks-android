package com.quizzingbricks.authentication;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.quizzingbricks.R;
import com.quizzingbricks.activities.FirstStartActivity;
import com.quizzingbricks.activities.LoginActivity;

import com.quizzingbricks.activities.RegisterUserActivity;

import com.quizzingbricks.activities.menu.MainMenuActivity;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.communication.apiObjects.apiCalls.AbstractApiCall;
import com.quizzingbricks.tools.AsyncTaskResult;

public class AuthenticationManager extends Activity implements OnTaskCompleteAsync {

	private Context context;
	private SharedPreferences sharedPref;
	protected ProgressDialog progressDialog;
	
	private final String IS_LOGIN = "isLoggedIn";
	private final String KEY_TOKEN = "token";
	private final String KEY_USER_ID = "userId";
	
	private String popUpTitle = "Loggin in";
	private String popUpMessage = "Please wait...";
	
	public AuthenticationManager(Context context)	{
		this.context = context;
		this.sharedPref = context.getSharedPreferences("quizzingbricks-android", 0);
	}
	
	public void login(String email, String password)		{
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(popUpTitle);
		progressDialog.setMessage(popUpMessage);
		
		final UserThreadedAPI userAPI = new UserThreadedAPI(context, false);
		
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	userAPI.CancelRequest();
		    	progressDialog.dismiss();
		    }
		});
		userAPI.loginUser(email, password, this);
		
//		UserThreadedAPI userAPI = new UserThreadedAPI(context, false);
//		userAPI.loginUserWithPopup(email, password, "Logging in", "Please wait...", context, this);
	}
	
	public int getUserId() throws RuntimeException	{
		int userId = sharedPref.getInt(KEY_USER_ID, 0);
		if(userId == 0)	{
			throw new RuntimeException("User id not found");
		}
		else	{
			return userId;
		}
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
		Editor editor = this.sharedPref.edit();
		editor.clear();
		editor.commit();
		changeToFirstStartActivity();
	}

	public boolean isLoggedIn()	{
		return sharedPref.getBoolean(IS_LOGIN, false);
	}
	
	
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		progressDialog.dismiss();
		if(result.getException() == null)	{
			JSONObject jsonResult = result.getResult();
			if(jsonResult == null)	{
				changeToLoginActivity("Empty response from server");
			}
			else if(jsonResult.has("token"))	{
				try {
					String token = jsonResult.getString("token");
					Editor editor = this.sharedPref.edit();
					editor.putString(KEY_TOKEN, token);
					editor.putBoolean(IS_LOGIN, true);
					editor.commit();
					new UserThreadedAPI(this, token).getCurrentUserInfo(this);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else if(jsonResult.has("id"))	{
				try {
					Editor editor = this.sharedPref.edit();
					editor.putInt(KEY_USER_ID, jsonResult.getInt("id"));
					editor.commit();
				} catch (JSONException e) {
					e.printStackTrace();
				}
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
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //Only for api level 11
		this.context.startActivity(intent);
	}
	
	private void changeToFirstStartActivity()	{
		Intent intent = new Intent(context, FirstStartActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //Only for api level 11
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	private void changeToLoginActivity(String message)	{
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra("Message", message);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //Only for api level 11
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
