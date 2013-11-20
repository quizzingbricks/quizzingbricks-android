package com.quizzingbricks.communication.jsonObject.jsonPairs;


public class JsonPairBoolean extends JsonPair {

	public JsonPairBoolean(String variableName, boolean value) {
		super(variableName, value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toJsonString() {
		return "\"" + this.getVariableName() + "\" : \"" + this.getValue().toString() + "\"";
	}

}
