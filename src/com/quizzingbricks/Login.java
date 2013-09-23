package com.quizzingbricks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
    
    public void sendLoginUserInfo(View view)	{
    	Intent intent = new Intent(this, MainScreen.class);
    	EditText email = (EditText) findViewById(R.id.login_email_edit);
    	String message = email.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    }
}
