package com.quizzingbricks.communication;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.JsonWriter;

import com.quizzingbricks.exceptions.ServerConnectionException;

public class LobbyAPI {
	
	private String serverUrl = "http://130.240.94.184:5000/games/lobby"; 
	private String token = "RandomStringGoesHere"; //Token for testing
	private HttpClient httpClient = new DefaultHttpClient();
	
	/**
	 * 
	 * @param context, needed to get the token from the shared preferences
	 */
	public LobbyAPI(Context context)	{
//		AuthenticationManager authManager = new AuthenticationManager(context);
//		this.token = authManager.getToken();
//		if(token == null)	{
//			authManager.checkAuthentication();
//		}
	}
	//TODO: change the return type to string list
	public String getGameLobbies() throws ServerConnectionException	{
		try	{
			HttpGet httpGet= new HttpGet(serverUrl);
			httpGet.addHeader("X-Auth-Token", token);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntiry = httpResponse.getEntity();
			httpClient.getConnectionManager().shutdown();
			int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
			if(httpStatusCode == 200)	{
		        JSONObject jsonObject = new JSONObject(EntityUtils.getContentCharSet(httpEntiry).trim());
		        System.out.println("Game Lobbies: " + jsonObject.getString("lobbies"));
		        return jsonObject.getString("lobbies");
			}
			else	{
				throw new ServerConnectionException("API Error: /api/games/lobby", 0, httpStatusCode);
			}
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new ServerConnectionException("Unknown error", 0);
		}
	}
}
