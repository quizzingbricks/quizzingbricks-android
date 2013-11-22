package com.quizzingbricks.communication.apiObjects.asyncTasks;

public interface OnTaskComplete<ResultType> {
	
	public abstract void onComplete(ResultType result);
}
