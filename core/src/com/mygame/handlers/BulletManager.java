package com.mygame.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.mygame.actor.Bullet;
import com.mygame.inter.Drawable;
import com.mygame.inter.Updatable;
import com.mygame.statics.Constants;
import com.mygame.utils.Node2;

public class BulletManager  implements Updatable, Drawable, Disposable {

	
	private ArrayList<Bullet> bullets;	
	TextureAtlas atlas;
	
	
	public BulletManager(TextureAtlas atlas){
		this.atlas = atlas;
		bullets = new ArrayList<Bullet>();
	}
	
	@Override
	public void dispose() {
				
	}
	
	public void addBullet(ArrayList<Node2> array,float x, float y){
		//System.out.println("addbullet");
		
		Bullet b = new Bullet(atlas,"plasma", 5, x, y);
		b.setArrayPath(array);
		bullets.add(b);
	}
	public ArrayList<Bullet> getArray(){
		if(!bullets.isEmpty()){
			return bullets;
		}
		else{
			return null;
		}
	}

	@Override
	public void update() {
		
		if (!bullets.isEmpty()) {
			
			for (int i = 0; i < bullets.size(); i++) {
				if (bullets.get(i).Visible) {
					bullets.get(i).update();
				}
			}

			for (int i = 0; i < bullets.size(); i++) {
				if (!bullets.get(i).Visible) {
					bullets.remove(bullets.get(i));
					Constants.Ammo--;
				}
			}
		}
		
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if (!bullets.isEmpty())
			for (int i = 0; i < bullets.size(); i++) {
				if (bullets.get(i).Visible) {
					bullets.get(i).draw(batch);
				}
			}

	}

}
