package com.quizzingbricks.activities.menu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.bool;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

public class LobbyFragment extends ListFragment implements OnTaskCompleteAsync {
	ArrayList<String> lobbynamelist;
	ArrayList<Boolean> lobbyownerlist;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LobbyThreadedAPI lobbyThreadedAPI = new LobbyThreadedAPI(getActivity());
		lobbyThreadedAPI.getGameLobbies(this);
		
	}
	
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		// TODO Auto-generated method stub
		if (lobbynamelist == null) {
			lobbynamelist = new ArrayList<String>();
		}if (lobbyownerlist == null) {
			lobbyownerlist = new ArrayList<Boolean>();
		}
		lobbynamelist.clear();
		lobbynamelist.add("+Create New Game");
		lobbyownerlist.clear();
		//This is a filler to make sure onListItemClick is correctly instansiated
		lobbyownerlist.add(null);
		try {
			if(result.hasException())	{
				System.out.println("Oh noes...");
				result.getException().printStackTrace();
			}
			else	{
				JSONArray lobbyArray = result.getResult().getJSONArray("lobbies");
				for (int i = 0; i < lobbyArray.length(); i++) {
					JSONObject lobbyObject = lobbyArray.getJSONObject(i);
					Object lobbyid = lobbyObject.get("l_id");
					boolean lobbyowner = lobbyObject.getBoolean("owner");
					String lobbyname;
					if (lobbyowner == true) {
						lobbyname = "Lobby "+lobbyid.toString()+"\nYou are owner";
					}else {
						lobbyname = "Lobby "+lobbyid.toString();
					}
//					String lobbyName = lobbyOwner.toString();
					lobbynamelist.add(lobbyname);
					lobbyownerlist.add(lobbyowner);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			lobbynamelist.add("No Lobbys");
		}
		Adapter md = new Adapter(getActivity(), lobbynamelist);
		setListAdapter(md);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if (position == 0) {
			 Intent i = new Intent(getActivity(), CreateLobbyActivity.class);
			 startActivityForResult(i, 1);
		} else if (lobbyownerlist.get(position)) {
			Intent i = new Intent(getActivity(), LobbyOwnerActivity.class);
			 startActivity(i);
		} else {
			Intent i = new Intent(getActivity(), LobbySlaveActivity.class);
			 startActivity(i);
		}
			
		
		
		
	}
}
