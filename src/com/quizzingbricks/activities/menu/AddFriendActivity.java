package com.quizzingbricks.activities.menu;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.quizzingbricks.R;
import com.quizzingbricks.communication.apiObjects.asyncTasks.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.asyncTasks.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class AddFriendActivity extends Activity implements OnTaskCompleteAsync{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);
		
		
		
		
	  //Need input for inputting email of friend
		//Need to give toast or something in onComplete to alert user
	}
	public void sendAddFriend(View view){
		EditText emailEdit = (EditText) findViewById(R.id.add_friend_email);
    	String email = emailEdit.getText().toString();
		UserThreadedAPI userThreadedAPI = new UserThreadedAPI(this);
	    userThreadedAPI.addFriendToFriendsList(email, this);
//	    Toast.makeText(this, "lol", 2).show();
	    
	}
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		// TODO Response to user that friend has been added?
		try {
			 if (result.getResult().toString() == "OK"){
//				 int lol = getIntent().getExtras().getInt("id");
//				 System.out.println(lol);
				 Intent returnIntent = new Intent();
//				 returnIntent.putExtra("result",lol);
				 setResult(RESULT_OK,returnIntent);     
				 finish();
//				finishActivity(1);
//				GTAPI.sendAnswer(gameId, 1, this);
			}
			else {
//				String selectedValue = (String) getListAdapter().getItem(position);
//				Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
				Intent returnIntent = new Intent();
//				 returnIntent.putExtra("result",1);
				 setResult(RESULT_CANCELED,returnIntent);     
				 finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Intent returnIntent = new Intent();
		setResult(RESULT_OK,returnIntent);
		finish();
		
		
	}
	
}
