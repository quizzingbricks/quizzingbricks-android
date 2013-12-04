package com.quizzingbricks.activities.lobby;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.quizzingbricks.R;
import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

public class LobbyInviteFriendActivity extends Activity implements OnTaskCompleteAsync {

	private int lobbyId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby_invite_friend);
		this.lobbyId = getIntent().getIntExtra("l_id", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lobby_invite_friend, menu);
		return true;
	}
	
	public void sendFriendInvite(View view)		{
		EditText friendEdit = (EditText) findViewById(R.id.lobby_invite_friend_edit_text);
    	String friend = friendEdit.getText().toString();
    	
    	ArrayList<String> friendArray = new ArrayList<String>();
    	friendArray.add(friend);
    	
    	new LobbyThreadedAPI(this).invitetoLobby(lobbyId, friendArray, this);
	}

	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		JSONObject jsonResult = result.getResult();
		if(result.hasException())	{
			result.getException().printStackTrace();
		}
		else if(jsonResult.has("error"))	{
			try {
				Toast.makeText(this, jsonResult.getJSONObject("error").getString("message"), 3);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		else	{
			finish();
			Intent intent = new Intent(this, LobbyOwnerActivity.class);
			intent.putExtra("l_id", lobbyId);
			startActivity(intent);
		}
	}

}
