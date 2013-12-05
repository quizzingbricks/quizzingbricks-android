package com.quizzingbricks.activities.lobby;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quizzingbricks.R;
import com.quizzingbricks.activities.answerQuestion.QuestionPromptAdapter;
import com.quizzingbricks.activities.menu.CreateLobbyActivity;
import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class LobbyInviteFriendActivity extends ListActivity implements OnTaskCompleteAsync {
	private ArrayList<String> userIdList = new ArrayList<String>(); //Will have the same positions as the users in the friends list
	private int lobbyId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_lobby_invite_friend);
		this.lobbyId = getIntent().getIntExtra("l_id", 0);
		ActionBar ab = getActionBar();
	    ab.setTitle("Invite to Lobby " + Integer.toString(lobbyId));
	    new UserThreadedAPI(this).getFriendsList(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lobby_invite_friend, menu);
		return true;
	}
	
//	public void sendFriendInvite(View view)		{
//		EditText friendEdit = (EditText) findViewById(R.id.lobby_invite_friend_edit_text);
//    	String friend = friendEdit.getText().toString();
//    	
//    	ArrayList<String> friendArray = new ArrayList<String>();
//    	friendArray.add(friend);
//    	
//    	new LobbyThreadedAPI(this).invitetoLobby(lobbyId, friendArray, this);
//	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ArrayList<String> userToAdd = new ArrayList<String>();
		userToAdd.add(this.userIdList.get(position));
		new LobbyThreadedAPI(this).invitetoLobby(this.lobbyId, userToAdd, this);
	}

	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		JSONObject jsonResult = result.getResult();
		if(result.hasException())	{
			result.getException().printStackTrace();
		}
		else if(jsonResult.has("friends"))	{
			createFriendsList(jsonResult);
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
			finish();
			Intent intent = new Intent(this, LobbyOwnerActivity.class);
			intent.putExtra("l_id", lobbyId);
			startActivity(intent);
		}
	}
	
	private void createFriendsList(JSONObject jsonObject)	{
		ArrayList<String> playerList = new ArrayList<String>();
		JSONArray jsonPlayerList;
		ListView listView = getListView();
		
		TextView textView = new TextView(this);
		textView.setTextSize(18);
		int whiteSpacePadding = 20;
		textView.setText("Friends List");
		textView.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
		listView.addHeaderView(textView, "Friends List", false);
		
		try {
			jsonPlayerList = jsonObject.getJSONArray("friends");
			for(int i=0; i<=jsonPlayerList.length()-1; i++)	{
				playerList.add(jsonPlayerList.getJSONObject(i).getString("email"));
				this.userIdList.add(jsonPlayerList.getJSONObject(i).getString("id"));
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

}
