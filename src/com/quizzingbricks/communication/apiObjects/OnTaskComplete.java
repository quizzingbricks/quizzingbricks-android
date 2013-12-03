package com.quizzingbricks.communication.apiObjects;

public interface OnTaskComplete<ResultType> {
	
	public abstract void onComplete(ResultType result);
}
