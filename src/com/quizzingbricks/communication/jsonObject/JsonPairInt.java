package com.quizzingbricks.communication.jsonObject;


public class JsonPairInt extends JsonPair {
	
	public JsonPairInt(String variableName, int value) {
		super(variableName, value);
	}

	@Override
	public String toJsonString() {
		return "\"" + this.getVariableName() + "\" : \"" + this.getValue().toString() + "\"";
	}

}
