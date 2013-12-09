package com.quizzingbricks.activities.menu;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.quizzingbricks.R;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class AddFriendActivity extends Activity implements OnTaskCompleteAsync{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setTitle("Add friend");
        ab.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_friend);
		
	  //Need input for inputting email of friend
		//Need to give toast or something in onComplete to alert user
	}
	public void sendAddFriend(View view){
		EditText emailEdit = (EditText) findViewById(R.id.add_friend_email);
    	String email = emailEdit.getText().toString();
//    	System.out.println(email);
		UserThreadedAPI userThreadedAPI = new UserThreadedAPI(this);
	    userThreadedAPI.addFriendToFriendsList(email, this);
//	    Toast.makeText(this, "lol", 2).show();
	    
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case android.R.id.home:
	        	userBackButtonPress();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
    }
	
	@Override
	public void onBackPressed() {
		userBackButtonPress();
	}
	
	private void userBackButtonPress()	{
		Intent intent = new Intent();
		intent.putExtra("canceledByUser", true);
		setResult(RESULT_CANCELED, intent);
        finish();
	}
	
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		// TODO Response to user that friend has been added?
		System.out.println("GOT RESULT IN ADD FRIEND");
//		System.out.println(result.getResult().toString());
		try {
			if(result.hasException())	{
				System.out.println("Oh noes...");
				result.getException().printStackTrace();
			}
			else if (result.getResult().get("result").equals("ok")){
				System.out.println(result.getResult().toString());
				Intent returnIntent = new Intent();
				setResult(RESULT_OK,returnIntent);  
				finish();

			}
//			else {
//
//				Intent returnIntent = new Intent();
//				setResult(RESULT_CANCELED,returnIntent);     
//				finish();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Intent returnIntent = new Intent();
		setResult(RESULT_CANCELED,returnIntent);
		finish();
		
	}
	
}
