package com.mygame.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygame.handlers.Animation;
import com.mygame.handlers.ExplosionManager;
import com.mygame.inter.Drawable;
import com.mygame.inter.Updatable;

public class Actor implements Drawable, Updatable, Disposable {

	protected Vector2 actorPosition;
	protected Vector2 Center;
	protected TextureAtlas sprite;

	public TextureRegion[] playerImage;
	private AtlasRegion player;
	private float scaleX = 1f, scaleY = 1f;
	protected Texture texture;
	public Animation animate;
	public float height1 = 0, width1 = 0;
	public ExplosionManager explosionManager;

	public float Health;
	public boolean Alive, Visible;
	public float Speed = 5f;
	protected float Rotate = 0;

	public Actor(TextureRegion[] playerImage) {
		setPlayerImage(playerImage);
		actorPosition = new Vector2(0, 0);
		Alive = true;
		Visible = true;
		Health = 100f;
		width1 = playerImage[0].getRegionWidth();
		height1 = playerImage[0].getRegionHeight();
		Center = getCenter();

	}

	public Actor(AtlasRegion findRegion) {
		player = findRegion;
		actorPosition = new Vector2();
		Alive = true;
		Visible = true;
		Health = 100f;
		width1 = player.getRegionWidth();
		height1 = player.getRegionHeight();
		Center = regionCenter();
	}

	public Actor(TextureAtlas texture2, String regionName) {
		sprite = texture2;
		actorPosition = new Vector2();
		Alive = true;
		Visible = true;
		Health = 100;

		try {
			width1 = sprite.findRegion(regionName, 1).getRegionWidth();
			height1 = sprite.findRegion(regionName, 1).getRegionHeight();
			Center = atlasCenter();
		} catch (NullPointerException ie) {
			System.out.println("null pointer at actor class");
		}
	}

	public void setPlayerImage(TextureRegion[] playerImage) {
		this.playerImage = playerImage;
	}

	public Vector2 getCenter() {
		Vector2 center = new Vector2(width1 * 0.5f, height1 * 0.5f);
		return center;
	}

	public Vector2 regionCenter() {

		Vector2 center = new Vector2(width1 * 0.5f, height1 * 0.5f);
		return center;
	}

	public Vector2 atlasCenter() {

		Vector2 center = new Vector2(width1 * 0.5f, height1 * 0.5f);

		return center;
	}
	
	public void setExplosionManager(ExplosionManager ex){
		explosionManager = ex;
	}

	public void setScale(float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	public Vector2 getActorPosition() {
		return actorPosition;
	}

	public void setPosition(Vector2 position) {
		// System.out.println(actorPosition);
		actorPosition = position;
		// System.out.println(actorPosition);
	}

	public void setPosition(float x, float y) {
		actorPosition.set(x, y);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {

	}

	@Override
	public void draw(SpriteBatch batch) {

		if (Alive && Visible) {
			/*
			 * batch.draw(playerImage[0], actorPosition.x, actorPosition.y,
			 * Center.x, Center.y, playerImage[0].getRegionWidth(),
			 * playerImage[0].getRegionHeight(), 1.0f, 1.0f, Rotate);
			 */

			batch.setColor(Color.WHITE);
			batch.draw(player, actorPosition.x, actorPosition.y, Center.x,
					Center.y, player.getRegionWidth(),
					player.getRegionHeight(), scaleX, scaleY, Rotate);
		}

	}

}
