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
import com.quizzingbricks.exceptions.APIException;

public class RequestParser {
	private DefaultHttpClient httpClient = new DefaultHttpClient();
//	private String serverApiAddr = "http://192.168.1.6:5000/api/";
//	private String serverApiAddr = "http://130.240.93.181:5000/api/";
	private String serverApiAddr = "http://api.quizzingbricks.130.240.233.81.xip.io/api/";
	
	public String getServerApiAddr()	{
		return serverApiAddr;
	}
	
	public void cancelRequest()	{
		httpClient.getConnectionManager().shutdown(); //This will throw a java.net.SocketException: Socket closed
	}
	
	public JSONObject sendPostToServer(String serverUrl, String token, List<BasicNameValuePair> nameValuePairList) throws APIException	{
		try	{
			HttpPost httpPost= new HttpPost(serverUrl);
			httpPost.addHeader("token", token);

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
			return executeAndGetRequest(httpPost, serverUrl);
		}
		catch(APIException se)	{
			throw se;
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new APIException("API Error: " + serverUrl);
		}
	}
	
	//Just for the /api/games/lobby/<I_id>/invite endpoint 
	public JSONObject sendPostToServer(String serverUrl, String token, SimpleJsonObject simpleJsonObject, BasicNameValuePair... nameValuePairs) throws APIException	{
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
		catch(APIException se)	{
			throw se;
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new APIException("API Error: " + serverUrl);
		}
	}
	
	public JSONObject sendPostToServer(String serverUrl, String token, BasicNameValuePair... nameValuePairs) throws APIException	{
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
		catch(APIException se)	{
			throw se;
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new APIException("API Error: " + serverUrl);
		}
	}
	
	public JSONObject postJsonToServer(String serverUrl, String token, SimpleJsonObject simpleJsonObject) throws APIException	{
		try	{
			HttpPost httpPost= new HttpPost(serverUrl);
			httpPost.addHeader("token", token);
			httpPost.addHeader("Content-type", "application/json");
			httpPost.setEntity(new StringEntity(simpleJsonObject.toJsonString()));
			
			return executeAndGetRequest(httpPost, serverUrl);
		}
		catch(APIException se)	{
			throw se;
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new APIException("API Error: " + serverUrl);
		}
	}
	
	public JSONObject getServerEndpointInfo(String serverUrl, String token) throws APIException	{
		try	{
			HttpGet httpGet= new HttpGet(serverUrl);
			httpGet.addHeader("token", token);
			
			return executeAndGetRequest(httpGet, serverUrl);
		}
		catch(APIException se)	{
			throw se;
		}
		catch(Exception e)	{
			e.printStackTrace();
			throw new APIException("API Error: " + serverUrl);
		}
	}
	
	public JSONObject sendDeleteRequestToServer(String serverUrl, String token) throws APIException	{
			HttpDelete httpDelete = new HttpDelete(serverUrl);
			httpDelete.addHeader("token", token);
			try {
				return executeAndGetRequest(httpDelete, serverUrl);
			} 
			catch(APIException se)	{
				throw se;
			}
			catch(Exception e)	{
				e.printStackTrace();
				throw new APIException("API Error: " + serverUrl);
			}
	}
	
	private JSONObject executeAndGetRequest(HttpUriRequest httpRequest, String serverUrl) throws APIException, ClientProtocolException, IOException, JSONException	{
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		HttpEntity httpEntity = httpResponse.getEntity();
		
		int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
		if(httpStatusCode == 200)	{
			JSONObject jsonObject;
			try	{
				String response = EntityUtils.toString(httpEntity);
				jsonObject = new JSONObject(response.trim());
			}
			catch(JSONException je)	{
				jsonObject = new JSONObject("{\"result\":\"ok\"}");
			}
			catch(IOException e)	{
				e.printStackTrace();
				jsonObject = new JSONObject("{\"responseContent\":\"empty\"}");
			}
			httpClient.getConnectionManager().shutdown();
			return jsonObject;
		}
		else if(httpStatusCode == 400)	{
			try	{
				JSONObject jsonObject;
				String response = EntityUtils.toString(httpEntity);
				jsonObject = new JSONObject(response.trim());
				//TODO: handle multiple error objects in the JSON array
				JSONObject jsonErrorMessage = jsonObject.getJSONArray("errors").getJSONObject(0);
				httpClient.getConnectionManager().shutdown();
				throw new APIException(jsonErrorMessage.getString("message"), jsonErrorMessage.getInt("code"), httpStatusCode);
			}
			catch(JSONException e)	{
				e.printStackTrace();
				httpClient.getConnectionManager().shutdown();
				throw new APIException("API Error: bad request, " + serverUrl, 0, httpStatusCode);
			}
		}
		else if(httpStatusCode == 404)	{
			httpClient.getConnectionManager().shutdown();
			throw new APIException("API Error: faulty endpoint path, " + serverUrl, 0, httpStatusCode);
		}
		else if(httpStatusCode == 500)	{
			httpClient.getConnectionManager().shutdown();
			throw new APIException("API Error: internal server error, " + serverUrl, 0, httpStatusCode);
		}
		else	{
			httpClient.getConnectionManager().shutdown();
			System.out.println("HTTP status code: " + httpStatusCode);
			throw new APIException("API Error: " + serverUrl, 0, httpStatusCode);
		}
	}
}
	
