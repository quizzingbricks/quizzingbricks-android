package com.quizzingbricks.activities.lobby;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quizzingbricks.R;
import com.quizzingbricks.activities.answerQuestion.QuestionPromptAdapter;
import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

public class LobbySlaveActivity extends ListActivity implements OnTaskCompleteAsync {

	private boolean waitingForYourInviteAnswer = false;
	private int lobbyId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		this.lobbyId = getIntent().getIntExtra("l_id", 0);
	    ab.setTitle("Lobby " + Integer.toString(lobbyId));
	    getActionBar().setDisplayHomeAsUpEnabled(true);
		new LobbyThreadedAPI(this).getLobbyInfo(lobbyId, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lobby_slave, menu);
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
		if(result.hasException())	{
			result.getException().printStackTrace();
		}
		else if(result.hasResult())	{
			if(jsonResult.has("lobby"))	{
				createFriendsList(jsonResult);
			}
			else if(jsonResult.has("errors"))	{
				try {
					System.out.println("Error message from server: " + jsonResult.getJSONObject("errors").getString("message"));
					Toast.makeText(this, jsonResult.getJSONObject("errors").getString("message"), Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else	{
				Toast.makeText(this, "Invite answer sent", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	private void createFriendsList(JSONObject jsonObject)	{
		System.out.println(jsonObject.toString());
		ArrayList<String> playerList = new ArrayList<String>();
		JSONArray jsonPlayerList;
		try {
			ListView listView = getListView();
			
			TextView textView = new TextView(this);
			textView.setTextSize(18);
			int whiteSpacePadding = 20;
			textView.setText("Players in lobby");
			textView.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
			listView.addHeaderView(textView, "Players in lobby", false);
			
			int userId = 0;
			try	{
				userId = new AuthenticationManager(this).getUserId();
			}
			catch(RuntimeException re)	{
				new AuthenticationManager(this).checkAuthentication();
			}
			jsonPlayerList = jsonObject.getJSONObject("lobby").getJSONArray("players");
			for(int i=0; i<=jsonPlayerList.length()-1; i++)	{
				JSONObject currentUser = jsonPlayerList.getJSONObject(i);
				if(currentUser.getInt("u_id") == userId && currentUser.get("status").equals("waiting"))	{
					this.waitingForYourInviteAnswer = true;
				}
				playerList.add(currentUser.getString("u_mail") + " - " + currentUser.getString("status"));
			}
			QuestionPromptAdapter adapter = new QuestionPromptAdapter(this, playerList, "Not needed");
//			ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this, R.layout.list_layout_no_image, playerList);
			
//			setListAdapter(stringAdapter);
			setListAdapter(adapter);
			if(this.waitingForYourInviteAnswer)		{
				makeYesNoPopup(jsonObject.getJSONObject("lobby").getInt("l_id") ,
						jsonObject.getJSONObject("lobby").getJSONArray("players").getJSONObject(0).getString("u_mail"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void makeYesNoPopup(final int lobbyId, String userName)	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Invite");
    	dialogBuilder.setMessage("Accept invite from " + userName + "?");
    	final LobbySlaveActivity lobbySlaveActivity = (LobbySlaveActivity) this;
    	dialogBuilder.setNegativeButton("Deny", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				new LobbyThreadedAPI(lobbySlaveActivity).acceptLobbyInvitation(lobbyId, false, lobbySlaveActivity);
			}
    		
    	});
    	dialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener()	{
    		
    		@Override
    		public void onClick(DialogInterface dialog, int arg1) {
    			dialog.dismiss();
    			new LobbyThreadedAPI(lobbySlaveActivity).acceptLobbyInvitation(lobbyId, true, lobbySlaveActivity);
    		}
    		
    	});
    	dialogBuilder.show();
	}
}
