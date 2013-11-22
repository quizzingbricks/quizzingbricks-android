package com.quizzingbricks.communication.apiObjects.asyncTasks;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class GamesThreadedAPI extends AbstractThreadedAPI {

	private String serverLobbyApiPath = "games/";
	
	public GamesThreadedAPI(Context context) {
		super(context);
	}
	
	public void getGameInfo(int gameId, OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverLobbyApiPath + Integer.toString(gameId));
		getCall.execute();
	}
	
	public void placeBricks(int gameId, int xCordinate, int yCordinate, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl("games/" + Integer.toString(gameId) + "/play/move");
		BasicNameValuePair xCordinatePair = new BasicNameValuePair("x", Integer.toString(xCordinate));
		BasicNameValuePair yCordinatePair = new BasicNameValuePair("y", Integer.toString(yCordinate));
		postCall.execute(xCordinatePair, yCordinatePair);
	}
	
	public void getQuestion(int gameId, OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverLobbyApiPath + Integer.toString(gameId) + "/play/question");
		getCall.execute();
	}
	
	public void sendAnswer(int gameId, int answer, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverLobbyApiPath + Integer.toString(answer) + "/play/question");
		postCall.execute(new BasicNameValuePair("answer", Integer.toString(answer)));
	}
}
