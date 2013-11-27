package com.quizzingbricks.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.quizzingbricks.R;
import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.apiObjects.asyncTasks.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;


public class LoginActivity extends Activity implements OnTaskCompleteAsync	{

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        if(intent.hasExtra("Message"))	{
        	TextView textView = (TextView)findViewById(R.id.error_message_text);
        	textView.setText(intent.getStringExtra("Message"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
    //TODO: disable the button when this function is called
    public void sendLoginUserInfo(View view) {
    	
    	EditText emailEdit = (EditText) findViewById(R.id.login_email_edit);
    	String email = emailEdit.getText().toString();
    	
    	EditText passwordEdit = (EditText) findViewById(R.id.login_password_edit);
    	String password = passwordEdit.getText().toString();
    	
    	AuthenticationManager authManager = new AuthenticationManager(LoginActivity.this);
    	authManager.login(email, password);
    	
    }

	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		if(result.getException() != null)	{
			result.getException().printStackTrace();
		}
	}
}
