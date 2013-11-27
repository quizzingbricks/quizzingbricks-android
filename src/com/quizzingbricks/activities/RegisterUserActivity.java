package com.quizzingbricks.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.quizzingbricks.R;
import com.quizzingbricks.activities.menu.MenuActivity;
import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.apiObjects.asyncTasks.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.asyncTasks.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class RegisterUserActivity extends Activity implements OnTaskCompleteAsync {
	
	private String email;
	private String password;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_register_user);
	    }
	 
	 public void sendRegisterUserInfo()	{
		EditText emailEdit = (EditText) findViewById(R.id.register_user_email_edit);
		String email = emailEdit.getText().toString();
		
		EditText usernameEdit = (EditText) findViewById(R.id.register_user_username_edit);
		String username = usernameEdit.getText().toString();
		
		EditText passwordEdit = (EditText) findViewById(R.id.register_user_password_edit);
		String password = passwordEdit.getText().toString();
		
		EditText rewritePasswordEdit = (EditText) findViewById(R.id.register_user_rewrite_password_edit);
		String rewrittenPassword = rewritePasswordEdit.getText().toString();
		
		TextView textView = (TextView)findViewById(R.id.error_message_text);
		if(email == null | username == null | password == null | rewrittenPassword == null)	{
			textView.setText("Please fill in all the fields");
		}
		else if(password != rewrittenPassword)	{
			textView.setText("The passwords in the fields do not match");
		}
		else	{
			this.email = email;
			this.password = password;
			UserThreadedAPI userAPI = new UserThreadedAPI(this, false);
			userAPI.registerUser(email, username, password, this);
		}
		
		
	 }

	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		if(result.hasException())	{
			result.getException().printStackTrace();
		}
		else	{
			JSONObject jsonResult = result.getResult();
			if(jsonResult.has("errors"))	{
				try {
					String errorCode = jsonResult.getJSONObject("errors").getString("code");
					TextView textView = (TextView)findViewById(R.id.error_message_text);
					if(errorCode == "101")	{
						textView.setText("Email already taken");
					}
					else if(errorCode == "102")	{
						textView.setText("Missing email or password");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			else	{
				AuthenticationManager authManager = new AuthenticationManager(this);
				authManager.login(email, password);
			}
		}
	}
}
