package com.quizzingbricks.activities.lobby;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.quizzingbricks.R;
import com.quizzingbricks.R.layout;
import com.quizzingbricks.R.menu;
import com.quizzingbricks.activities.answerQuestion.QuestionPromptAdapter;
import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LobbySlaveActivity extends ListActivity implements OnTaskCompleteAsync {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby_slave);
		new LobbyThreadedAPI(this).getLobbyInfo(getIntent().getIntExtra("l_id", 0), this);
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
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		JSONObject jsonResult = result.getResult();
		if(result.hasException())	{
			result.getException().printStackTrace();
		}
		else if(result.hasResult())	{
			if(jsonResult.has("l_id"))	{
				createFriendsList(jsonResult);
			}
		}
	}
	
	
	private void createFriendsList(JSONObject jsonObject)	{
		ArrayList<String> playerList = new ArrayList<String>();
		JSONArray jsonPlayerList;
		try {
			jsonPlayerList = jsonObject.getJSONArray("players");
			for(int i=0; i<=jsonPlayerList.length()-1; i++)	{
				playerList.add(jsonPlayerList.getString(i));
			}
			ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this, R.layout.list_layout_no_image, playerList);
			ListView listView = getListView();
			
			TextView textView = new TextView(this);
			textView.setTextSize(18);
			int whiteSpacePadding = 20;
			textView.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
			listView.addHeaderView(textView, "Players in lobby", false);
			setListAdapter(stringAdapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
