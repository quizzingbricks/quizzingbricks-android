package com.quizzingbricks;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class AuthManager {

	private final String USER_AGENT = "Mozilla/5.0";
	
	public void sendJson(final String email, final String password) {
		Thread t = new Thread()	{
			public void run()	{ // Needed to declare this method to make new objects
				HttpClient client = new DefaultHttpClient();
				JSONObject jsonMessage = new JSONObject();
				
				try {
					jsonMessage.put("email", email);
					jsonMessage.put("password", password);
					StringEntity message = new StringEntity(jsonMessage.toString());
					message.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
					
					HttpPost post = new HttpPost();
					post.setURI(new URI("http://192.168.1.6:5000/login"));
//					post.setHeader("Content-Type", "application/json");
					post.setEntity(message);
					HttpEntity entity = client.execute(post).getEntity();
					
					if(entity != null)	{
						String response = EntityUtils.toString(entity); 
						//consume the entity
				        entity.consumeContent();
				        // When HttpClient instance is no longer needed, shut down the connection manager to ensure immediate deallocation of all system resources
				        client.getConnectionManager().shutdown();
				        JSONObject object = new JSONObject(response.trim());
				        System.out.println(object.getString("username"));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
}
