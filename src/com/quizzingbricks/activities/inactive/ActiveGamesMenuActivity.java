//package com.quizzingbricks.activities.inactive;
//
//
//import java.util.ArrayList;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import com.quizzingbricks.R;
//
//import com.quizzingbricks.activities.gameboard.GameBoardActivity;
//import com.quizzingbricks.communication.apiObjects.GamesThreadedAPI;
//import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
//import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
//import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
//import com.quizzingbricks.tools.AsyncTaskResult;
//
//
//import android.app.ListActivity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
// 
//public class ActiveGamesMenuActivity extends ListActivity implements OnTaskCompleteAsync{
//	ActiveGamesMenuAdapter md;
//	ArrayList<String> list;
//		
//	 
//		@Override
//		public void onCreate(Bundle savedInstanceState) {
//			super.onCreate(savedInstanceState);
////			String[] list = new String[] {"Game1","Game2"};
//			list = new ArrayList<String>();
//			list.add("Waiting for games");
//			md = new ActiveGamesMenuAdapter(this, list);
//			setListAdapter(md);
//			UserThreadedAPI lt = new UserThreadedAPI(this);
//			lt.getActiveGamesList(this);
//			}
//		 
//			@Override
//			protected void onListItemClick(ListView l, View v, int position, long id) {
//		 
//				//get selected items
//				
//					
////					v.toString()
//					Intent intent = new Intent(this, GameBoardActivity.class);
//					intent.putExtra("id", Integer.parseInt(list.get(position)));
//			    	startActivity(intent);
//				
//		 
//			}
//			// no more this
//			// setContentView(R.layout.list_fruit);
//
//			@Override
//			public void onComplete(AsyncTaskResult<JSONObject> result) {
//				// TODO Auto-generated method stub
////				list = new ArrayList<String>();
//				try {
//					JSONObject asd = result.getResult().getJSONObject("games");
////					for (int i = 0; i < asd.length(); i++) {
////						JSONObject test = asd.getJSONObject(i)get(i);
////						list[i]=asd.get(i).toString();
//						list.add(asd.toString());
//						
////					}
//					
//					
//				} catch (Exception e) {
//					// TODO: handle exception
//					list.add("0");
//					e.printStackTrace();
//					list.add("No Games");
//				}
//				FriendAdapter md = new FriendAdapter(this, list);
//				setListAdapter(md);
//			}
//	 
////			setListAdapter(new ArrayAdapter<String>(this, R.layout.list_layout,INNEHÅLL));
////	 
////			ListView listView = getListView();
////			listView.setTextFilterEnabled(true);
////	 
////			listView.setOnItemClickListener(new OnItemClickListener() {
////				public void onItemClick(AdapterView<?> parent, View view,
////						int position, long id) {
////				    // When clicked, show a toast with the TextView text
////				    Toast.makeText(getApplicationContext(),
////					((TextView) view).getText(), Toast.LENGTH_SHORT).show();
////				}
////			});
//}
//class ActiveGamesMenuAdapter extends ArrayAdapter<String> {
//	private final Context context;
//	public ArrayList<String> values;
// 
//	public ActiveGamesMenuAdapter(Context context, ArrayList<String> values) {	
//		super(context, R.layout.list_layout_no_image, values);		
//		this.values = values;
//		this.context = context;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		LayoutInflater inflater = (LayoutInflater) context
//			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.list_layout_no_image, parent, false);
//		TextView textView = (TextView) rowView.findViewById(R.id.label);
//		textView.setText(values.get(position));
//		return rowView;
//	}
//	
//}