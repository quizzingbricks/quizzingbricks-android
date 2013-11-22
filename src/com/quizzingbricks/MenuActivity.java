package com.quizzingbricks;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
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
					Intent intent = new Intent(this, GridViewActivity.class);
			    	startActivity(intent);
				} else if (position == 1){
					Intent intent = new Intent(this, MultiScrollView.class);
			    	startActivity(intent);
				} else if (position == 2){
					Intent intent = new Intent(this, HorizontalViewActivity.class);
			    	startActivity(intent);
				} else if (position == 3){
					Intent intent = new Intent(this, MultiScrollActivity.class);
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