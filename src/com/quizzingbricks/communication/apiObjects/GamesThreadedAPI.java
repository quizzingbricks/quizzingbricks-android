package com.quizzingbricks.communication.apiObjects;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

public class GamesThreadedAPI extends AbstractThreadedAPI {

	private String serverGameApiPath = "games/";
	
	public GamesThreadedAPI(Context context) {
		super(context, true);
	}
	
	public void getGameInfo(int gameId, OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverGameApiPath + Integer.toString(gameId));
		getCall.execute();
	}
	
	public void placeBricks(int gameId, int xCordinate, int yCordinate, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl("games/" + Integer.toString(gameId) + "/play/move/");
		BasicNameValuePair xCordinatePair = new BasicNameValuePair("x", Integer.toString(xCordinate));
		BasicNameValuePair yCordinatePair = new BasicNameValuePair("y", Integer.toString(yCordinate));
		postCall.execute(xCordinatePair, yCordinatePair);
	}
	
	public void getQuestion(int gameId, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverGameApiPath + Integer.toString(gameId) + "/play/question/");
		postCall.execute();
	}
	
	public void sendAnswer(int gameId, int answer, OnTaskCompleteAsync onTaskCompleteClass)	{
		postCall.addOnTaskComplete(onTaskCompleteClass);
		postCall.addToTheEndOfUrl(serverGameApiPath + Integer.toString(gameId) + "/play/answer/");
		postCall.execute(new BasicNameValuePair("answer", Integer.toString(answer)));
	}
	
	public void getActiveGames(OnTaskCompleteAsync onTaskCompleteClass)	{
		getCall.addOnTaskComplete(onTaskCompleteClass);
		getCall.addToTheEndOfUrl(serverGameApiPath);
		getCall.execute();
	}
}
