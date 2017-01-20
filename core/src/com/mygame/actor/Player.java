package com.mygame.actor;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygame.handlers.Animation;
import com.mygame.handlers.BulletManager;
import com.mygame.statics.Constants;
import com.mygame.statics.Direction;
import com.mygame.statics.State;
import com.mygame.utils.Node2;

public class Player extends Actor {

	private Timer timer;
	public final float velocity = 8f;
	private Rectangle destination;
	public Vector2 oldPosition;
	private float width, height;
	private Direction direction;
	private State state;
	private Rectangle rectangle;
	protected ArrayList<Node2> path;
	private Vector2 playerCenter;
	public boolean atGoal = false;
	public Rectangle rectArray;
	public BulletManager bulletManager;

	public Player(TextureAtlas texture2, String regionName) {
		super(texture2, regionName);

		animate = new Animation(texture2, new Vector2(2, 4), regionName);
		width = texture2.findRegion(regionName).getRegionWidth();
		height = texture2.findRegion(regionName).getRegionHeight();
		init();
	}

	public Player(TextureAtlas texture2, int size, String regionName) {
		super(texture2, regionName);

		animate = new Animation(texture2, size, regionName);
		width = texture2.findRegion(regionName).getRegionWidth();
		height = texture2.findRegion(regionName).getRegionHeight();
		init();
	}

	public Player(TextureRegion[] playerImage) {
		super(playerImage);
		animate = new Animation(playerImage, 0.010f);
		width = playerImage[0].getRegionWidth();
		height = playerImage[0].getRegionHeight();
		init();
	}

	public void setBulletManager(BulletManager bm) {
		bulletManager = bm;
	}

	public void init() {

		atGoal = false;
		actorPosition = new Vector2(Constants.tileSize * 2, Constants.tileSize);
		oldPosition = new Vector2(Constants.tileSize * 2, Constants.tileSize);
		direction = Direction.North;
		state = State.Idle;
		Constants.playerState = state;
		rectangle = new Rectangle(actorPosition.x, actorPosition.y,
				Constants.tileSize, Constants.tileSize);
		rectArray = new Rectangle(actorPosition.x, actorPosition.y,
				Constants.tileSize, Constants.tileSize);
		destination = new Rectangle(1, 1, Constants.tileSize,
				Constants.tileSize);
		timer = new Timer();
		playerCenter = new Vector2();
	}

	@Override
	public void update() {
		super.update();
		animate.deltaTime = Gdx.graphics.getDeltaTime();
		// animate.update();
		rectangle.setPosition(actorPosition);
		rectArray.setPosition(actorPosition.x / Constants.tileSize,
				actorPosition.y / Constants.tileSize);

		switch (direction) {
		case North:
			if (Constants.playerState == State.moving) {
				animate.setFrame(0, 0);
			} else
				animate.setFrame(0, 1);
			break;
		case East:
			if (Constants.playerState == State.moving) {
				animate.setFrame(2, 0);
			} else
				animate.setFrame(2, 1);
			break;
		case South:
			if (Constants.playerState == State.moving) {
				animate.setFrame(1, 0);
			} else
				animate.setFrame(1, 1);
			break;
		case West:
			if (Constants.playerState == State.moving) {
				animate.setFrame(3, 0);
			} else
				animate.setFrame(3, 1);
			break;
		default:
			break;
		}
	}

	public Rectangle getTilePos() {
		return rectangle;
	}
	public Rectangle getCollRec() {
		return new Rectangle(getPosition().x, getPosition().y, width, height);
	}

	public void movePlayer(ArrayList<Node2> result) {
		// System.out.println("player path");
		path = result;
		if (Constants.playerState != State.ATTACK) {
			timer.scheduleTask(new myTask(), 0.2f, 0.023f);
			timer.start();
		} else if (Constants.playerState == State.ATTACK) {
			// System.out.println("player creating bullet");
			setPosition(path.get(1).getPosition());
			if (Constants.Ammo > 0)
				bulletManager.addBullet(path, rectangle.x, rectangle.y);
		}

	}

	@Override
	public void setPosition(Vector2 position) {
		oldPosition = actorPosition;
		// actorPosition = position;
		System.out.println(position);
		if (oldPosition.x < position.x && oldPosition.y == position.y) {
			// east
			setDirection(Direction.East);

		}

		if (oldPosition.x > position.x && oldPosition.y == position.y) {

			setDirection(Direction.West);

		}
		if (oldPosition.y < position.y && oldPosition.x == position.x) {

			setDirection(Direction.North);

		}
		if (oldPosition.y > position.y && oldPosition.x == position.x) {

			setDirection(Direction.South);

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

	public void step(Node2 next) {

		switch (next.direction) {
		case North:
			setDirection(Direction.North);
			actorPosition.add(0, velocity);
			break;
		case East:
			setDirection(Direction.East);
			actorPosition.add(velocity, 0);
			break;
		case South:
			setDirection(Direction.South);
			actorPosition.sub(0, velocity);
			break;
		case West:
			setDirection(Direction.West);
			actorPosition.sub(velocity, 0);
			break;
		default:
			break;

		}
	}

	public Vector2 center() {
		playerCenter
				.set(getPosition().x + Center.x, getPosition().y + Center.y/*
																			 * +68f
																			 */);
		return playerCenter;
	}

	// set player to center position of tile
	public Vector2 getPosition() {
		return new Vector2(actorPosition.x, actorPosition.y + (height * 0.25f));

	}

	public void setDestination(Vector2 des) {
		if (Constants.playerState == State.Idle) {
			// destination = new Rectangle(des.x,des.y,96,96);
			destination.setPosition(des);
			// System.out.println("destin: "+destination);
		}

	}

	public Rectangle getDes() {
		return destination;
	}

	public Rectangle getRect() {
		return rectangle;
	}

	public void setDirection(Direction dir) {
		direction = dir;
	}

	public void setState(State state) {
		this.state = state;
		Constants.playerState = state;
	}

	public State getState() {
		return Constants.playerState;
	}

	@Override
	public void draw(SpriteBatch batch) {
		// super.draw(batch);
		if (Alive && Visible) {
			batch.setColor(Color.WHITE);
			batch.draw(animate.getFrame(), getPosition().x, getPosition().y,
					Center.x, Center.y, width, height, 1.0f, 1.0f, Rotate);
		}
	}

	public class myTask extends Task {

		@Override
		public void run() {

			if (!path.isEmpty()) {

				step(path.get(0));
				//moveTo(path.get(0).getPosition());
				if (actorPosition.equals(path.get(0).getPosition())) {
					// System.out.println(path.get(0).direction);
					path.remove(0);
				}

			} else {
				
				this.cancel();
				timer.stop();
				
				timer.scheduleTask(new Task(){
					@Override
					public void run() {
						setState(State.Idle);						
					}
					
				}, 0.2f);
				timer.start();

			}

		}

	}
	

}
