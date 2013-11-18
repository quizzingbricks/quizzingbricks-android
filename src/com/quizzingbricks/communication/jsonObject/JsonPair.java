package com.quizzingbricks.communication.jsonObject;


public abstract class JsonPair implements JsonObject {
	private String variableName;
	private Object value;
	
	public JsonPair(String variableName, Object value)		{
		this.variableName = variableName;
		this.value = value;
	}
	
	public String getVariableName()	{
		return variableName;
	}
	
	public Object getValue()	{
		return value;
	}
	
	public abstract String toJsonString();
}
