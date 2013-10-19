package com.quizzingbricks.authentication;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.quizzingbricks.activities.LoginActivity;
import com.quizzingbricks.exceptions.ServerConnectionException;

public class AuthenticationManager {

	private Editor editor;
	private Context context;
	private SharedPreferences sharedPref;
	
	private final String IS_LOGIN = "isLoggedIn";
	private final String KEY_EMAIL = "email";
	private final String KEY_TOKEN = "token";
	
	public AuthenticationManager(Context context)	{
		this.context = context;
		this.sharedPref = context.getSharedPreferences("quizzingbricks-android", 0);
		this.editor = this.sharedPref.edit();
	}
	
	public void login(String email, String password) throws ServerConnectionException, Exception		{
		String token = getToken(email, password);
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_EMAIL, email);
		editor.putString(KEY_TOKEN, token);
		editor.commit();
	}
	
	public void logout()	{
		editor.clear();
		editor.commit();
	}
	
	/**
	 * Checks if the users if authenticated (i.e. has a token) and changes activity to LoginActivity if necessary
	 */
	public void checkAuthentication()	{
		if(!isLoggedIn())	{
			Intent intent = new Intent(this.context, LoginActivity.class);
			// Closing all the Activities
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.context.startActivity(intent);
		}
	}
	
	public boolean isLoggedIn()	{
		return sharedPref.getBoolean(IS_LOGIN, false);
	}
	
	// TODO: make sure that it runs as a async task
	private String getToken(String email, String password) throws Exception, ServerConnectionException {
		HttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost();
		
		try {
			httppost.setURI(new URI("http://130.240.96.181:5000/login"));
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("email", email));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpEntity entity = client.execute(httppost).getEntity();
			
			if(entity != null)	{
				String response = EntityUtils.toString(entity); 
				//consume the entity
		        entity.consumeContent();
		        // When HttpClient instance is no longer needed, shut down the connection manager to ensure immediate deallocation of all system resources
		        client.getConnectionManager().shutdown();
		        JSONObject object = new JSONObject(response.trim());
		        try {
		        	return object.getString("token");
		        }
		        catch (JSONException je){
		        	return object.getString("error");
		        }
			}
			else	{
				throw new ServerConnectionException("No return message from server");
			}
			
		} catch (ClientProtocolException e) {
			throw new ServerConnectionException("Could not send HTTP Post to server");
		} 
	}
}
