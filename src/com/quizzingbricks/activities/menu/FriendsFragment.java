package com.quizzingbricks.activities.menu;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.quizzingbricks.activities.answerQuestion.QuestionPromptActivity;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class FriendsFragment extends ListFragment implements OnTaskCompleteAsync{
		ArrayList<String> list;
		UserThreadedAPI userThreadedAPI;
		int count=0;
		
		 @Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			userThreadedAPI = new UserThreadedAPI(getActivity());
		    userThreadedAPI.getFriendsList(this);
		}  
		 
		 @Override
		 public void onListItemClick(ListView l, View v, int position, long id) {
			 if (position == 0){
				 //Add new Friend
				 Intent i = new Intent(getActivity(), AddFriendActivity.class);
				 startActivityForResult(i, 1);

			 }
		  }
		 
		@Override
		public void onComplete(AsyncTaskResult<JSONObject> result) {
		 	ArrayList<String> newfriendslist = new ArrayList<String>();
			newfriendslist.add("+Add Friend");
			try {
				if(result.hasException())	{
					System.out.println("Oh noes...");
					result.getException().printStackTrace();
				}
				JSONArray asd = result.getResult().getJSONArray("friends");
				for (int i = 0; i < asd.length(); i++) {
					JSONObject friendJson = asd.getJSONObject(i);
					Object friendEmail = friendJson.get("email");
					newfriendslist.add(friendEmail.toString());
				}									
			} catch (Exception e) {			
				e.printStackTrace();
				newfriendslist.add("No Friends");
			}
			Adapter ad = new Adapter(getActivity(), newfriendslist);
			setListAdapter(ad);
		}
	}





















