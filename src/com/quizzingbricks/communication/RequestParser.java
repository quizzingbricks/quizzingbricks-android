package com.quizzingbricks.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.quizzingbricks.communication.jsonObject.SimpleJsonObject;
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
	
	protected JSONObject sendPostToServer(String serverUrl, String token, List<BasicNameValuePair> nameValuePairList) throws ServerConnectionException	{
		try	{
			HttpPost httpPost= new HttpPost(serverUrl);
			httpPost.addHeader("token", token);

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
			return executeAndGetRequest(httpPost, serverUrl);
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new ServerConnectionException("API Error: " + serverUrl);
		}
	}
	
	protected JSONObject sendPostToServer(String serverUrl, String token, BasicNameValuePair... nameValuePairs) throws ServerConnectionException	{
		try	{
			HttpPost httpPost= new HttpPost(serverUrl);
			httpPost.addHeader("token", token);
			
			List<BasicNameValuePair> nameValuePairList = new ArrayList<BasicNameValuePair>();
			for(BasicNameValuePair nameValuePair : nameValuePairs)	{
				nameValuePairList.add(nameValuePair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
			return executeAndGetRequest(httpPost, serverUrl);
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new ServerConnectionException("API Error: " + serverUrl);
		}
	}
	
	protected JSONObject postJsonToServer(String serverUrl, String token, SimpleJsonObject simpleJsonObject) throws ServerConnectionException	{
		try	{
			HttpPost httpPost= new HttpPost(serverUrl);
			httpPost.addHeader("token", token);
			httpPost.addHeader("Content-type", "application/json");
			httpPost.setEntity(new StringEntity(simpleJsonObject.toJsonString()));
			
			return executeAndGetRequest(httpPost, serverUrl);
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new ServerConnectionException("API Error: " + serverUrl);
		}
	}
	
	protected JSONObject getServerEndpointInfo(String serverUrl, String token) throws ServerConnectionException	{
		try	{
			HttpGet httpGet= new HttpGet(serverUrl);
			httpGet.addHeader("token", token);
			
			return executeAndGetRequest(httpGet, serverUrl);
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new ServerConnectionException("API Error: " + serverUrl);
		}
	}
	
	private JSONObject executeAndGetRequest(HttpUriRequest httpRequest, String serverUrl) throws ServerConnectionException, ClientProtocolException, IOException, JSONException	{
		HttpResponse httpResponse = httpClient.execute(httpRequest);
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
}
	