package com.quizzingbricks;

//import com.example.killall.GameView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class Gameboard extends Activity {//implements OnClickListener{
//	private GameView view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		ImageButton button = new ImageButton(null);
//		RelativeLayout gameBoard= new RelativeLayout(getApplicationContext());
//		button.setImageResource(R.drawable.black);
//		button.setOnClickListener(buttonOnClickListener);
//		button.setBackgroundColor(Color.TRANSPARENT);
//		button.setTag(1);
//		button.setId(1);
//		view = new GameView(this);
//        setContentView(view);
//		setContentView(R.layout.activity_game_board);
//		Button but = (Button) findViewById(R.id.login_submit);
//		but.setTag(1);
//		but.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Button bat = (Button) v;
//                Log.i("button", "Clicked button "+bat.getTag());
//            }
//        });
//		Button but = new Button(R.id.my_button);
//		GridLayout gl = new GridLayout(this);
//		but.
		setContentView(R.layout.grid_layout);
		 
//        GridView gridView = (GridView) findViewById(R.id.grid_view);
// 
//        // Instance of ImageAdapter Class
//        gridView.setAdapter(new ImageAdapter(this));
		
        
	}
	public void doonclick(){
		Log.i("test", "testing is done");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_board, menu);
		return true;
	}
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//
//		    //check which green ball was clicked
//		        int selected_item = (Integer) v.getTag();
//
//		Log.i("greeny","Clicked on green ball->"+selected_item);
//		}
//	};
}
