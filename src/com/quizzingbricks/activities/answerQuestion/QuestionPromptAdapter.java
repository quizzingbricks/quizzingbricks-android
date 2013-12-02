package com.quizzingbricks.activities.answerQuestion;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.quizzingbricks.R;

public class QuestionPromptAdapter extends ArrayAdapter<String> {
	private final Context context;
	private ArrayList<String> values;
	private String question;
 
	public QuestionPromptAdapter(Context context, ArrayList<String> values, String question) {	
		super(context, R.layout.list_layout_no_image, values);
		this.values = values;
		this.context = context;
		this.question = question;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_layout_no_image, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(values.get(position));
		if(position == 0)	{
		}
		return rowView;
	}
}
