package com.quizzingbricks;



import com.quizzingbricks.R;
import com.quizzingbricks.R.id;
import com.quizzingbricks.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
 
public class QuestionPromptActivity extends Activity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionprompt);
 
        // get intent data
        Intent i = getIntent();
 
        // Selected image id
        int position = i.getExtras().getInt("id");
//        ImageAdapter imageAdapter = new ImageAdapter(this);
// 
//        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
//        imageView.setImageResource(imageAdapter.mThumbIds[position]);
    }
 
}