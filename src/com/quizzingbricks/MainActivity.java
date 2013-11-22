//package com.quizzingbricks;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.GridView;
// 
//public class MainActivity extends Activity {
//	ImageAdapter ia;
//	GridView gridView;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.grid_layout);
// 
//        gridView = (GridView) findViewById(R.id.grid_view);
// 
//        // Instance of ImageAdapter Class
//        ia = new ImageAdapter(this);
//        gridView.setAdapter(ia);
//        gridView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v,
//                    int position, long id) {
//            	changecolored(position);
//            	if (position == 1){
//            		gotomenu();
//            	}
//            	
//                // Sending image id to FullScreenActivity
////                Intent i = new Intent(getApplicationContext(), QuestionPromptActivity.class);
////                // passing array index
////                i.putExtra("id", position);
//////                String tempname = getIA();
//////                i.getExtra("adapter", tempname);
////                startActivity(i);
//            	
//            }
//        });
//        
//    }
////    public ImageAdapter getIA(){
////    	return ia;
////    }
//    public void changecolored(int position){
//    	ia.mThumbIds[position] = R.drawable.boardcellgreen;
//    	gridView.setAdapter(ia);
//    }
//    public void gotomenu(){
//    	Intent i = new Intent(getApplicationContext(), MenuActivity.class);
////    	i.putExtra("id", position);
//    	startActivity(i);
//    }
//}
//
//
//
//
//
//
//
//
//
