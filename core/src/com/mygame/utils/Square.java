package com.mygame.utils;

import com.badlogic.gdx.math.Rectangle;

public class Square extends Rectangle {

	/**
	 * 
	 */
	private String name;
	private int value;
	
	private static final long serialVersionUID = 1L;
	
	public Square(){
		super();
		name = "";
		value = 0;
	}
	
	public Square (float x, float y, float width, float height, String name, int value) {		
		super(x,y,width,height);
		this.name = name;
		this.value = value;
	}
	public Square (float x, float y, float width, float height) {		
		super(x,y,width,height);
		this.name = "";
		this.value = 0;
	}
	
	public Square (Rectangle rect, String name, int value) {
		super(rect);
		this.name = name;
		this.value = value;
		
	}
	
	public Square (Square rect) {
		super(rect);
		name = rect.getName();
		value = rect.getValue();
		
	}

	public int getValue() {		
		return value;
	}

	public String getName() {	
		return name;
	}
	
	public void setValue(int v) {		
		 value = v;
	}

	public void setName(String n) {	
		name = n;
	}

	
	

}
