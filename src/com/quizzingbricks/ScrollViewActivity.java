package com.quizzingbricks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
 
public class ScrollViewActivity extends Activity {
	ImageAdapter ia;
	ScrollView scrollView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_layout);
//        HorizontalScrollView e = new HorizontalScrollView(this);
//        LinearLayout a = (LinearLayout) findViewById(R.id.LinearLayout01);
//        int SIZEOFBOARD = 36;
////        Button b = (Button) findViewById(R.id.scrollbutton);
//        
//        for (int i = 0; i < 8; i++) {
//        	for (int j = 0; j < 8; j++) {
//        		Button c = new Button(this);
//                a.addView(c);
//			}        	
//		}
//        e.addView(a);
        
    }
}