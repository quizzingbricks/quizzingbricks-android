package com.quizzingbricks;

import android.graphics.*;

import java.util.LinkedList;
import java.util.Iterator;

public class GameTiles {
	private Bitmap[][] tiles;
	private LinkedList<Bitmap> sprites;
	private Bitmap player;
	public GameTiles(int width, int height){
		tiles = new Bitmap[width][height];
		sprites = new LinkedList<Bitmap>();
	}
	public Bitmap getTile(int x,int y){
		if (x<0||x>=getWidth()||
			y<0||y>=getHeight()){
			return null;
		}
		else {
			return tiles[x][y];
		}
	}
	public void fillBoard(Bitmap bmp){
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				tiles[i][j]=bmp;
			}
			
		}
	}
	public void setTile(int x, int y,Bitmap tile){
		tiles[x][y]=tile;
	}
	public Bitmap getPlayer(){
		return player;
	}
	public void setPlayer(Bitmap player){
		this.player = player;
	}
	public void addSprite(Bitmap sprite){
		sprites.add(sprite);
	}
	public void removeSprite(Bitmap sprite){
		sprites.remove(sprite);
	}
	public Iterator<Bitmap> getSprites(){
		return sprites.iterator();
	}
	public int getWidth(){
		return tiles.length;
	}
	public int getHeight(){
		return tiles[0].length;
	}
	
}
