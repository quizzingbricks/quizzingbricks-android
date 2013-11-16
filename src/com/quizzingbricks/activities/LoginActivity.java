package com.quizzingbricks.activities;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.testing.*;

public class LoginActivity extends Activity {

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
    	
    	EditText emailEdit = (EditText) findViewById(R.id.login_email_edit);
    	String email = emailEdit.getText().toString();
    	
    	EditText passwordEdit = (EditText) findViewById(R.id.login_password_edit);
    	String password = passwordEdit.getText().toString();
    	
    	AuthenticationManager authManager = new AuthenticationManager(LoginActivity.this);
    	authManager.login(email, password);
    	
//    	APITester tester = new APITester(LoginActivity.this);
//    	tester.testGetLobbies();
    }
}
