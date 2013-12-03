package com.quizzingbricks.activities.gameboard;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.quizzingbricks.R;
import com.quizzingbricks.R.id;
import com.quizzingbricks.R.layout;


import com.quizzingbricks.communication.apiObjects.asyncTasks.GamesThreadedAPI;
import com.quizzingbricks.communication.apiObjects.asyncTasks.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class QuestionPromptActivity extends ListActivity implements OnTaskCompleteAsync {
 QuestionPromptAdapter md;
 GamesThreadedAPI GTAPI;
 int gameId;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.questionprompt);
//        md = new QuestionPromptAdapter(this, new ArrayString[] {  "Question","Answer 1","Answer 2","Answer 3","Answer 4" });
//		setListAdapter(md);
		gameId = getIntent().getExtras().getInt("id");
		GamesThreadedAPI gt = new GamesThreadedAPI(this);
		
		gt.getQuestion(gameId, this);
        
    }
        // get intent data
//        Intent i = getIntent();
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	
		//get selected items
		
		if (position == 0){
		}
		 else if (position == 1){
			 int lol = getIntent().getExtras().getInt("id");
			 System.out.println(lol);
			 Intent returnIntent = new Intent();
			 returnIntent.putExtra("result",lol);
			 setResult(RESULT_OK,returnIntent);     
			 finish();
//			finishActivity(1);
//			GTAPI.sendAnswer(gameId, 1, this);
		}
		else {
//			String selectedValue = (String) getListAdapter().getItem(position);
//			Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
			Intent returnIntent = new Intent();
//			 returnIntent.putExtra("result",1);
			 setResult(RESULT_CANCELED,returnIntent);     
			 finish();
		}
 
	}
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
//		String x = result.toString();
//		md = new QuestionPromptAdapter(this, new String[] {  "THIS IS QUESTION","Answer 1","Answer 2","Answer 3",x });
//		setListAdapter(md);
		
		ArrayList<String> list = new ArrayList<String>();
		try {
			JSONArray asd = result.getResult().getJSONArray("alternatives");
			list.add(result.getResult().getJSONObject("question").toString());
			for (int i = 0; i < asd.length(); i++) {
	//			JSONObject test = asd.getJSONObject(i)get(i);
	//			list[i]=asd.get(i).toString();
//				list.add(asd.getJSONObject(i).get("name").toString());
				list.add(asd.getJSONObject(i).toString());
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			list.add("Waiting for Question");
		}
		QuestionPromptAdapter md = new QuestionPromptAdapter(this, list);
		setListAdapter(md);
	}
	
}
class QuestionPromptAdapter extends ArrayAdapter<String> {
	private final Context context;
	public ArrayList<String> values;
 
	public QuestionPromptAdapter(Context context, ArrayList<String> values) {	
		super(context, R.layout.list_layout_no_image, values);		
		this.values = values;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_layout_no_image, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(values.get(position));
		return rowView;
	}
}