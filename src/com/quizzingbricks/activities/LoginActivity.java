package com.quizzingbricks.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.quizzingbricks.R;
import com.quizzingbricks.R.id;
import com.quizzingbricks.R.layout;
import com.quizzingbricks.R.menu;
import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.exceptions.ServerConnectionException;

public class LoginActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
    public void sendLoginUserInfo(View view) {
    	EditText emailEdit = (EditText) findViewById(R.id.login_email_edit);
    	String email = emailEdit.getText().toString();
    	
    	EditText passwordEdit = (EditText) findViewById(R.id.login_password_edit);
    	String password = passwordEdit.getText().toString();
    	
    	AuthenticationManager authManager = new AuthenticationManager(getApplicationContext());
    	try {
    		authManager.login(email, password);
			Intent intent = new Intent(this, MainScreenActivity.class);
	    	intent.putExtra(EXTRA_MESSAGE, email);
	    	startActivity(intent);
		} catch (ServerConnectionException e) {
			// TODO: make a pop up notification
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO: make a pop up notification
			e.printStackTrace();
		}
    }
}
