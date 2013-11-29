package com.quizzingbricks.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.quizzingbricks.communication.jsonObject.SimpleJsonObject;
import com.quizzingbricks.exceptions.ServerConnectionException;

public class RequestParser {
	private DefaultHttpClient httpClient = new DefaultHttpClient();
//	private String serverApiAddr = "http://192.168.1.6:5000/api/";
//	private String serverApiAddr = "http://130.240.93.141:5000/api/";
	private String serverApiAddr = "http://130.240.233.81:8000/api/";
	
	public RequestParser()	{
		httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
	}
	
	public String getServerApiAddr()	{
		return serverApiAddr;
	}
	
	public void cancelRequest()	{
		httpClient.getConnectionManager().shutdown(); //This will throw a java.net.SocketException: Socket closed
	}
	
	public JSONObject sendPostToServer(String serverUrl, String token, List<BasicNameValuePair> nameValuePairList) throws ServerConnectionException	{
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
	
	//Just for the /api/games/lobby/<I_id>/invite endpoint 
	public JSONObject sendPostToServer(String serverUrl, String token, SimpleJsonObject simpleJsonObject, BasicNameValuePair... nameValuePairs) throws ServerConnectionException	{
		try	{
			HttpPost httpPost= new HttpPost(serverUrl);
			httpPost.addHeader("token", token);
			List<BasicNameValuePair> nameValuePairList = new ArrayList<BasicNameValuePair>();
			nameValuePairList.add(new BasicNameValuePair("invite", simpleJsonObject.toJsonString()));
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
	
	public JSONObject sendPostToServer(String serverUrl, String token, BasicNameValuePair... nameValuePairs) throws ServerConnectionException	{
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
	
	public JSONObject postJsonToServer(String serverUrl, String token, SimpleJsonObject simpleJsonObject) throws ServerConnectionException	{
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
	
	public JSONObject getServerEndpointInfo(String serverUrl, String token) throws ServerConnectionException	{
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
	
	public JSONObject sendDeleteRequestToServer(String serverUrl, String token) throws ServerConnectionException	{
			HttpDelete httpDelete = new HttpDelete(serverUrl);
			httpDelete.addHeader("token", token);
			try {
				return executeAndGetRequest(httpDelete, serverUrl);
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
			JSONObject jsonObject;
			String response = EntityUtils.toString(httpEntiry);
			try	{
				jsonObject = new JSONObject(response.trim());
			}
			catch(JSONException je)	{
				jsonObject = new JSONObject("{\"result\":\"ok\"}");
			}
			return jsonObject;
		}
		else if(httpStatusCode == 400)	{
			throw new ServerConnectionException("API Error: bad request, " + serverUrl, 0, httpStatusCode);
		}
		else if(httpStatusCode == 404)	{
			throw new ServerConnectionException("API Error: faulty endpoint path, " + serverUrl, 0, httpStatusCode);
		}
		else if(httpStatusCode == 500)	{
			throw new ServerConnectionException("API Error: internal server error, " + serverUrl, 0, httpStatusCode);
		}
		else	{
			System.out.println("HTTP status code: " + httpStatusCode);
			throw new ServerConnectionException("API Error: " + serverUrl, 0, httpStatusCode);
		}
	}
}
	