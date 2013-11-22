package com.quizzingbricks;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameBoardActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bitmap bitmap = draw3();
//		ImageButton button = new ImageButton(null); 
		ImageView imageView = new ImageView(this);
		imageView.setImageBitmap(bitmap);
		
		    
        
        
		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
		android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		layout.addView(imageView, params);
		
		layout.setBackgroundColor(Color.WHITE);

		setContentView(layout);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_board, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	private Bitmap draw3(){
		//requestWindowFeature(featureId)
		Bitmap bitmap = Bitmap.createBitmap(490, 740, Config.RGB_565);
		GameTiles board = new GameTiles(9,6);
		Canvas canvas = new Canvas(bitmap);
		
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		int size = 80;
		board.fillBoard(bmp);
		for (int i = 0; i < board.getWidth(); i++) {
//			//width
			for (int k=0; k < board.getHeight();k++) {
				canvas.drawBitmap(board.getTile(i, k), i*size, k*size, null);
			}
		}
//		int size = 80;
//		int leftedge = 2;
//		int rightedge = 78;
//		//height
//		int enumer = 1;
		
//				//left, top, right, bot
//				canvas.drawPicture(R.drawable.ic_launcher);
//				canvas.drawRect(k*size+leftedge, i*size+leftedge, k*size+rightedge, i*size+rightedge, paint);
//				canvas.drawText(Integer.toString(enumer) ,k*size+rightedge, i*size+rightedge, paint1);
//				enumer++;
//			}
//		}
		
		return bitmap;
	}
	
	
	void draw2(){
		Bitmap bitmap = Bitmap.createBitmap(490, 740, Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		Paint paint1 = new Paint();
		paint1.setColor(Color.WHITE);
		int size = 80;
		int leftedge = 2;
		int rightedge = 78;
		//height
		int enumer = 1;
//		int[][] grid = new int[9][6];
		for (int i = 0; i < 9; i++) {
			//width
			for (int k = 0; k < 6; k++) {
				//left, top, right, bot
				
				canvas.drawRect(k*size+leftedge, i*size+leftedge, k*size+rightedge, i*size+rightedge, paint);
				canvas.drawText(Integer.toString(enumer) ,k*size+rightedge, i*size+rightedge, paint1);
				enumer++;
			}
		}
		
		
		ImageView imageView = new ImageView(this);
		imageView.setImageBitmap(bitmap);
		
		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
		android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		layout.addView(imageView, params);
		layout.setBackgroundColor(Color.WHITE);

		setContentView(layout);
	}
	
	
	void draw1(){
	
		// We'll be creating an image that is 100 pixels wide and 200 pixels tall.
		int width = 100;
		int height = 200;
		 
		// Create a bitmap with the dimensions we defined above, and with a 16-bit pixel format. We'll
		// get a little more in depth with pixel formats in a later post.
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
		 
		// Create a paint object for us to draw with, and set our drawing color to blue.
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		 
		// Create a new canvas to draw on, and link it to the bitmap that we created above. Any drawing
		// operations performed on the canvas will have an immediate effect on the pixel data of the
		// bitmap.
		Canvas canvas = new Canvas(bitmap);
		 
		// Fill the entire canvas with a red color.
		canvas.drawColor(Color.RED);
		 
		// Draw a rectangle inside our image using the paint object we defined above. The rectangle's
		// upper left corner will be at (25,50), and the lower left corner will be at (75,150). Since we set
		// the paint object's color above, this rectangle will be blue.
		canvas.drawRect(25, 50, 75, 150, paint);
		 
		// In order to display this image in our activity, we need to create a new ImageView that we
		// can display.
		ImageView imageView = new ImageView(this);
		 
		// Set this ImageView's bitmap to the one we have drawn to.
		imageView.setImageBitmap(bitmap);
		 
		// Create a simple layout and add our image view to it.
		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
		android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		layout.addView(imageView, params);
		layout.setBackgroundColor(Color.BLACK);
		 
		// Show this layout in our activity.
		setContentView(layout);
	}

	

}
