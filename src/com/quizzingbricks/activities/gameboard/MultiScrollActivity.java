package com.quizzingbricks.activities.gameboard;

import com.quizzingbricks.R;
import com.quizzingbricks.R.drawable;

import android.app.Activity;
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

public class MultiScrollActivity extends Activity {
	MultiScrollView mview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.multiscroll_layout);
        
        ScrollView a = new ScrollView(this);
        a.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        HorizontalScrollView b = new HorizontalScrollView(this);
        b.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        LinearLayout c = new LinearLayout(this);
        c.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        c.setOrientation(LinearLayout.VERTICAL);
        
        for (int i = 0; i < 15; i++) {
        	LinearLayout d = new LinearLayout(this);
            d.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            d.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 15; j++) {
            	ImageButton ib = new ImageButton(this);
            	ib.setImageResource(R.drawable.boardcellempty);
            	ib.setId(i+j);
            	ib.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						// TODO Auto-generated method stub
						
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
//        
////        ScrollView e = (ScrollView)findViewById(R.id.scroll_view);
//        
//        Button f = new Button(this);
//        d.addView(f);
//        setContentView(R.layout.multiscroll1_layout);
        
//        setContentView(R.layout.multiscroll1_layout);
        
//        HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.scroll_view);
        
        
//        LinearLayout c = new LinearLayout(this);
//        c.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        Button f = new Button(this);
//        c.addView(f);
//        scrollView.addView(c);
//        setContentView(scrollView);
        
        
//        LinearLayout tv = new LinearLayout(this);
//        Button x = new Button(this);
//        tv.addView(x);
//        mview = new MultiScrollView(this);
//        mview.AddChild(tv);
//        setContentView(mview);
	}
}
