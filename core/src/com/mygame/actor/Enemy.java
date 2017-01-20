package com.mygame.actor;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygame.handlers.Animation;
import com.mygame.statics.Constants;
import com.mygame.statics.Direction;
import com.mygame.utils.Node2;
import com.mygame.utils.Square;

public class Enemy extends Actor {

	public boolean attacking, pathEmpty = false;
	// private Direction direction;
	// private State state;
	private Rectangle rectangle;
	private Rectangle rectCollision;
	//private Rectangle rectArray;
	private Vector2 oldPosition;
	private Square[][] collisionRects;
	private ArrayList<Square> path;
	private ArrayList<Node2> walkingPath;

	int moveWhere;

	public final float velocity = 0.5f;
	

	public Enemy(TextureAtlas texture2, int size, String regionName) {
		super(texture2, regionName);
		animate = new Animation(texture2, size, regionName);
		animate.setDelay(0.01f);
		init();
	}

	public Enemy(TextureRegion[] playerImage) {
		super(playerImage);
		animate = new Animation(playerImage, 0.10f);
		path = new ArrayList<Square>();
		init();
	}

	public Enemy(TextureAtlas atlas, int size, String regionName,
			Square[][] collisionRects2) {
		super(atlas, regionName);
		animate = new Animation(atlas, size, regionName);
		animate.setDelay(0.08f);
		this.collisionRects = collisionRects2;
		System.out.println("enemy created");
		path = new ArrayList<Square>();
		initPathArray();
		init();

	}

	public void setMapArray(Square[][] collisionRects) {
		this.collisionRects = collisionRects;

	}

	public void init() {

		attacking = false;
		// actorPosition = new Vector2(192, 288);
		oldPosition = new Vector2(Constants.tileSize * 2, Constants.tileSize);
		// direction = Direction.North;
		// state = State.Idle;
		rectangle = new Rectangle(actorPosition.x, actorPosition.y,
				Constants.tileSize, Constants.tileSize);
		rectCollision = new Rectangle(actorPosition.x,actorPosition.y*0.25f,rectangle.width,rectangle.height*0.25f);

		/*rectArray = new Rectangle(actorPosition.x / Constants.tileSize,
				actorPosition.y / Constants.tileSize, Constants.tileSize,
				Constants.tileSize);*/

		// System.out.println("enemy initialized");

	}

	public void initPathArray() {

		// System.out.println( Constants.col_Size+" : r "+Constants.row_Size);
		if (collisionRects.length > 0) {
			for (int i = 0; i < Constants.col_Size; i++) {
				for (int j = 0; j < Constants.row_Size; j++) {

					if (collisionRects[i][j].getName().equals("path")) {
						path.add(collisionRects[i][j]);

					}
				}
			}

			moveWhere = MathUtils.random(0, (path.size()-1));
			setPosition(path.get(moveWhere).x, path.get(moveWhere).y);
			// System.out.println(" enemyPos:" + path.get(moveWhere));//
			// path.clear();
			path.clear();
		}

	}

	public void setArrayPath(ArrayList<Node2> array) {

		// System.out.println("walkingpE " + array.size());
		pathEmpty = false;
		walkingPath = array;

	}

	@Override
	public void setPosition(Vector2 position) {

		oldPosition = actorPosition;

		super.setPosition(position);

	}

	public Vector2 getPosition() {
		return new Vector2(actorPosition.x + 5, actorPosition.y
				+ (height1 * 0.05f));

	}
	public Vector2 center(){
		
		 return  new Vector2(getPosition().x+Center.x,getPosition().y+Center.y/*+68f*/);
	}

	@Override
	public void update() {

		super.update();
		animate.deltaTime = Gdx.graphics.getDeltaTime();
		animate.update();
		rectangle.setPosition(actorPosition);
		rectCollision.set(actorPosition.x,actorPosition.y+20,rectangle.width,rectangle.height-20);
	/*	rectArray.setPosition(actorPosition.x / Constants.tileSize,
				actorPosition.y / Constants.tileSize);*/

		if (!walkingPath.isEmpty() && !pathEmpty) {
			
			moveTo(walkingPath.get(0).getPosition());
			if (actorPosition.equals(walkingPath.get(0).getPosition())) {
				walkingPath.remove(0);
			}
		} else {
			//System.out.println("moving stopped:");
			// state = State.Idle;
			pathEmpty = true;
		}

	}

	public void moveTo(Vector2 position) {
		oldPosition = actorPosition;

		// System.out.println("old pos "+oldPosition);

		if (oldPosition.x < position.x && oldPosition.y == position.y) {
			// east
			setDirection(Direction.East);
			actorPosition.add(velocity, 0);
		}

		if (oldPosition.x > position.x && oldPosition.y == position.y) {

			setDirection(Direction.West);
			actorPosition.sub(velocity, 0);

		}
		if (oldPosition.y < position.y && oldPosition.x == position.x) {

			setDirection(Direction.North);
			actorPosition.add(0, velocity);

		}
		if (oldPosition.y > position.y && oldPosition.x == position.x) {

			setDirection(Direction.South);
			actorPosition.sub(0, velocity);
		}
	}

	public void setDirection(Direction dir) {
		// direction = dir;
	}

	@Override
	public void draw(SpriteBatch batch) {
		// super.draw(batch);
		if (Alive && Visible) {
			batch.setColor(Color.WHITE);
			batch.draw(animate.getCurrentFrame(), getPosition().x,
					getPosition().y, width1, height1);
		}
	}

	public Rectangle getTilePos() {

		return rectangle;
	}
	public Rectangle getCollisionRec() {

		return rectCollision;
	}

}
