package com.quizzingbricks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class MenuAdapter extends ArrayAdapter<String> {
	private final Context context;
	static final public String[] values = new String[] {  "GridLayout","MultiScrollHARDCODED","HorizontalScroll","MultiScroll" };
//	private final String[] values;
 
	public MenuAdapter(Context context) {
		super(context, R.layout.list_layout, values);		
		this.context = context;
//		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);
 
		// Change icon based on name
		String s = values[position];
 
		System.out.println(s);
 
		if (s.equals(values[0])) {
			imageView.setImageResource(R.drawable.boardcellblue);
		} else if (s.equals(values[1])) {
			imageView.setImageResource(R.drawable.boardcellgreen);
		} else if (s.equals(values[2])) {
			imageView.setImageResource(R.drawable.boardcellred);
		} else if (s.equals(values[3])){
			imageView.setImageResource(R.drawable.boardcellyellow);
		}
 
		return rowView;
	}
}