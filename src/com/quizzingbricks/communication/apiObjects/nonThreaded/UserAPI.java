package com.quizzingbricks.communication.apiObjects.nonThreaded;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

public class UserAPI extends AbstractAPI {
	
	private String serverUserApiUrl = this.requestParser.getServerApiAddr() + "/users/";
	
	public UserAPI(Context context)	{
		super(context);
	}
	
	public JSONObject registerUser(String email, String username, String password)	{
		BasicNameValuePair emailPair = new BasicNameValuePair("email", email);
		BasicNameValuePair usernamePair = new BasicNameValuePair("username", username);
		BasicNameValuePair passwordPair = new BasicNameValuePair("password", password);
		return this.requestParser.sendPostToServer(serverUserApiUrl + "register", this.token, emailPair, usernamePair, passwordPair);
	}
	
	//Is right now handled by the Authenticator class
	public JSONObject loginUser(String email, String password)	{
		BasicNameValuePair emailPair = new BasicNameValuePair("email", email);
		BasicNameValuePair passwordPair = new BasicNameValuePair("password", password);
		return this.requestParser.sendPostToServer(serverUserApiUrl + "login", this.token, emailPair, passwordPair);
	}
	
	public JSONObject getCurrentUserInfo()	{
		return this.requestParser.getServerEndpointInfo(serverUserApiUrl + "me", this.token);
	}
	
	public JSONObject getFriendsList()	{
		return this.requestParser.getServerEndpointInfo(serverUserApiUrl + "me/friends", this.token);
	}
	
	public JSONObject getActiveGamesList()	{
		return this.requestParser.getServerEndpointInfo(serverUserApiUrl + "me/activegames", this.token);
	}
	
	public JSONObject getOldGamesList()	{
		return this.requestParser.getServerEndpointInfo(serverUserApiUrl + "me/oldgames", this.token);
	}
}
