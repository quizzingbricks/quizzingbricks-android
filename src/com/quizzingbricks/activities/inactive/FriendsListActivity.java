package com.quizzingbricks.activities.inactive;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.quizzingbricks.R;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class FriendsListActivity extends ListActivity implements OnTaskCompleteAsync{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		String[] list = new String[] {"Update Friends List"};
//		Adapter md = new Adapter(this, list);
//		setListAdapter(md);
		UserThreadedAPI ut = new UserThreadedAPI(getApplicationContext());
		ut.getFriendsList(this);
		 
		}
	 
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
//			UserThreadedAPI ut = new UserThreadedAPI(getApplicationContext());
//			ut.getFriendsList(this);
			//get selected items
		
			
	 
		}

		@Override
		public void onComplete(AsyncTaskResult<JSONObject> result) {
			
//			result.toArray();
			
//			String[] list = new String[1];
			ArrayList<String> list = new ArrayList<String>();
			try {
				JSONArray asd = result.getResult().getJSONArray("friends");
				for (int i = 0; i < asd.length(); i++) {
//					JSONObject test = asd.getJSONObject(i)get(i);
//					list[i]=asd.get(i).toString();
					list.add(asd.getJSONObject(i).get("name").toString()+", "+asd.getJSONObject(i).get("email").toString());
				
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				list.add("No friends");
			}
			FriendAdapter md = new FriendAdapter(this, list);
			setListAdapter(md);
//			String[] list = new String[]{"TODO:","make it active","result.toArray();)"};
			
		}
}
class FriendAdapter 
extends ArrayAdapter<String> {
	private final Context context;
	public ArrayList<String> values;
//	private final String[] values;

	public FriendAdapter(Context context, ArrayList<String> values) {
		
		super(context, R.layout.list_layout_no_image, values);		
		this.values = values;
		this.context = context;
//		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_layout_no_image, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
//		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values.get(position));

		// Change icon based on name
//		String s = values[position];
// 
//		System.out.println(s);
// 
//		if (s.equals(values[0])) {
//			imageView.setImageResource(R.drawable.boardcellblue);
//		} else if (s.equals(values[1])) {
//			imageView.setImageResource(R.drawable.boardcellgreen);
//		} else if (s.equals(values[2])) {
//			imageView.setImageResource(R.drawable.boardcellred);
//		} else if (s.equals(values[3])){
//			imageView.setImageResource(R.drawable.boardcellyellow);
//		}

		return rowView;
	}

}
