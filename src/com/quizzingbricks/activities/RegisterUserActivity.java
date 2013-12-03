package com.quizzingbricks.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.quizzingbricks.R;
import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;
import com.quizzingbricks.tools.SimplePopupWindow;

public class RegisterUserActivity extends Activity implements OnTaskCompleteAsync {
	
	private String email;
	private String password;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_register_user);
	    }
	 
	 public void sendRegisterUserInfo(View view)	{
		EditText emailEdit = (EditText) findViewById(R.id.register_user_email_edit);
		String email = emailEdit.getText().toString();
		
		EditText usernameEdit = (EditText) findViewById(R.id.register_user_username_edit);
		String username = usernameEdit.getText().toString();
		
		EditText passwordEdit = (EditText) findViewById(R.id.register_user_password_edit);
		String password = passwordEdit.getText().toString();
		
		EditText rewritePasswordEdit = (EditText) findViewById(R.id.register_user_rewrite_password_edit);
		String rewrittenPassword = rewritePasswordEdit.getText().toString();
		
		TextView textView = (TextView)findViewById(R.id.error_message_text);
		if(email.equals("") | username.equals("") | password.equals("") | rewrittenPassword.equals(""))	{
			textView.setText("Please fill in all the fields");
			new SimplePopupWindow(this).createPopupWindow("Form error", "Please fill in all the fields");
		}
		else if(!password.equals(rewrittenPassword))	{
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
		TextView textView = (TextView)findViewById(R.id.error_message_text);
		if(result.hasException())	{
			String message = result.getException().getMessage();
			textView.setText(message);
			new SimplePopupWindow(this).createPopupWindow("Form error", message);
		}
		else	{
			AuthenticationManager authManager = new AuthenticationManager(this);
			authManager.login(email, password);
		}
	}
}
