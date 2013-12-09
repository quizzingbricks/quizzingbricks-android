package com.quizzingbricks.activities.lobby;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
		ActionBar ab = getActionBar();
        ab.setTitle("Create Lobby");
        ab.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_create_lobby);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case android.R.id.home:
	        	userBackButtonPress();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
    }
	
	@Override
	public void onBackPressed() {
		userBackButtonPress();
	}
	
	private void userBackButtonPress()	{
		Intent intent = new Intent();
		intent.putExtra("canceledByUser", true);
		setResult(RESULT_CANCELED, intent);
        finish();
	}
	
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		// TODO Auto-generated method stub
		System.out.println("GOT RESULT IN CREATE GAME");
//		System.out.println(result.getResult().toString());
		try {
			if(result.hasException())	{
				result.getException().printStackTrace();
			}
			else if (result.getResult().has("lobby")){
				System.out.println(result.getResult().toString());
				Intent returnIntent = new Intent();
				setResult(RESULT_OK,returnIntent);     
				finish();

			}
			else if(result.getResult().has("errors")) {
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED,returnIntent);     
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
