package com.mygame.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygame.handlers.Animation;
import com.mygame.statics.Constants;

public class Item extends Actor {
	
	public int type = 0;
	public Rectangle rect;
	
	public Item(TextureAtlas texture2, String regionName, int size,  float x, float y) {		
		super(texture2, regionName);
		animate = new Animation(texture2, size, regionName);
		animate.setDelay(0.06f);
		
		setPosition(x+(Constants.tileSize/2)-width1/2,y+(Constants.tileSize/2)-height1/2);
		rect = new Rectangle(x,y,width1,height1);
	}
	
	public Item (TextureRegion[] playerImage) {
		super(playerImage);
		animate = new Animation(playerImage, 0.1f);			
		//rect = new Rectangle(x,y,width1,height1);
	}
	public Item (TextureRegion[] playerImage , float x, float y) {
		super(playerImage);
		animate = new Animation(playerImage, 0.1f);
		setPosition(x+(Constants.tileSize/2)-width1/2,y+(Constants.tileSize/2));		
		rect = new Rectangle(x,y,width1,height1);
	}
	
	@Override
	public void update(){
		
		animate.deltaTime = Gdx.graphics.getDeltaTime();
		animate.update();
	}
	
	@Override
	public void draw(SpriteBatch batch){
		
		
		if(Alive && Visible){
			batch.setColor(Color.WHITE);
			batch.draw(animate.getFrame(), getActorPosition().x, getActorPosition().y, Center.x, Center.y, 
					width1, height1, 1.0f, 1.0f, Rotate);
		}
		
	}

	@Override
	public void dispose(){
		
	}

}
