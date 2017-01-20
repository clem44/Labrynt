package com.mygame.handlers;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygame.actor.Enemy;
import com.mygame.actor.Player;
import com.mygame.inter.Drawable;
import com.mygame.inter.Updatable;
import com.mygame.statics.Constants;
import com.mygame.utils.Node2;
import com.mygame.utils.PathFinder;
import com.mygame.utils.Square;
import com.mygame.utils.Vector4;

public class EnemyManager implements Drawable, Updatable, Disposable {

	TextureAtlas atlas;
	ArrayList<Enemy> enemies;
	private Square[][] collisionRects;
	private PathFinder finder;
	ArrayList<Node2> walkingPath;
	Node2 myPath;
	Vector4 destination;
	int num = 0, num2 = 0;
	Player player;
	public ExplosionManager explosionManager;

	public EnemyManager() {
		enemies = new ArrayList<Enemy>();
		finder = new PathFinder();
		destination = new Vector4(5);
	}

	public EnemyManager(TextureAtlas atlas, Player player) {
		this.atlas = atlas;
		enemies = new ArrayList<Enemy>();
		finder = new PathFinder();
		destination = new Vector4(10);
		walkingPath = new ArrayList<Node2>();
		this.player = player;
	}

	public void init() {
		if (enemies != null || enemies.size() > 0)
			enemies.clear();
		// walkingPath.clear();
	}

	public ArrayList<Enemy> getArray() {
		if (!enemies.isEmpty()) {
			return enemies;
		} else {
			return null;
		}
	}

	public void setExplosionManager(ExplosionManager em) {
		explosionManager = em;
	}

	public void setMapArray(Square[][] collisionRects) {
		this.collisionRects = collisionRects;

	}

	private ArrayList<Node2> findPathTaken(Node2 myPath2) {

		ArrayList<Node2> result = new ArrayList<Node2>();

		Vector2 current = myPath2.getRecPosition();
		Node2 curNode = myPath2;
		Node2 prev = null;
		current = curNode.getParent().getRecPosition();

		while (current.x != 0) {

			current = curNode.getParent().getRecPosition();
			prev = curNode;
			// System.out.println(prev);

			try {

			} catch (NullPointerException ie) {
				System.out.println(ie.getMessage());
				break;
			}
			result.add(prev);
			curNode = curNode.getParent();
		}
		// return prev;
		Collections.reverse(result);
		return result;
	}

	@Override
	public void dispose() {

	}

	public ArrayList<Node2> setupPath(Enemy enemy) {

		walkingPath.clear();
		// System.out.println("enemy rec "+enemy.getTilePos());
		walkingPath = finder.findPath(enemy.getTilePos(), collisionRects,
				new Node2(), destination, Constants.enemy);
		// System.out.println("walkingpath :" + walkingPath.size());
		num = MathUtils.random(1, 4);
		// System.out.println("EM index :" + num);
		num2 = walkingPath.size() - num;
		// System.out.println("em get i :"+num2);
		if(num2>0){
			myPath = walkingPath.get(num2);
		}

		return findPathTaken(myPath);

	}

	@Override
	public void update() {

		// update live enemies
		if (enemies.size() > 0) {
			for (int i = 0; i < enemies.size(); i++) {
				if (enemies.get(i).Alive) {
					checkCollisions(enemies.get(i));
					enemies.get(i).update();
				}
				if (enemies.get(i).pathEmpty) {
					enemies.get(i).setArrayPath(setupPath(enemies.get(i)));
				}
				/*
				 * for (Enemy e : enemies) { checkCollisions(e); if (e.Alive) {
				 * e.update(); } if (e.pathEmpty) e.setArrayPath(setupPath(e));
				 * }
				 */			

			}
			// remove dead enemies
			for (int i = 0; i < enemies.size(); i++) {
				if (!enemies.get(i).Alive) {
					explosionManager.addExplosion(enemies.get(i).center());
					enemies.remove(enemies.get(i));
				}
			}
		}

	}

	public void checkCollisions(Enemy enemy) {
		if (player.getCollRec().overlaps(enemy.getCollisionRec())) {
			player.Alive = false;
		}
	}

	@Override
	public void draw(SpriteBatch batch) {

		if (enemies.size() > 0)
			for (Enemy e : enemies) {
				e.draw(batch);
			}

	}

	public void createEnemy() {

		switch (Constants.currentLevel) {

		case 0:
			for (int n = 0; n < 1; n++) {
				Enemy e = new Enemy(atlas, 4, "enemy", collisionRects);
				e.setArrayPath(setupPath(e));
				enemies.add(e);
			}

			break;
		case 1:
			for (int n = 0; n < 3; n++) {
				Enemy e = new Enemy(atlas, 4, "enemy", collisionRects);
				e.setArrayPath(setupPath(e));
				enemies.add(e);
			}
			break;
		case 2:
			for (int n = 0; n < 2; n++) {
				Enemy e = new Enemy(atlas, 4, "enemy", collisionRects);
				e.setArrayPath(setupPath(e));
				enemies.add(e);
			}

			break;
		case 3:
			for (int n = 0; n < 3; n++) {
				Enemy e = new Enemy(atlas, 4, "enemy", collisionRects);
				e.setArrayPath(setupPath(e));
				enemies.add(e);
			}

			break;
		case 4:
			for (int n = 0; n < 4; n++) {
				Enemy e = new Enemy(atlas, 4, "enemy", collisionRects);
				e.setArrayPath(setupPath(e));
				enemies.add(e);
			}

			break;
		case 5:
			for (int n = 0; n < 5; n++) {
				Enemy e = new Enemy(atlas, 4, "enemy", collisionRects);
				e.setArrayPath(setupPath(e));
				enemies.add(e);
			}

			break;
		default:
			for (int n = 0; n < 8; n++) {
				Enemy e = new Enemy(atlas, 4, "enemy", collisionRects);
				e.setArrayPath(setupPath(e));
				enemies.add(e);
			}

			break;

		}

	}

	/**
	 * Clear all enemies contained in object array
	 */
	public void purge() {
		enemies.clear();
	}

}
