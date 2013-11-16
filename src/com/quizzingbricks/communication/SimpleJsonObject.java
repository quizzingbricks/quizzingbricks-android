package com.quizzingbricks.communication;

import java.util.LinkedList;

public class SimpleJsonObject {
	
	private LinkedList<SimplePair> simplePairList = new LinkedList<SimplePair>();
	
	public void addValuePair(String variable, int value)	{
		this.simplePairList.add(new SimplePair<Integer>(variable, value));
	}
	
	public void addValuePair(String variable, boolean value)	{
		this.simplePairList.add(new SimplePair<Boolean>(variable, value));
	}
	
	public void addValuePair(String variable, String value)	{
		this.simplePairList.add(new SimplePair<String>(variable, value));
	}
	
	private class SimplePair<T>	{
		private String parameter;
		private T value;
		
		public SimplePair(String parameter, T value)		{
			this.parameter = parameter;
			this.value = value;
		}
		
		public String getParameter()	{
			return parameter;
		}
		
		public T getStringValue()	{
			return value;
		}
	}
}


