package com.mygame.handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygame.inter.Updatable;

public class Animation implements Updatable {

	private TextureRegion[] frames;
	public int length;
	private TextureRegion[][] scenes;
	private float time;
	private float delay;
	public float deltaTime = 0.0f;
	private int currentFrame;
	private int timesPlayed;
	private int direction = 0, state = 0;

	public Animation() {

	}

	public Animation(TextureAtlas atlas, Vector2 size, String regionName) {

		scenes = new TextureRegion[(int) size.y][(int) size.x];
		int counter = 0;

		for (int i = 0; i < size.y; i++) {
			for (int j = 0; j < size.x; j++) {
				scenes[i][j] = atlas.findRegion(regionName, counter);
				counter++;
			}
		}

	}

	public Animation(TextureAtlas atlas, int size, String regionName) {
		length = size;
		frames = new TextureRegion[size];
		int counter = 0;
		for (int j = 0; j < size; j++) {
			frames[j] = atlas.findRegion(regionName, counter);
			counter++;
		}
		delay = 0.08f;
		setFrames(frames, delay);
	}

	public Animation(TextureRegion[] frames) {
		this(frames, 1 / 12f); // default delay

	}

	public Animation(TextureRegion[] frames, float delay) {
		setFrames(frames, delay);
		scenes = new TextureRegion[0][0];
	}

	public void setDelay(float delay) {
		this.delay = delay;
	}

	public void setFrames(TextureRegion[] frames, float delay) {
		this.frames = frames;
		this.delay = delay;
		time = 0;
		currentFrame = 0;
		timesPlayed = 0;
	}

	@Override
	public void update() {
		if (delay <= 0)
			return;
		time += deltaTime;
		while (time >= delay) {
			step();
		}

	}

	public void setFrame(int direction, int state) {

		this.direction = direction;
		this.state = state;
	}

	public void setFrame(int index) {
		if (index <= frames.length)
			currentFrame = index;
	}

	private void step() {
		time -= delay;
		currentFrame++;
		timesPlayed++;
		if (currentFrame == frames.length) {
			currentFrame = 0;
			
		}
	}

	public TextureRegion getFrame() {
		if (scenes != null && scenes.length > 1) {
			return scenes[direction][state];
		} else
			return frames[currentFrame];
	}

	public TextureRegion getCurrentFrame() {
		return frames[currentFrame];
	}

	public TextureRegion[] getImage() {
		return frames;
	}

	public int getTimesPlayed() {
		return timesPlayed;
	}
}
