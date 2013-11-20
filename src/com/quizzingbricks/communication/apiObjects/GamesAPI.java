package com.quizzingbricks.communication.apiObjects;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

public class GamesAPI extends AbstractAPI {
	
	private String serverGameApiUrl = this.requestParser.getServerApiAddr() + "games/";
	
	public GamesAPI(Context context)	{
		super(context);
	}
	
	public JSONObject getGameInfo(int gameId)	{
		return this.requestParser.getServerEndpointInfo(serverGameApiUrl + Integer.toString(gameId), this.token);
	}
	
	public JSONObject placeBrick(int gameId, int xCordinate, int yCordinate)	{
		BasicNameValuePair xCordinatePair = new BasicNameValuePair("x", Integer.toString(xCordinate));
		BasicNameValuePair yCordinatePair = new BasicNameValuePair("y", Integer.toString(yCordinate));
		return this.requestParser.sendPostToServer(serverGameApiUrl + Integer.toString(gameId) + "/play/move", this.token, xCordinatePair, yCordinatePair);
	}
	
	public JSONObject getQuestion(int gameId)	{
		return this.requestParser.getServerEndpointInfo(serverGameApiUrl + Integer.toString(gameId) + "/play/question", this.token);
	}
	
	public JSONObject sendAnswer(int gameId, int answer)	{
		BasicNameValuePair answerPair = new BasicNameValuePair("answer", Integer.toString(answer));
		return this.requestParser.sendPostToServer(serverGameApiUrl + Integer.toString(answer) + "/play/answer", this.token, answerPair);
	}
	
}
