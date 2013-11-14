package communication;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.exceptions.ServerConnectionException;

public class LobbyAPI {
	
	private String serverUrl = "http://130.240.94.184:5000/games/"; 
	private String token = "";
	private HttpClient client = new DefaultHttpClient();
	private HttpClient httpClient = new DefaultHttpClient();
	
	/**
	 * 
	 * @param context, needed to get the token from the shared preferences
	 */
	public LobbyAPI(Context context)	{
		AuthenticationManager authManager = new AuthenticationManager(context);
		this.token = authManager.getToken();
		if(token == null)	{
			authManager.checkAuthentication();
		}
	}
	
	public String[] getGameLobbies() throws ClientProtocolException, IOException, ServerConnectionException	{
		HttpGet httpGet= new HttpGet(serverUrl);
		httpGet.addHeader("X-Auth-Token", token);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if(httpResponse.getStatusLine().getStatusCode() == 200)	{
			
		}
		else	{
			throw new ServerConnectionException("Unknown error");
		}
		return null;
	}
}
