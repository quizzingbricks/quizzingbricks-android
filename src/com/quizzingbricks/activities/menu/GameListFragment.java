package com.quizzingbricks.activities.menu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.quizzingbricks.activities.gameboard.GameBoardActivity;
import com.quizzingbricks.communication.apiObjects.GamesThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

public class GameListFragment extends ListFragment implements OnTaskCompleteAsync {
	int gameID;
	ArrayList<String> list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//just set -1 so we don't get wierd errors
		gameID = -1;
		GamesThreadedAPI lt = new GamesThreadedAPI(getActivity());
		lt.getActiveGames(this);
	}
	
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		// TODO Auto-generated method stub

		if (list == null) {
			list = new ArrayList<String>();
		}
		list.clear();
		try {
			if(result.hasException())	{
				System.out.println("Oh noes...");
				result.getException().printStackTrace();
			}
			else	{
				JSONArray asd = result.getResult().getJSONArray("games");
				for (int i = 0; i < asd.length(); i++) {
					Object gameid = asd.get(i);
					list.add(gameid.toString());
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			list.add("No Games");
		}
		Adapter md = new Adapter(getActivity(), list);
		setListAdapter(md);
	}
	
	@Override
	 public void onListItemClick(ListView l, View v, int position, long id) {
		
		Intent intent = new Intent(getActivity(), GameBoardActivity.class);
		try {
			gameID = Integer.parseInt(list.get(position));
		} catch (Exception e) {
			gameID = -1;
		}
		intent.putExtra("id", gameID);
    	startActivity(intent);


	 }
}


