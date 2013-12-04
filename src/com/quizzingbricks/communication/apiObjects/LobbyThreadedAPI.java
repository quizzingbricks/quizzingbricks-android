package com.quizzingbricks.communication.apiObjects;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.quizzingbricks.communication.jsonObject.SimpleJsonObject;
import com.quizzingbricks.communication.jsonObject.jsonPairs.JsonPairStringList;

import android.content.Context;

public class LobbyThreadedAPI extends AbstractThreadedAPI {

	private String serverLobbyApiPath = "games/lobby/";
	
	public LobbyThreadedAPI(Context context) {
		super(context, true);
	}
	
	public LobbyThreadedAPI(Context context, String token) {
		super(context, true, token);
	}
	
	public void createLobby(int size, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverLobbyApiPath);
		postCall.execute(new BasicNameValuePair("size", Integer.toString(size)));
	}
	
	public void getGameLobbies(OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverLobbyApiPath);
		getCall.execute();
	}
	
	public void acceptLobbyInvitation(int lobbyId, boolean accept, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverLobbyApiPath + "/" + Integer.toString(lobbyId) + "/accept/");
		BasicNameValuePair acceptPair;
		if(accept == true)	{
			acceptPair = new BasicNameValuePair("answer", "accept"); 
		}
		else	{
			acceptPair = new BasicNameValuePair("answer", "deny");
		}
		BasicNameValuePair lobbyIdPair = new BasicNameValuePair("lobby", Integer.toString(lobbyId));
		postCall.execute(acceptPair, lobbyIdPair);
	}
	
	public void getLobbyInfo(int id, OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverLobbyApiPath + "/" + Integer.toString(id) + "/");
		getCall.execute();
	}
	
	public void invitetoLobby(int lobbyId, List<String> users, OnTaskCompleteAsync onTaskCompleteClass)		{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverLobbyApiPath + "/" + Integer.toString(lobbyId) + "/accept/");
		JsonPairStringList jsonStringList = new JsonPairStringList("invite", users);
		SimpleJsonObject jsonObject = new SimpleJsonObject();
		jsonObject.addJsonField(jsonStringList);
		postCall.addSimpleJsonObject(jsonObject);
		postCall.execute();
	}
	
	public void startGame(int lobbyId, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverLobbyApiPath + "/" + Integer.toString(lobbyId) + "/start/");
		postCall.execute();
	}
	
	public void endGame(int lobbyId, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverLobbyApiPath + "/" + Integer.toString(lobbyId) + "/end/");
		postCall.execute();
	}
}
