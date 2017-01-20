package com.mygame.actor;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygame.handlers.Animation;
import com.mygame.statics.Constants;
import com.mygame.utils.Node2;

public class Bullet extends Actor{

	
	public Rectangle rectangle;
	public boolean pathEmpty = false;
	private ArrayList<Node2> walkingPath;
	private Vector2 oldPosition;
	
	public Bullet(TextureAtlas texture2, String regionName, int size,  float x, float y) {
		super(texture2, regionName);
		
		animate = new Animation(texture2, size, regionName);
		animate.setDelay(0.01f);
		Speed = 4f ;
		setPosition(x,y);
		rectangle = new Rectangle(x,y,width1,height1);
		oldPosition = new Vector2(Constants.tileSize * 2, Constants.tileSize);
		System.out.print("bullet created "+actorPosition);
	}
	
	
	@Override
	public void update(){
		
		animate.deltaTime = Gdx.graphics.getDeltaTime();
		animate.update();
		
		rectangle.setPosition(actorPosition);
		
		if (!walkingPath.isEmpty() && !pathEmpty) {
			//System.out.println("array"+walkingPath.get(0).getPosition());
			moveTo(walkingPath.get(0).getPosition());
			if (actorPosition.equals(walkingPath.get(0).getPosition())) {
				walkingPath.remove(0);
			}
		} else {
			//System.out.println("moving stopped:");
			// state = State.Idle;
			Visible = false;
			pathEmpty = true;
		}

	}
	
	public void setArrayPath(ArrayList<Node2> array) {

		System.out.println("walkingpE " + array.size());
		pathEmpty = false;
		walkingPath = array;
		walkingPath.remove(0);

	}
	
	@Override
	public void draw(SpriteBatch batch){		
		
		if(Alive && Visible){
			batch.setColor(Color.WHITE);
			batch.draw(animate.getCurrentFrame(), getPosition().x,
					getPosition().y, width1, height1);
		}
		
	}


	private Vector2 getPosition() {
		return new Vector2(actorPosition.x+(Constants.tileSize/2)-width1/2,
				actorPosition.y+(Constants.tileSize/2)-height1/2);
	}
	
	
	public void moveTo(Vector2 position) {
		oldPosition = actorPosition;

		// System.out.println("old pos "+oldPosition);

		if (oldPosition.x < position.x && oldPosition.y == position.y) {
			// east
			//setDirection(Direction.East);
			actorPosition.add(Speed, 0);
		}

		if (oldPosition.x > position.x && oldPosition.y == position.y) {

			//setDirection(Direction.West);
			actorPosition.sub(Speed, 0);

		}
		if (oldPosition.y < position.y && oldPosition.x == position.x) {

			//setDirection(Direction.North);
			actorPosition.add(0, Speed);

		}
		if (oldPosition.y > position.y && oldPosition.x == position.x) {

			//setDirection(Direction.South);
			actorPosition.sub(0, Speed);
		}
	}
	
	public Rectangle getTilePos() {

		return rectangle;
	}


}
