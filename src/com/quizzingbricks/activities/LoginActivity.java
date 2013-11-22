package com.quizzingbricks.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.quizzingbricks.R;
import com.quizzingbricks.communication.apiObjects.asyncTasks.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.asyncTasks.OnTaskComplete;
import com.quizzingbricks.communication.apiObjects.asyncTasks.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.asyncTasks.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;
import com.testing.*;

public class LoginActivity extends Activity implements OnTaskCompleteAsync	{

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
    //TODO: disable the button when this function is called
    public void sendLoginUserInfo(View view) {
    	
//    	EditText emailEdit = (EditText) findViewById(R.id.login_email_edit);
//    	String email = emailEdit.getText().toString();
//    	
//    	EditText passwordEdit = (EditText) findViewById(R.id.login_password_edit);
//    	String password = passwordEdit.getText().toString();
//    	
//    	AuthenticationManager authManager = new AuthenticationManager(LoginActivity.this);
//    	authManager.login(email, password);
    	
//    	APITester tester = new APITester(LoginActivity.this);
//    	tester.testGetLobbies();
    	
//    	new LobbyThreadedAPI().createLobby(2, this);
//    	new LobbyThreadedAPI().getGameLobbies(this);
    	new UserThreadedAPI().removeFriendFromFriendsList(1, this);
    }

	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		if(result.getException() == null)	{
			System.out.println("Yay I got a response from the server");
		}
		else	{
			result.getException().printStackTrace();
		}
	}
}
