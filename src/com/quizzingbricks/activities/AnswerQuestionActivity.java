package com.quizzingbricks.activities;

import android.app.ListActivity;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class AnswerQuestionActivity extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fm = getFragmentManager();  
		if (fm.findFragmentById(android.R.id.content) == null) {  
			SimpleListFragment list = new SimpleListFragment();  
			fm.beginTransaction().add(android.R.id.content, list).commit();  
		} 
	}
	
	public class SimpleListFragment extends ListFragment	{
		String[] countries = new String[] {
	        "India",
	        "Pakistan",
	        "Sri Lanka",
	        "China",
	        "Bangladesh",
	        "Nepal",
	        "Afghanistan",
	        "North Korea",
	        "South Korea",
	        "Japan",
	        "Sweden",
	        "Denmark", 
	        "Norway", 
	        "Finland",
	        "Iceland",
	        "Greenland"
	    };
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,countries);
			setListAdapter(adapter);
	        return super.onCreateView(inflater, container, savedInstanceState);
		}
	}
}

