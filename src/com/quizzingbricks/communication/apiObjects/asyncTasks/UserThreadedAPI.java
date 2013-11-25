package com.quizzingbricks.communication.apiObjects.asyncTasks;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class UserThreadedAPI extends AbstractThreadedAPI {
	
	private String serverUserApiPath = "users/";
	
	/**
	 * Empty constructor for testing
	 */
	public UserThreadedAPI()	{}
	
	public UserThreadedAPI(Context context) {
		super(context);
	}
	
	public void registerUser(String email, String username, String password, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(this.serverUserApiPath);
		postCall.execute(new BasicNameValuePair("email", email), new BasicNameValuePair("username", username), new BasicNameValuePair("password", password));
	}
	
	public void loginUser(String email, String password, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverUserApiPath + "login");
		postCall.execute(new BasicNameValuePair("email", email), new BasicNameValuePair("password", password));
	}
	
	public void getCurrentUserInfo(OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverUserApiPath + "me");
		getCall.execute();
	}
	
	
	public void getActiveGamesList(OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverUserApiPath + "me/activegames");
		getCall.execute();
	}
	
	public void getOldGamesList(OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverUserApiPath + "/me/oldgames");
	}
	
	public void getFriendsList(OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverUserApiPath + "me/friends");
		getCall.execute();
	}
	
	public void addFriendToFriendsList(String email, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverUserApiPath + "me/friends");
		postCall.execute(new BasicNameValuePair("friend", email));
	}
	
	public void removeFriendFromFriendsList(int userId, OnTaskCompleteAsync onTaskCompleteClass)	{
		deleteCall.addOnTaskComplete(onTaskCompleteClass);
		deleteCall.addToTheEndOfUrl(serverUserApiPath + "me/friends/" + Integer.toString(userId));
		deleteCall.execute();
	}
	
}