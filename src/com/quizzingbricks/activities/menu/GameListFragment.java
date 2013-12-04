package com.quizzingbricks.activities.menu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.quizzingbricks.activities.gameboard.GameBoardActivity;
import com.quizzingbricks.communication.apiObjects.GamesThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

public class GameListFragment extends ListFragment implements OnTaskCompleteAsync {
	ArrayList<String> gameIDlist;
	ArrayList<String> gamenamelist;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//just set -1 so we don't get wierd errors
//		gameID = -1;
		GamesThreadedAPI lt = new GamesThreadedAPI(getActivity());
		lt.getActiveGames(this);
	}
	
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		// TODO Auto-generated method stub

		if (gameIDlist == null) {
			gameIDlist = new ArrayList<String>();
		}
		if (gamenamelist == null) {
			gamenamelist = new ArrayList<String>();
		}
		gameIDlist.clear();
		gamenamelist.clear();
		try {
			if(result.hasException())	{
				System.out.println("Oh noes...");
				result.getException().printStackTrace();
			}
			else	{
				JSONArray gamearray = result.getResult().getJSONArray("games");
				for (int i = 0; i < gamearray.length(); i++) {
					JSONObject onegame = gamearray.getJSONObject(i);
					Object gameid = onegame.get("id");
					Object gamesize = onegame.get("size");
					int gamestate = onegame.getInt("state");
					
					String gamename;
					if (gamestate==0) {
						gamename = "Game "+gameid.toString()+"   ("+gamesize.toString()+")\nYou can place brick";
					}else if (gamestate==1) {
						gamename = "Game "+gameid.toString()+"   ("+gamesize.toString()+")\nSomeone tried to take you brick. Question fight!";
					}else if (gamestate==2) {
						gamename = "Game "+gameid.toString()+"   ("+gamesize.toString()+")\nYou are answering question!";	
					}else if (gamestate==3) {
						gamename = "Game "+gameid.toString()+"   ("+gamesize.toString()+")\nWaiting for other player";	
					}else {
						gamename = "Game "+gameid.toString()+" has bad state, contact support";
					}
					
//					String gamename = "Game "+gameid.toString()+"   ("+gamesize.toString()+")\nState "+gamestate.toString();
					gamenamelist.add(gamename);
					gameIDlist.add(gameid.toString());
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			gamenamelist.add("No Games");
		}
		Adapter md = new Adapter(getActivity(), gamenamelist);
		setListAdapter(md);
	}
	
	@Override
	 public void onListItemClick(ListView l, View v, int position, long id) {
		int gameID;
		Intent intent = new Intent(getActivity(), GameBoardActivity.class);
		try {
			gameID = Integer.parseInt(gameIDlist.get(position));
			intent.putExtra("id", gameID);
	    	startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "Can not start game", 2).show();
		}
		


	 }
}


