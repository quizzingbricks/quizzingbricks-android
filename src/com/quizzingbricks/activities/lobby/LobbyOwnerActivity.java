package com.quizzingbricks.activities.lobby;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.quizzingbricks.R;
import com.quizzingbricks.R.layout;
import com.quizzingbricks.R.menu;
import com.quizzingbricks.activities.answerQuestion.QuestionPromptAdapter;
import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LobbyOwnerActivity extends ListActivity implements OnTaskCompleteAsync {

	private int lobbyId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.lobbyId = getIntent().getIntExtra("l_id", 0);
		ActionBar ab = getActionBar();
	    ab.setTitle("Lobby " + Integer.toString(lobbyId));
	    ab.setDisplayHomeAsUpEnabled(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case android.R.id.home:
	            finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
    }

	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		JSONObject jsonResult = result.getResult();
		System.out.println(jsonResult.toString());
		if(result.hasException())	{
			result.getException().printStackTrace();
		}
		else if(result.hasResult())	{
			if(jsonResult.has("lobby"))	{
				createOwnerLobby(result.getResult());
			}
			else if(jsonResult.has("errors"))	{
				try {
					System.out.println("Error message from server: " + jsonResult.getJSONObject("errors").getString("message"));
					Toast.makeText(this, jsonResult.getJSONObject("errors").getString("message"), Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else	{
				setResult(RESULT_OK, new Intent());
				finish();
			}
		}
	}
	
	private void createOwnerLobby(JSONObject jsonObject)	{
		try {
			ArrayList<String> playerList = new ArrayList<String>();
			JSONArray jsonPlayerList;
			ListView listView = getListView();
			
			final Context context = this; 
			final int lobbyId = this.lobbyId;
			int whiteSpacePadding = 20;
		
			Button addFriendButton = new Button(this);
			addFriendButton.setText("Add friend");
			addFriendButton.setOnClickListener(new Button.OnClickListener() {
			    public void onClick(View v) {
			    	Intent intent = new Intent(context, LobbyInviteFriendActivity.class);
			    	intent.putExtra("l_id", lobbyId);
			    	((Activity) context).finish();
		            context.startActivity(intent);
			    }
			});
			addFriendButton.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
			listView.addHeaderView(addFriendButton);
			
			Button startGameButton = new Button(this);
			startGameButton.setText("Start game");
			startGameButton.setOnClickListener(new OnStartGameClick(this, this, this));
			startGameButton.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
			listView.addHeaderView(startGameButton);
			
			TextView textView = new TextView(this);
			textView.setTextSize(18);
			textView.setText("Players in lobby");
			textView.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
			listView.addHeaderView(textView, "Players in lobby", false);
			
			jsonPlayerList = jsonObject.getJSONObject("lobby").getJSONArray("players");
			for(int i=0; i<=jsonPlayerList.length()-1; i++)	{
				JSONObject currentUser = jsonPlayerList.getJSONObject(i);
				playerList.add(currentUser.getString("u_mail") + " - " + currentUser.getString("status"));
			}
			QuestionPromptAdapter adapter = new QuestionPromptAdapter(this, playerList, "Not needed");
			
//			ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this, R.layout.list_layout_no_image, playerList);
//			setListAdapter(stringAdapter);
			
			setListAdapter(adapter);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class OnStartGameClick implements OnClickListener	{

		private LobbyOwnerActivity lobbyOwnerActivity;
		private Context context;
		private Activity activity;
		
		public OnStartGameClick(LobbyOwnerActivity lobbyOwnerActivity, Context context, Activity activity)	{
			this.lobbyOwnerActivity = lobbyOwnerActivity;
			this.context = context;
			this.activity = activity;
		}
		
		@Override
		public void onClick(View arg0) {
			new LobbyThreadedAPI(context).startGame(lobbyId, lobbyOwnerActivity);
		}
		
	}

}
