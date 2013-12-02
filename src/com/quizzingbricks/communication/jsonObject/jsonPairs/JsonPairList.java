package com.quizzingbricks.communication.jsonObject.jsonPairs;

import java.util.List;

import com.quizzingbricks.communication.jsonObject.SimpleJsonObject;


public class JsonPairList extends JsonPair {

	private SimpleJsonObject[] values;
	private List<SimpleJsonObject> objectList;
	
	public JsonPairList(String variableName, SimpleJsonObject[] simpleJsonObject) {
		super(variableName, simpleJsonObject);
		this.values = simpleJsonObject;
	}
	
	public JsonPairList(String variableName, List<SimpleJsonObject> simpleJsonObjectList) {
		super(variableName, simpleJsonObjectList);
		this.objectList = simpleJsonObjectList;
	}

	@Override
	public String toJsonString() {
		String returnString = "\"" + this.getVariableName() + "\" : [";
		if (values != null)	{
			for(int i=0; i<values.length; i++)	{
				returnString += values[i].toJsonString();
				if(i != values.length-1)	{
					returnString += ", ";
				}
			}
		}
		else	{
			for(int i=0; i<objectList.size(); i++)	{
				returnString += objectList.get(i).toJsonString();
				if(i != objectList.size()-1)	{
					returnString += ", ";
				}
			}
		}
		returnString += "]";
		return returnString;
	}
	
}
