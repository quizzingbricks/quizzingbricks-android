package com.quizzingbricks.communication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.quizzingbricks.exceptions.ServerConnectionException;

public class RequestParser {
	private HttpClient httpClient = new DefaultHttpClient();
	private String serverApiAddr = "http://192.168.1.6:5000/api/";
	
	protected String getServerApiAddr()	{
		return serverApiAddr;
	}
	
	protected void cancelRequest()	{
		System.out.println("Shutting down http client");
		httpClient.getConnectionManager().shutdown(); //This will throw a java.net.SocketException: Socket closed
	}
	
	//Under construction...
	protected JSONObject postToServerEndpoint(String serverUrl, String token, SimpleJsonObject... stringPairs) throws ServerConnectionException	{
		try	{
			HttpPost httpPost= new HttpPost(serverUrl);
			httpPost.addHeader("Authorization", token);

			String jsonString = "{";
			for(SimpleJsonObject pair : stringPairs)	{
				
			}
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntiry = httpResponse.getEntity();
			httpEntiry.consumeContent();
			
			httpClient.getConnectionManager().shutdown();
			int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
			if(httpStatusCode == 200)	{
				String response = EntityUtils.toString(httpEntiry);
				JSONObject jsonObject = new JSONObject(response.trim());
				return jsonObject;
			}
			else if(httpStatusCode == 404)	{
				throw new ServerConnectionException("API Error: faulty endpoint path, " + serverUrl, 0, httpStatusCode);
			}
			else	{
				System.out.println("HTTP status code: " + httpStatusCode);
				throw new ServerConnectionException("API Error: " + serverUrl, 0, httpStatusCode);
			}
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new ServerConnectionException("API Error: " + serverUrl);
		}
	}
	
	protected JSONObject getServerEndpointInfo(String serverUrl, String token) throws ServerConnectionException	{
		try	{
			HttpGet httpGet= new HttpGet(serverUrl);
			httpGet.addHeader("Authorization", token);
			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntiry = httpResponse.getEntity();
			httpEntiry.consumeContent();
			
			httpClient.getConnectionManager().shutdown();
			int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
			if(httpStatusCode == 200)	{
				String response = EntityUtils.toString(httpEntiry);
				JSONObject jsonObject = new JSONObject(response.trim());
				return jsonObject;
			}
			else if(httpStatusCode == 404)	{
				throw new ServerConnectionException("API Error: faulty endpoint path, " + serverUrl, 0, httpStatusCode);
			}
			else	{
				System.out.println("HTTP status code: " + httpStatusCode);
				throw new ServerConnectionException("API Error: " + serverUrl, 0, httpStatusCode);
			}
			
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new ServerConnectionException("API Error: " + serverUrl);
		}
	}
}
	