package com.mygame.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.mygame.statics.Constants;

public class TileLabel extends Label {

	Rectangle rectangle;
	public int value;
	int tileHalf = (int) (Constants.tileSize*0.45f);
	
	public TileLabel(CharSequence text, LabelStyle style) {
		super(text, style);
		
	}
	public TileLabel(CharSequence text,int value, LabelStyle style) {
		super(text, style);
		this.value = value;
		
	}
	
	public TileLabel (CharSequence text, Skin skin, String styleName) {
		super(text, skin.get(styleName, LabelStyle.class));
	}
	
	
	public Rectangle getRec(){
		return rectangle;
	}
	public void setRectangle(Rectangle rectangle2){
		rectangle = rectangle2; 
		//System.out.println(rectangle);
		this.setSize(rectangle.width, rectangle.height);
		//this.setFontScale(1.2f);
		this.setPosition(rectangle.x, rectangle.y);
		this.setAlignment(Align.center);
	}
	
	public Vector2 getLocation(){
		return new Vector2(rectangle.x+20, rectangle.y+20);
	}
	

	
}
