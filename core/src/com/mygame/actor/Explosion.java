package com.mygame.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.mygame.handlers.Animation;

public class Explosion  extends Actor{

	public Explosion(TextureAtlas texture2, String regionName, Vector2 position) {
		super(texture2, regionName);
		animate = new Animation(texture2,12, regionName);
		animate.setDelay(0.1f);
		actorPosition = position;
		init();
	}
	
	public void init() {
		
		//actorPosition = new Vector2(192, 288);
		System.out.println("explosing "+actorPosition);

	}
	
	public Vector2 getPosition() {
		return new Vector2(actorPosition.x - this.atlasCenter().x, actorPosition.y
				- this.atlasCenter().y);

	}
	
	
	@Override
	public void update() {		
		super.update();
		
		if(animate.getTimesPlayed()!=animate.length){
			//System.out.println("animating");
			animate.deltaTime = Gdx.graphics.getDeltaTime();
			animate.update();
		}else{
			Alive = false;
		}
		
		
		//System.out.println(actorPosition);
		
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if (Alive && Visible) {
			batch.setColor(Color.WHITE);
			batch.draw(animate.getCurrentFrame(), getPosition().x,
					getPosition().y, width1, height1);
		}
	}

}
