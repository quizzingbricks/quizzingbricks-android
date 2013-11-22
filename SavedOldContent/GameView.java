package com.quizzingbricks;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;



public class GameView extends View {//implements SurfaceHolder.Callback{
    private Bitmap bmp;
    private Bitmap background;
//    private SurfaceHolder holder;
//    private GameLoopThread gameLoopThread;
    private int x;
    private int xprev;

    public GameView(Context context) {
          super(context);
          bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pink);
          background = BitmapFactory.decodeResource(getResources(), R.drawable.boardsmall60x60);
          
          x=0;xprev=0;
//          gameLoopThread = new GameLoopThread(this);
          
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
    	 super.onTouchEvent(event);
    	 event.getX();
    	 event.getY();
    	 return true;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    	drawBackground(canvas);
    	if (x < getWidth() - bmp.getWidth()){
        	xprev=x;
    		
        } else {
        	xprev=x;
        	x=0;
        }
    	canvas.drawBitmap(background, xprev, 0, null);
    	canvas.drawBitmap(bmp, x, 0, null);
    	x=+60;
    }
    
    private void drawBackground(Canvas canvas){
        for (int i = 0; i < 15; i++) {
        	for (int j = 0; j < 9; j++) {
        		canvas.drawBitmap(background, i*60, j*60, null);
    		} 
		}  
	}
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width,
//  			int height) {
//  		// TODO Auto-generated method stub
//  		}
//    @Override
//      	public void surfaceCreated(SurfaceHolder holder) {
//      		Canvas c = holder.lockCanvas(null);
//      		onDraw(c);//drawBackground(c);
//      		holder.unlockCanvasAndPost(c);
//      		// TODO Auto-generated method stub	
//      	}
//    @Override
//      	public void surfaceDestroyed(SurfaceHolder holder) {
//      		// TODO Auto-generated method stub	
//      	}
}
//holder = getHolder();
//holder.addCallback(new SurfaceHolder.Callback(){
//  	public void surfaceChanged(SurfaceHolder holder, int format, int width,
//			int height) {
//		// TODO Auto-generated method stub
//		}
//    	public void surfaceCreated(SurfaceHolder holder) {
////    		gameLoopThread.setRunning(true);
////    		gameLoopThread.start();
//    		Canvas c = holder.lockCanvas(null);
//    		drawBackground(c);//onDraw(c);//
//    		holder.unlockCanvasAndPost(c);
//    		// TODO Auto-generated method stub	
//    	}
//	
//    	public void surfaceDestroyed(SurfaceHolder holder) {
////    		boolean retry = true;
////    		gameLoopThread.setRunning(false);
////    		while (retry){
////    			try {
////    				gameLoopThread.join();
////    				retry=false;
////    			} catch (InterruptedException e){
////    				
////    			}
////    		}
//    		// TODO Auto-generated method stub	
//    	}
//});