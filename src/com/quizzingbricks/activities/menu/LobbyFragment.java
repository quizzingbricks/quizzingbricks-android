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

import com.quizzingbricks.activities.lobby.LobbyOwnerActivity;
import com.quizzingbricks.activities.lobby.LobbySlaveActivity;
import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

public class LobbyFragment extends ListFragment implements OnTaskCompleteAsync {
	ArrayList<String> lobbynamelist;
	ArrayList<Boolean> lobbyownerlist;
	ArrayList<Integer> lobbyidlist;
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
		}if (lobbyidlist == null) {
			lobbyidlist = new ArrayList<Integer>();
		}
		lobbynamelist.clear();
		lobbynamelist.add("+ Create New Game");
		lobbyownerlist.clear();
		//This is a filler to make sure onListItemClick is correctly instansiated
		lobbyownerlist.add(null);
		lobbyidlist.clear();
		lobbyidlist.add(null);
		try {
			if(result.hasException())	{
				System.out.println("Oh noes...");
				result.getException().printStackTrace();
			}
			else	{
				JSONArray lobbyArray = result.getResult().getJSONArray("lobbies");
				for (int i = 0; i < lobbyArray.length(); i++) {
					JSONObject lobbyObject = lobbyArray.getJSONObject(i);
					int lobbyid = lobbyObject.getInt("l_id");
					int lobbysize = lobbyObject.getInt("size");
					String lobbySize = lobbyObject.getString("size") + " player lobby";
					boolean lobbyowner = lobbyObject.getBoolean("owner");
					String lobbyname;
					if (lobbyowner == true) {
						lobbyname = "Lobby "+lobbyid+"   ("+lobbySize+")\nYou are owner";
					}else {
						lobbyname = "Lobby "+lobbyid+"   ("+lobbySize +")";
					}
//					String lobbyName = lobbyOwner.toString();
					lobbynamelist.add(lobbyname);
					lobbyownerlist.add(lobbyowner);
					lobbyidlist.add(lobbyid);
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
			int lobbyid = lobbyidlist.get(position);
			i.putExtra("l_id", lobbyid);
			startActivity(i);
//			startActivityForResult(i, 2);
		} else {
			Intent i = new Intent(getActivity(), LobbySlaveActivity.class);
			int lobbyid = lobbyidlist.get(position);
			i.putExtra("l_id", lobbyid);
			startActivity(i);
		}
			
		
	}
}
