package com.quizzingbricks.activities.gameboard;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.quizzingbricks.R;
import com.quizzingbricks.R.drawable;
import com.quizzingbricks.activities.answerQuestion.QuestionPromptActivity;

import com.quizzingbricks.communication.apiObjects.GamesThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
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
	int gameID;
	ArrayList<Integer> gameboard;
	OnTaskCompleteAsync ontaskcomplete;
	int BOARD_SIZE = 8;
	GamesThreadedAPI gameThreadedAPI;
	int y_coord, x_coord;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentBuffer = getIntent();
        gameID = intentBuffer.getExtras().getInt("id");
        ontaskcomplete = this;
        gameboard = new ArrayList<Integer>();
        gameThreadedAPI = new GamesThreadedAPI(this);
        gameThreadedAPI.getGameInfo(gameID, this);
	}    
	
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
    	System.out.println("GOT ON COMPLETE MESSAGE");
    	int[] gameboards = null;
    	ArrayList<Integer> gameborder = new ArrayList<Integer>();
    	ArrayList<Integer> playerlist = new ArrayList<Integer>();
    	try {
    		if(result.hasException())	{
				System.out.println("Oh noes...");
				result.getException().printStackTrace();
    		
			//THIS IS PLACE BRICK
    		} else if (result.getResult().has("result")) {
    			System.out.println("IN PLACE BRICK");
    			Intent i = new Intent(getApplicationContext(), QuestionPromptActivity.class);
		    	i.putExtra("gameID", gameID);
		    	startActivityForResult(i, 1);
		    	
		    //THIS IS GET GAMEBOARD
			} else if (result.getResult().has("players")){
    			JSONObject bigJson = result.getResult();
    			JSONArray playerArray = bigJson.getJSONArray("players");
    			System.out.println(playerArray);
//    			Object gameid = bigJson.get("gameid");
    			JSONArray board = bigJson.getJSONArray("board");
//    			System.out.println(gameid);
    			System.out.println(board);
    			for (int i = 0; i < board.length(); i++) {
					gameborder.add(Integer.parseInt(board.get(i).toString()));
//					System.out.println(board.get(i).toString());
				}
//    			gameboards = (int[]) board.get(index);
    			for (int i = 0; i < playerArray.length(); i++) {
    				Object playerid = playerArray.getJSONObject(i).get("userId");
					Object playeranswerbool = playerArray.getJSONObject(i).get("answeredCorrectly");
					playerlist.add(Integer.parseInt(playerid.toString()));
					System.out.println("player "+playerid+" answered"+playeranswerbool);
				}
    			makeNewAndImprovedGameBoard(gameborder, playerlist);
    		} else if (result.getResult().has("errors")) {
				System.out.println(result.getResult().getJSONArray("errors").get(0).toString());
				Toast.makeText(this, "Move not allowed, you have already answered question", 2).show();
			}
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			//IF ELSE FAIL JUST MAKE EMPTY GAMEBOARD
			LinearLayout asd = new LinearLayout(this);
			Button panicbutton = new Button(this);
			panicbutton.setText("PANIC BUTTON");
			panicbutton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {System.out.println("PANIC");}});
			asd.addView(panicbutton);
			setContentView(asd);
			System.out.println("Exception in onComplete, PANIC: make empty gameboard");
//			makeGameBoard();
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		 if (requestCode == 1) {
		     if(resultCode == RESULT_OK){
		    	 GamesThreadedAPI gamesThreadedAPI = new GamesThreadedAPI(this);
		    	 gameThreadedAPI.getGameInfo(gameID, this);
		     }
		     else if (resultCode == RESULT_CANCELED) {    
		         //Write your code if there's no result
		     }
//		 }
	}
	
	void makeNewAndImprovedGameBoard(ArrayList<Integer> gameboards, ArrayList<Integer> playerlist){
		ScrollView a = new ScrollView(this);
        a.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        HorizontalScrollView b = new HorizontalScrollView(this);
        b.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        LinearLayout c = new LinearLayout(this);
        c.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        c.setOrientation(LinearLayout.VERTICAL);
        
        
        //Extra text for player
        LinearLayout gameInfo = new LinearLayout(this);
        gameInfo.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        gameInfo.setOrientation(LinearLayout.HORIZONTAL);
        TextView playerinfo = new TextView(this);
        playerinfo.append(playerlist.get(0).toString()+"= green, ");
        playerinfo.append(playerlist.get(1).toString()+"= blue, ");
        gameInfo.addView(playerinfo);
		c.addView(gameInfo);
        
        for (int i = 0; i < BOARD_SIZE; i++) {
        	LinearLayout d = new LinearLayout(this);
            d.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//            d.setPadding(-5, 0, -5, 0);
            d.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < BOARD_SIZE; j++) {
            	ImageButton ib = new ImageButton(this);
//            	
            	
            	ib.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            	ib.setId((i*BOARD_SIZE)+j);
            	ib.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						GamesThreadedAPI GTAPI = new GamesThreadedAPI(getApplicationContext());
						
						int id = v.getId();
						x_coord = id / BOARD_SIZE;
						y_coord = id % BOARD_SIZE;
						System.out.println(gameID);
						System.out.println(x_coord);
						System.out.println(y_coord);
						
						GTAPI.placeBricks(gameID, x_coord, y_coord, ontaskcomplete);
//						Intent i = new Intent(getApplicationContext(), QuestionPromptActivity.class);
////						System.out.println("loltest"+id);
//				    	i.putExtra("gameID", gameID);
////						startActivity(i);
//				    	startActivityForResult(i, 1);
				    	
//				    	 ImageButton b = (ImageButton) findViewById(id);
//							b.setImageResource(R.drawable.boardcellblue);

					}
				});
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            	System.out.println((i*BOARD_SIZE)+j);
            	
//            	int tileistakenby = gameboards;
            	int is_owned_by = gameboards.get((i*BOARD_SIZE)+j);
            	if (playerlist.size()==2) {
            		if (is_owned_by == playerlist.get(0)) {
                		ib.setImageResource(R.drawable.boardcellblue);
    				}else if (is_owned_by==playerlist.get(1)) {
    					ib.setImageResource(R.drawable.boardcellgreen);
    				}else {
    					ib.setImageResource(R.drawable.boardcellempty);
    				}
				}  
    			else if (playerlist.size()==4) {
					if (is_owned_by == playerlist.get(0)) {
	            		ib.setImageResource(R.drawable.boardcellblue);
					}else if (is_owned_by==playerlist.get(1)) {
						ib.setImageResource(R.drawable.boardcellgreen);
					}else if (is_owned_by==playerlist.get(2)) {
						ib.setImageResource(R.drawable.boardcellyellow);
					}else if (is_owned_by==playerlist.get(3)) {
						ib.setImageResource(R.drawable.boardcellred);
					}else {
						ib.setImageResource(R.drawable.boardcellempty);
					}
				} 
            	d.addView(ib);//new Button(this));
    		}
            c.addView(d);
		}
        b.addView(c);
        a.addView(b);
        setContentView(a);
	}
	
	
    void makeGameBoard(){   
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
//            d.setPadding(-5, 0, -5, 0);
            d.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < BOARD_SIZE; j++) {
            	ImageButton ib = new ImageButton(this);
            	ib.setImageResource(R.drawable.boardcellempty);
            	ib.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            	ib.setId((i*BOARD_SIZE)+j);
            	ib.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						GamesThreadedAPI GTAPI = new GamesThreadedAPI(getApplicationContext());
						
						int id = v.getId();
						int y = id % BOARD_SIZE;
						int x = id / BOARD_SIZE;
						System.out.println(gameID);
						System.out.println(x);
						System.out.println(y);
						
						GTAPI.placeBricks(gameID, x, y, ontaskcomplete);
						Intent i = new Intent(getApplicationContext(), QuestionPromptActivity.class);
//						System.out.println("loltest"+id);
				    	i.putExtra("gameID", gameID);
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
}
