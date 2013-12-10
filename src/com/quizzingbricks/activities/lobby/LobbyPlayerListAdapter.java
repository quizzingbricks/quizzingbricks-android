package com.quizzingbricks.activities.lobby;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.quizzingbricks.R;

public class LobbyPlayerListAdapter extends ArrayAdapter<String> {

	private Context context;
	private ArrayList<String> values;
	
	public LobbyPlayerListAdapter(Context context, ArrayList<String> values) {
		super(context, R.layout.list_layout_no_image, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_layout_no_image, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(values.get(position));
		return rowView;
	}
	
}
