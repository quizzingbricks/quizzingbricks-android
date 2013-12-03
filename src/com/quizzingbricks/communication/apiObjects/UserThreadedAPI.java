package com.quizzingbricks.communication.apiObjects;

import org.apache.http.message.BasicNameValuePair;

import com.quizzingbricks.communication.apiObjects.apiCalls.AsyncApiPostCall;

import android.content.Context;

public class UserThreadedAPI extends AbstractThreadedAPI {
	
	private String serverUserApiPath = "users/";
	
	public UserThreadedAPI(Context context) {
		super(context, true);
	}
	
	public UserThreadedAPI(Context context, boolean sendWithToken) {
		super(context, sendWithToken);
	}
	
	public void registerUser(String email, String username, String password, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(this.serverUserApiPath);
		postCall.execute(new BasicNameValuePair("email", email), new BasicNameValuePair("username", username), new BasicNameValuePair("password", password));
	}
	
	public void registerUserWithPopup(String email, String username, String password, String popUpTitle, String popUpMessage, Context context, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(this.serverUserApiPath);
		postCall.addPopup(popUpTitle, popUpMessage, context);
		postCall.execute(new BasicNameValuePair("email", email), new BasicNameValuePair("username", username), new BasicNameValuePair("password", password));
	}
	
	public void loginUser(String email, String password, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverUserApiPath + "login/");
		postCall.execute(new BasicNameValuePair("email", email), new BasicNameValuePair("password", password));
	}
	
	public void loginUserWithPopup(String email, String password, String popUpTitle, String popUpMessage, Context context, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverUserApiPath + "login/");
		postCall.addPopup(popUpTitle, popUpMessage, context);
		postCall.execute(new BasicNameValuePair("email", email), new BasicNameValuePair("password", password));
	}
	
	public void getCurrentUserInfo(OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverUserApiPath + "me/");
		getCall.execute();
	}
	
	public void getOldGamesList(OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverUserApiPath + "/me/oldgames/");
		getCall.execute();
	}
	
	public void getFriendsList(OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverUserApiPath + "me/friends/");
		getCall.execute();
	}
	
	public void addFriendToFriendsList(String email, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverUserApiPath + "me/friends/");
		postCall.execute(new BasicNameValuePair("friend", email));
	}
	
	public void removeFriendFromFriendsList(int userId, OnTaskCompleteAsync onTaskCompleteClass)	{
		deleteCall.addOnTaskComplete(onTaskCompleteClass);
		deleteCall.addToTheEndOfUrl(serverUserApiPath + "me/friends/" + Integer.toString(userId));
		deleteCall.execute();
	}
	
}
