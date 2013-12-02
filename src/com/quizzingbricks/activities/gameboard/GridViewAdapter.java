package com.quizzingbricks.activities.gameboard;



import com.quizzingbricks.R;
import com.quizzingbricks.R.drawable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    public Integer[] mThumbIds;
    // Keep all Images in array
//     = {
//            R.drawable.boardcellempty, R.drawable.boardcellempty,
//            R.drawable.boardcellempty, R.drawable.boardcellempty,
//            R.drawable.boardcellempty, 
//    };
 
    // Constructor
    public GridViewAdapter(Context c, int size){
        mContext = c;
        mThumbIds = new Integer[size];
        for (int i = 0; i < size; i++) {
			mThumbIds[i]= R.drawable.boardcellempty;
		}
    }
 
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);        
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        
        //Size of the picture
//        imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
        return imageView;
//    	ImageButton imageButton = new ImageButton(mContext);
//    	imageButton.setImageResource(mThumbIds[position]);//(R.drawable.boardcellempty);
//    	imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
//    	imageButton.setLayoutParams(new GridView.LayoutParams(70, 70));
//    	imageButton.onClick
//    	return imageButton;
    }
 
}