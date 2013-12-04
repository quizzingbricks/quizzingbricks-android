package com.quizzingbricks.activities.lobby;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.quizzingbricks.R;
import com.quizzingbricks.R.layout;
import com.quizzingbricks.R.menu;
import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LobbyOwnerActivity extends ListActivity implements OnTaskCompleteAsync {

	private int lobbyId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby_owner);
		this.lobbyId = getIntent().getIntExtra("l_id", 0);
		new LobbyThreadedAPI(this).getLobbyInfo(this.lobbyId, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lobby_owner, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		if(result.hasException())	{
			result.getException().printStackTrace();
		}
		else if(result.hasResult())	{
			createOwnerLobby(result.getResult());
		}
	}
	
	private void createOwnerLobby(JSONObject jsonObject)	{
		try {
			ArrayList<String> playerList = new ArrayList<String>();
			JSONArray jsonPlayerList;
			ListView listView = getListView();
			
			final Context context = this; 
			final int lobbyId = this.lobbyId;
		
			Button addFriendButton = new Button(this);
			addFriendButton.setText("Add friend");
			addFriendButton.setOnClickListener(new Button.OnClickListener() {
			    public void onClick(View v) {
			    	Intent intent = new Intent(context, LobbyInviteFriendActivity.class);
			    	intent.putExtra("l_id", lobbyId);
		            context.startActivity(intent);
			    }
			});
			listView.addHeaderView(addFriendButton);
			
			Button startGameButton = new Button(this);
			startGameButton.setText("Start game");
			startGameButton.setOnClickListener(new OnStartGameClick(this, this));
			listView.addHeaderView(startGameButton);
			
			TextView textView = new TextView(this);
			textView.setTextSize(18);
			int whiteSpacePadding = 20;
			textView.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
			listView.addHeaderView(textView, "Players in lobby", false);
			
			jsonPlayerList = jsonObject.getJSONArray("players");
			for(int i=0; i<=jsonPlayerList.length()-1; i++)	{
				playerList.add(jsonPlayerList.getString(i));
			}
			
			ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this, R.layout.list_layout_no_image, playerList);
			setListAdapter(stringAdapter);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class OnStartGameClick implements OnClickListener	{

		private LobbyOwnerActivity lobbyOwnerActivity;
		private Context context;
		
		public OnStartGameClick(LobbyOwnerActivity lobbyOwnerActivity, Context context)	{
			this.lobbyOwnerActivity = lobbyOwnerActivity;
			this.context = context;
		}
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			new LobbyThreadedAPI(context).startGame(lobbyId, lobbyOwnerActivity);
		}
		
	}

}
