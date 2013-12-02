package com.quizzingbricks.activities.gameboard;

import java.util.ArrayList;

import org.json.JSONObject;

import com.quizzingbricks.R;
import com.quizzingbricks.R.drawable;
import com.quizzingbricks.activities.menu.MenuActivity;
import com.quizzingbricks.communication.apiObjects.asyncTasks.GamesThreadedAPI;
import com.quizzingbricks.communication.apiObjects.asyncTasks.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path.FillType;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class GameBoardActivity extends Activity implements OnTaskCompleteAsync{
//	GameBoardView mview;
	int gameId = 1;
	ArrayList<Integer> gameboard;
	OnTaskCompleteAsync ontaskcomplete;
	int BOARD_SIZE = 8;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ontaskcomplete = this;
        gameboard = new ArrayList<Integer>();
//        setContentView(R.layout.multiscroll_layout);
        
        ScrollView a = new ScrollView(this);
        a.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        HorizontalScrollView b = new HorizontalScrollView(this);
        b.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        LinearLayout c = new LinearLayout(this);
        c.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        c.setOrientation(LinearLayout.VERTICAL);
        
        for (int i = 0; i < BOARD_SIZE; i++) {
        	LinearLayout d = new LinearLayout(this);
            d.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            d.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < BOARD_SIZE; j++) {
            	ImageButton ib = new ImageButton(this);
            	ib.setImageResource(R.drawable.boardcellempty);
            	ib.setId((i*BOARD_SIZE)+j);
            	ib.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
//						GamesThreadedAPI GTAPI = new GamesThreadedAPI(getApplicationContext());
//						GTAPI.getQuestion(gameId, ontaskcomplete);
						int id = v.getId();
						Intent i = new Intent(getApplicationContext(), QuestionPromptActivity.class);
//						System.out.println("loltest"+id);
				    	i.putExtra("id", id);
//						startActivity(i);
				    	startActivityForResult(i, 1);
				    	
//				    	 ImageButton b = (ImageButton) findViewById(id);
//							b.setImageResource(R.drawable.boardcellblue);

					}
				});
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            	d.addView(ib);//new Button(this));
    		}
            c.addView(d);
		}
        b.addView(c);
        a.addView(b);
        setContentView(a);
		}
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		Intent i = new Intent(getApplicationContext(), QuestionPromptActivity.class);
//    	i.putExtra("id", position);
    	startActivity(i);
    	
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){      
		         int result=data.getExtras().getInt("result");
		         ImageButton b = (ImageButton) findViewById(result);
					b.setImageResource(R.drawable.boardcellblue);
		     }
		     if (resultCode == RESULT_CANCELED) {    
		         //Write your code if there's no result
		     }
		  }
		}
}
