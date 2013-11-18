package com.quizzingbricks.communication.jsonObject;


public class JsonPairStringList extends JsonPair {

	private String[] values;

	
	public JsonPairStringList(String variableName, String[] value) {
		super(variableName, value);
		this.values = value;
	}

	@Override
	public String toJsonString() {
		String valueString = "\"" + this.getVariableName() + "\" : [ ";
		for(int i=0; i<this.values.length; i++)	{
			valueString += "\"" + values[i] + "\"";
			if(i != this.values.length-1)	{
				valueString += ", ";
			}
		}
		valueString += "]";
		return valueString;
	}

}

