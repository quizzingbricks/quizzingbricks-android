package com.quizzingbricks.communication.jsonObject;


public class JsonPairList extends JsonPair {

	private SimpleJsonObject[] values;
	
	public JsonPairList(String variableName, SimpleJsonObject[] simpleJsonObject) {
		super(variableName, simpleJsonObject);
		this.values = simpleJsonObject;
	}

	@Override
	public String toJsonString() {
		String returnString = "\"" + this.getVariableName() + "\" : [";
		for(int i=0; i<values.length; i++)	{
			returnString += values[i].toJsonString();
			if(i != values.length-1)	{
				returnString += ", ";
			}
		}
		returnString += "]";
		return returnString;
	}
	
}
