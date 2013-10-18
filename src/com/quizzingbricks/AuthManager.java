package com.quizzingbricks;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthManager {

//	private final String USER_AGENT = "Mozilla/5.0";
	
	public void sendJson(final String email, final String password) {
		Thread t = new Thread()	{
			public void run()	{ // Needed to declare this method to make new objects
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
				        	System.out.println(object.getString("token"));
				        }
				        catch (JSONException je){
				        	System.out.println(object.getJSONObject("error").getString("message"));
				        }
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
}
