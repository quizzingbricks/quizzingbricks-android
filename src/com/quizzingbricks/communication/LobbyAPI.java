package com.quizzingbricks.communication;

import org.json.JSONObject;

import android.content.Context;

public class LobbyAPI extends AbstractAPI {
	
	/**
	 * Empty constructor for testing
	 */
	public LobbyAPI()	{}
	
	public LobbyAPI(Context context) {
		super(context);
	}

	private String serverLobbyApiUrl = super.requestParser.getServerApiAddr() + "games/lobby"; 
	
	public JSONObject getGameLobbies()	{
		return super.requestParser.getServerEndpointInfo(this.serverLobbyApiUrl, super.token);
	}
	
	public JSONObject getLobbyInfo(int id)	{
		return super.requestParser.getServerEndpointInfo(this.serverLobbyApiUrl + "/" + Integer.toString(id), super.token);
	}
}
