package com.quizzingbricks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {

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
    
//    public void sendLoginUserInfo(View view)	{
//    	Intent intent = new Intent(this, MainScreen.class);
//    	EditText email = (EditText) findViewById(R.id.login_email_edit);
//    	String message = email.getText().toString();
//    	intent.putExtra(EXTRA_MESSAGE, message);
//    }
    public void sendLoginUserInfo(View view)	{
    	 EditText emailEdit = (EditText) findViewById(R.id.login_email_edit);
         String email = emailEdit.getText().toString();
         
         EditText passwordEdit = (EditText) findViewById(R.id.login_password_edit);
         String password = passwordEdit.getText().toString();
         
         AuthenticationManager authManager = new AuthenticationManager(Login.this);
         authManager.login(email, password);
//    	Intent intent = new Intent(this, MenuActivity.class);
//    	startActivity(intent);
//    	setContentView(R.layout.activity_gameboard);
    }
}
