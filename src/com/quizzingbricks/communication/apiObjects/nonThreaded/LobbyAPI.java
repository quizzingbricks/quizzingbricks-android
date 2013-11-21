package com.quizzingbricks.communication.apiObjects.nonThreaded;

import org.json.JSONObject;
import org.apache.http.message.BasicNameValuePair;

import com.quizzingbricks.communication.jsonObject.SimpleJsonObject;
import com.quizzingbricks.communication.jsonObject.jsonPairs.JsonPairStringList;

import android.content.Context;

/**
 * Based on REST API v0.1.5.2
 * 
 * @author David Eriksson
 *
 */
public class LobbyAPI extends AbstractAPI {
	
	/**
	 * Empty constructor for testing
	 */
	public LobbyAPI()	{}
	
	public LobbyAPI(Context context) {
		super(context);
	}

	private String serverLobbyApiUrl = this.requestParser.getServerApiAddr() + "games/lobby"; 
	
	public JSONObject getGameLobbies()	{
		return this.requestParser.getServerEndpointInfo(this.serverLobbyApiUrl, this.token);
	}
	
	public JSONObject getLobbyInfo(int id)	{
		return this.requestParser.getServerEndpointInfo(this.serverLobbyApiUrl + "/" + Integer.toString(id), this.token);
	}
	
	public JSONObject createLobby(int size)	{
		return this.requestParser.sendPostToServer(this.serverLobbyApiUrl + "/create", this.token, new BasicNameValuePair("size", Integer.toString(size)));
	}
	
	public JSONObject acceptLobbyInvitation(int lobbyId, boolean accept)	{
		BasicNameValuePair acceptPair;
		if(accept == true)	{
			acceptPair = new BasicNameValuePair("answer", "accept");
		}
		else 	{
			acceptPair = new BasicNameValuePair("answer", "deny");
		}
		return this.requestParser.sendPostToServer(this.serverLobbyApiUrl + "/" + Integer.toString(lobbyId) + "/accept", this.token, new BasicNameValuePair("lobby", Integer.toString(lobbyId)), acceptPair);
	}
	
	public JSONObject inviteToLobby(int lobbyId, String... users)	{
		BasicNameValuePair lobbyIdPair = new BasicNameValuePair("lobby", Integer.toString(lobbyId));
		JsonPairStringList jsonStringList = new JsonPairStringList("invite", users);
		SimpleJsonObject simpleJsonObject = new SimpleJsonObject();
		simpleJsonObject.addJsonField(jsonStringList);
		return this.requestParser.sendPostToServer(this.serverLobbyApiUrl + "/" + Integer.toString(lobbyId) + "/invite", serverLobbyApiUrl, simpleJsonObject, lobbyIdPair);
	}
}
