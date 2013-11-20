package com.quizzingbricks.communication.jsonObject;


import java.util.LinkedList;

import com.quizzingbricks.communication.jsonObject.jsonPairs.JsonPair;
import com.quizzingbricks.communication.jsonObject.jsonPairs.JsonPairList;

public class SimpleJsonObject implements JsonObject {

	LinkedList<JsonObject> jsonPairList = new LinkedList<JsonObject>();

	public void addJsonField(JsonPair jsonObject)	{
		jsonPairList.add(jsonObject);
	}
	
	public void addJsonField(JsonPairList jsonObject)	{
		jsonPairList.add(jsonObject);
	}
	
	public String toJsonString()	{
		String returnString = "{ \n";
		LinkedList<JsonObject> localJsonPairList = jsonPairList; 
		
		while(localJsonPairList.size() > 1)	{
			returnString += jsonPairList.poll().toJsonString() + ", \n";
		}
		returnString += jsonPairList.poll().toJsonString() + "\n";
		returnString += "}";
		return returnString;
	}
}


