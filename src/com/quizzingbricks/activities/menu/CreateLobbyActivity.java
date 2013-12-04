package com.quizzingbricks.activities.menu;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.quizzingbricks.R;
import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

public class CreateLobbyActivity  extends Activity implements OnTaskCompleteAsync{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_lobby);
	
	}
	
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		// TODO Auto-generated method stub
		System.out.println("GOT RESULT IN CREATE GAME");
//		System.out.println(result.getResult().toString());
		try {
			if(result.hasException())	{
				System.out.println("Oh noes...");
				result.getException().printStackTrace();
			}
			else if (result.getResult().has("lobby")){
				System.out.println(result.getResult().toString());
				Intent returnIntent = new Intent();
				setResult(RESULT_OK,returnIntent);     
				finish();

			}
//			else {
//
//				Intent returnIntent = new Intent();
//				setResult(RESULT_CANCELED,returnIntent);     
//				finish();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Intent returnIntent = new Intent();
		setResult(RESULT_CANCELED,returnIntent);
		finish();
		
	}
	public void create2(View v){
		LobbyThreadedAPI lobbyThreadedAPI = new LobbyThreadedAPI(this);
		lobbyThreadedAPI.createLobby(2, this);
	}
	public void create4(View v){
		LobbyThreadedAPI lobbyThreadedAPI = new LobbyThreadedAPI(this);
		lobbyThreadedAPI.createLobby(4, this);
	}
}
