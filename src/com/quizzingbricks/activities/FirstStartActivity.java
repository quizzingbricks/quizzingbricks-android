package com.quizzingbricks.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.quizzingbricks.R;

import com.quizzingbricks.activities.menu.MainMenuActivity;

import com.quizzingbricks.activities.answerQuestion.QuestionPromptActivity;
import com.quizzingbricks.activities.gameboard.GameBoardActivity;

import com.quizzingbricks.authentication.AuthenticationManager;

public class FirstStartActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthenticationManager authManager = new AuthenticationManager(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first_start);
        if(authManager.isLoggedIn()) {
        	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        	setContentView(R.layout.activity_first_start);
        	Intent intent = new Intent(this, MainMenuActivity.class);
        	startActivity(intent);
        }
    } 
	
	public void changeToLoginActivity(View view)	{
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	
	public void changeToRegisterUserActivity(View view)	{
		Intent intent = new Intent(this, RegisterUserActivity.class);
		startActivity(intent);
	}
}
