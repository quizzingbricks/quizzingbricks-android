package com.quizzingbricks;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
 
public class GridViewActivity extends Activity {
	ImageAdapter ia;
	GridView gridView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
 
        gridView = (GridView) findViewById(R.id.grid_view);
        int SIZEOFBOARD = 36;
        // Instance of ImageAdapter Class
        ia = new ImageAdapter(this, SIZEOFBOARD);
        gridView.setAdapter(ia);
//        gridView.setMinimumWidth(1200);
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
            	if (ia.mThumbIds[position]==R.drawable.boardcellgreen){
            		ia.mThumbIds[position] = R.drawable.boardcellempty;
//            		gotomenu();
            	} else {
            		ia.mThumbIds[position] = R.drawable.boardcellgreen;
            	}
            	
            	gridView.setAdapter(ia);
//            	changecolored(position);
            	
            	
                // Sending image id to FullScreenActivity
//                Intent i = new Intent(getApplicationContext(), QuestionPromptActivity.class);
//                // passing array index
//                i.putExtra("id", position);
////                String tempname = getIA();
////                i.getExtra("adapter", tempname);
//                startActivity(i);
            	
            }
        });
        
    }
//    public ImageAdapter getIA(){
//    	return ia;
//    }
    public void changecolored(int position){
    	ia.mThumbIds[position] = R.drawable.boardcellgreen;
    	gridView.setAdapter(ia);
    }
    public void gotomenu(){
    	Intent i = new Intent(getApplicationContext(), MenuActivity.class);
//    	i.putExtra("id", position);
    	startActivity(i);
    }
}









