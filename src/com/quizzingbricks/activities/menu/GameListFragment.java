package com.quizzingbricks.activities.menu;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.quizzingbricks.R;
import com.quizzingbricks.activities.gameboard.GameBoardActivity;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class GameListFragment extends ListFragment implements OnTaskCompleteAsync {
	int gameID;
	ArrayList<String> list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		gameID = 1;
		UserThreadedAPI lt = new UserThreadedAPI(getActivity());
		lt.getActiveGamesList(this);
	}
	

	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		// TODO Auto-generated method stub
		
		
		if (list == null) {
			list = new ArrayList<String>();
		}
		list.clear();
//		list.add("Test game");
		try {
			JSONObject asd = result.getResult().getJSONObject("games");
//			for (int i = 0; i < asd.length(); i++) {
//				JSONObject test = asd.getJSONObject(i)get(i);
//				list[i]=asd.get(i).toString();
				list.add(asd.toString());
				
//			}
			
			
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
		
		intent.putExtra("id", gameID);
    	startActivity(intent);


	 }
}


