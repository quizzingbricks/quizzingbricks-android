package com.quizzingbricks.communication.jsonObject;



public class JsonPairString extends JsonPair {

	public JsonPairString(String variableName, String value) {
		super(variableName, value);
	}

	@Override
	public String toJsonString() {
		return "\"" + this.getVariableName() + "\" : \"" + this.getValue() + "\"";
	}

}
