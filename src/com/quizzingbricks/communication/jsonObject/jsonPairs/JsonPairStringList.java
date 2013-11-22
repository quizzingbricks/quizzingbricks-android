package com.quizzingbricks.communication.jsonObject.jsonPairs;

import java.util.List;


public class JsonPairStringList extends JsonPair {

	private String[] values;
	private List<String> stringList;

	
	public JsonPairStringList(String variableName, String[] value) {
		super(variableName, value);
		this.values = value;
	}
	
	public JsonPairStringList(String variableName, List<String> value) {
		super(variableName, value);
		this.stringList = value;
	}

	@Override
	public String toJsonString() {
		String valueString = "\"" + this.getVariableName() + "\" : [ ";
		if(values != null)	{
			for(int i=0; i<this.values.length; i++)	{
				valueString += "\"" + values[i] + "\"";
				if(i != this.values.length-1)	{
					valueString += ", ";
				}
			}
		}
		else	{
			for(int i=0; i<this.stringList.size(); i++)	{
				valueString += "\"" + stringList.get(i) + "\"";
				if(i != this.stringList.size()-1)	{
					valueString += ", ";
				}
			}
		}
		valueString += "]";
		return valueString;
	}

}

