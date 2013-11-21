package com.quizzingbricks.communication.apiObjects.asyncTasks;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class UserThreadedAPI extends AbstractThreadedAPI {
	
	private String serverLobbyApiPath = postCall.getServerUrl() + "users/";
	
	public UserThreadedAPI(Context context) {
		super(context);
	}
	
	public void registerUser(String email, String username, String password, OnTaskComplete onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(this.serverLobbyApiPath + "register");
		postCall.execute(new BasicNameValuePair("email", email), new BasicNameValuePair("username", username), new BasicNameValuePair("password", password));
	}
	
	public void loginUser(String email, String password, OnTaskComplete onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverLobbyApiPath + "login");
		postCall.execute(new BasicNameValuePair("email", email), new BasicNameValuePair("password", password));
	}
	
	public void getCurrentUserInfo(OnTaskComplete onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverLobbyApiPath + "me");
		getCall.execute();
	}
	
	public void getFriendsList(OnTaskComplete onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverLobbyApiPath + "me/friends");
		getCall.execute();
	}
	
	public void getActiveGamesList(OnTaskComplete onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverLobbyApiPath + "me/activegames");
		getCall.execute();
	}
	
	public void getOldGamesList(OnTaskComplete onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverLobbyApiPath + "/me/oldgames");
	}
}
