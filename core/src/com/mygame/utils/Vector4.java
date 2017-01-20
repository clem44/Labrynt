package com.mygame.utils;

import com.badlogic.gdx.math.Vector2;

public class Vector4 extends Vector2 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	float x;
	float y;
	float x2;
	float y2;
	
	public Vector4(float x,float y, float x2 , float y2){
		this.x=x;
		this.y = y;
		this.x2= x2;
		this.y2 = y2;
		
	}
	public Vector4(float cost){
		x =cost;
		y= cost;
		x2 = cost;
		y2=cost;
		
	}
	
	@Override
	public String toString(){
		
		return "vec4:[ "+x+" : "+y+" : "+x2+" : "+ y2+" ]";
	}

}
