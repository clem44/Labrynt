package com.mygame.handlers;

import java.util.ArrayList;

import com.mygame.statics.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.mygame.actor.Item;
import com.mygame.actor.Player;
import com.mygame.inter.Drawable;
import com.mygame.inter.Updatable;
import com.mygame.statics.Resources;

public class ItemManager implements Updatable, Drawable, Disposable {

	private ArrayList<Item> items;
	private Player playerPos;
	private Rectangle myPos;
	TextureRegion[] itemImage;
	TextureAtlas atlas;
	public int moveCost = 2;
	public int ammo = 0;

	/*
	 * public int firstItem1 = 0; public int firstItem2 = 0;
	 */

	public ItemManager() {
		items = new ArrayList<Item>();
		itemImage = Resources.itemFrames;
	}

	public ItemManager(Player player) {
		playerPos = player;
		items = new ArrayList<Item>();
		itemImage = Resources.itemFrames;
	}
	public ItemManager(Player player , TextureAtlas atlas) {
		playerPos = player;
		items = new ArrayList<Item>();
		this.atlas = atlas;
	}

	public void init() {
		items.clear();
		moveCost = 2;
	}

	public void setPosition(Rectangle pos) {
		myPos = pos;
	}

	public void setPosition(float x, float y) {
		myPos.setPosition(x, y);
	}

	public void setPlayerPos(Player player) {
		playerPos = player;
	}

	public void addBlocks(float x, float y) {
		// position.add(new Vector2(x,y));		
		
		if (items.size() < 5) {
			
			Item item;
			if(itemImage != null){
				
				item = new Item(itemImage, x, y);
				item.type = MathUtils.random(1, 2);
			}
			else{
				int n = MathUtils.random(1, 2);
				switch(n){
				case 1:
					item = new Item(atlas,"power", 6, x, y);
					item.type = n;
					items.add(item);
					break;
				case 2:
					item = new Item(atlas,"weapon", 6, x, y);
					item.type = n;
					items.add(item);
					break;
				}
				
				
				
			}
		}

	}

	@Override
	public void update() {

		if (items.size() > 0)
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).Alive) {
					items.get(i).update();

					if (playerPos.getTilePos().overlaps(items.get(i).rect)) {
						if(items.get(i).type==1)
							moveCost++;
						if(items.get(i).type==2)
							Constants.Ammo++;
						items.get(i).Alive = false;
						
					}
				}
			}

		for (int i = 0; i < items.size(); i++) {
			if (!items.get(i).Alive) {
				if (items.get(i).type == 1
						&& Constants.prefs.getInteger("powerBlock") == 0) {

					Constants.prefs.putInteger("powerBlock", 1);
					Constants.prefs.flush();

				} else if(items.get(i).type == 2
						&& Constants.prefs.getInteger("weaponBlock") == 0) {
					Constants.prefs.putInteger("weaponBlock", 1);
					Constants.prefs.flush();
				}
				items.remove(i);

			}
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		// batch.begin();
		if (items.size() > 0)
			for (Item item : items) {
				item.draw(batch);
			}
		// batch.end();

	}

	@Override
	public void dispose() {
		items.clear();

	}

}
