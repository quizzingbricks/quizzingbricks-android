package com.quizzingbricks.activities.inactive;


import com.quizzingbricks.R;

import com.quizzingbricks.activities.gameboard.GameBoardActivity;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class MenuActivity extends ListActivity {
	 	MenuAdapter md;
		
	 
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			md = new MenuAdapter(this);
			setListAdapter(md);
			 
			}
		 
			@Override
			protected void onListItemClick(ListView l, View v, int position, long id) {
		 
				//get selected items
				
				if (position == 0){
					Intent intent = new Intent(this, ActiveGamesMenuActivity.class);
			    	startActivity(intent);
				} else if (position == 1){
					Intent intent = new Intent(this, FriendsListActivity.class);
			    	startActivity(intent);
				} else if (position == 2){
//					Intent intent = new Intent(this, LobbyActivity.class);
//			    	startActivity(intent);
				} else if (position == 3){
					Intent intent = new Intent(this, GridViewActivity.class);
			    	startActivity(intent);
				}
				else {
					String selectedValue = (String) getListAdapter().getItem(position);
					Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
				}
		 
			}
			// no more this
			// setContentView(R.layout.list_fruit);
	 
//			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_layout,INNEHÅLL));
//	 
//			ListView listView = getListView();
//			listView.setTextFilterEnabled(true);
//	 
//			listView.setOnItemClickListener(new OnItemClickListener() {
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//				    // When clicked, show a toast with the TextView text
//				    Toast.makeText(getApplicationContext(),
//					((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//				}
//			});
	 
	
}


class MenuAdapter extends ArrayAdapter<String> {
	private final Context context;
	static final public String[] values = new String[] {  "Game List","Friends List","NewGame Test","GridView" };
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