package com.quizzingbricks.activities;

import android.app.Activity;
import com.quizzingbricks.authentication.AuthenticationManager;

/**
 * Implements a authentication check on start
 * 
 * @author David Eriksson
 *
 */
public abstract class AbstractActivity extends Activity {
	
	protected void onStart()	{
		AuthenticationManager authManager = new AuthenticationManager(this);
		authManager.checkAuthentication();
	}
}
