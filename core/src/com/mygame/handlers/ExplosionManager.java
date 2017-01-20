package com.mygame.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygame.actor.Explosion;
import com.mygame.inter.Drawable;
import com.mygame.inter.Updatable;

public class ExplosionManager implements Drawable, Updatable, Disposable {

	private TextureAtlas atlas;
	private ArrayList<Explosion> explode;
	private String regionName;

	public ExplosionManager(TextureAtlas texture2, String regionName) {
		this.regionName = regionName;
		this.atlas = texture2;
		explode = new ArrayList<Explosion>();
	}

	@Override
	public void dispose() {

	}

	public void addExplosion(Vector2 position) {
		explode.add(new Explosion(atlas, regionName, position));
	}

	@Override
	public void update() {

		if (!explode.isEmpty())
			for (Explosion e : explode) {
				if (e.Alive) {
					e.update();
				}
			}

		
		if (!explode.isEmpty())
			for (int i = 0; i<explode.size(); i++) {
				if (!explode.get(i).Alive)
					explode.remove(explode.get(i));
			}

	}

	@Override
	public void draw(SpriteBatch batch) {
		if (explode.size() > 0)
			for (Explosion e : explode) {
				if (e.Alive)
					e.draw(batch);
			}

	}

}
