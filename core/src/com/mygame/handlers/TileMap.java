package com.mygame.handlers;

import java.util.ArrayList;
import java.util.Collections;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Disposable;
import com.mygame.actor.Player;
import com.mygame.inter.*;

import com.mygame.statics.Constants;
import com.mygame.statics.Resources;
import com.mygame.statics.State;
import com.mygame.utils.Node2;
import com.mygame.utils.PathFinder;
import com.mygame.utils.Square;
import com.mygame.utils.TileLabel;
import com.mygame.utils.Vector4;

public class TileMap implements Drawable, Updatable, Disposable {

	public OrthogonalTiledMapRenderer render;
	private boolean ranOnce = false;
	public boolean enableTileLabel = false;
	private LabelStyle style;

	private TiledMapTileLayer mapLayer0, mapLayer1, mapLayer2, mapLayer3,mapLayer4;
	private TiledMap[] map;
	private ArrayList<TileLabel> tileLabel;
	private Player player;
	private int index = 0;
	int tileWidth = 96;
	int tileHeight = 96;
	private Square[][] collisionRects;
	private Vector2 tilePosition;
	private ArrayList<Node2> walkingArray;
	private Node2 myPath;
	private int moveCost = 2;
	private Color tileGreen = new Color(0, 230, 0, 0.3f);
	private Color tileRed = new Color(230, 0, 0, 0.3f);

	private ItemManager itemManager;
	ShapeRenderer shape;
	PathFinder finder;

	public TileMap(TiledMap map, Player player) {

		// this.map = map;
		mapLayer1 = (TiledMapTileLayer) map.getLayers().get("map1");
		this.player = player;
		itemManager = new ItemManager(player /* ,Resources.itemAtlas */);

		initTiles();
		finder = new PathFinder();
		tilePosition = new Vector2();
		myPath = new Node2();
		render = new OrthogonalTiledMapRenderer(map);
		shape = new ShapeRenderer();
		// shape.setColor(tileGreen);
	}

	public TileMap(TiledMap[] map, Player player, LabelStyle style2) {

		this.map = map;
		this.player = player;
		itemManager = new ItemManager(player, Resources.itemAtlas);
		tileLabel = new ArrayList<TileLabel>();
		style = style2;
		finder = new PathFinder();
		tilePosition = new Vector2();

		myPath = new Node2();
		render = new OrthogonalTiledMapRenderer(map[index]);
		shape = new ShapeRenderer();
		// shape.setColor(tileGreen);

		initTiles();
	}

	public void initVar() {
		ranOnce = false;
	}

	public void setLabelStyle(LabelStyle style) {
		style = new LabelStyle(style);

	}

	public Square[][] getPath() {

		return this.collisionRects;
	}

	public void initTiles() {
		// System.out.println("initTiles");
		walkingArray = new ArrayList<Node2>();

		mapLayer0 = (TiledMapTileLayer) map[index].getLayers().get("floor");
		mapLayer1 = (TiledMapTileLayer) map[index].getLayers().get("path");
		mapLayer2 = (TiledMapTileLayer) map[index].getLayers().get("blocked");
		mapLayer3 = (TiledMapTileLayer) map[index].getLayers().get("goal");
		mapLayer4 = (TiledMapTileLayer) map[index].getLayers().get("start");

		// Creates 2D array of squares and gives each one a name
		collisionRects = new Square[mapLayer0.getWidth()][mapLayer0.getHeight()];

		for (int i = 0; i < mapLayer0.getWidth(); i++) {
			for (int j = 0; j < mapLayer0.getHeight(); j++) {

				collisionRects[i][j] = new Square(i * tileWidth,
						j * tileHeight, tileWidth, tileHeight, "", 0);

				if (mapLayer0.getCell(i, j) != null)
					collisionRects[i][j].setName("floor");

				if (mapLayer1.getCell(i, j) != null)
					collisionRects[i][j].setName("path");
				
				if (mapLayer4.getCell(i, j) != null)
					collisionRects[i][j].setName("start");

				if (mapLayer2.getCell(i, j) != null) {
					collisionRects[i][j].setName("blocked");
					// System.out.println(count++);
					itemManager.addBlocks(i * Constants.tileSize, j
							* Constants.tileSize);
				}

				if (mapLayer3.getCell(i, j) != null)
					collisionRects[i][j].setName("goal");
			}
		}
		/*
		 * for (int i = 0; i < mapLayer1.getWidth(); i++) { for (int j = 0; j <
		 * mapLayer1.getHeight(); j++) {
		 * 
		 * if(collisionRects[i][j].getName.equals("blocked")){
		 * itemManager.addBlocks(i*Constants.tileSize,j*Constants.tileSize); } }
		 */

	}

	public void setMap(int index) {

		try {
			this.index = index;
			render.setMap(map[index]);
			itemManager.init();
			initTiles();

		} catch (ArrayIndexOutOfBoundsException ie) {
			System.out.println("Map index out of bounds");
		} catch (NullPointerException e) {
			System.out.println("Maplayer might be null");
		}
	}

	@Override
	public void dispose() {
		itemManager.dispose();
		render.dispose();
		shape.dispose();
	}

	@Override
	public void update() {

		if (player.getState() != State.moving && !ranOnce) {
			pathHighlighter();
		}

		if (collisionRects[(int) player.rectArray.x][(int) player.rectArray.y]
				.getName().contains("goal")) {
			player.atGoal = true;
		}
		itemManager.update();
		moveCost = itemManager.moveCost;
	}

	public void pathHighlighter() {
		walkingArray.clear();
		tileLabel.clear();
		/*
		 * Vector4 destination = new
		 * Vector4((player.getTilePos().x/96)+2,(player.getTilePos().y/96)+2,
		 * (player.getTilePos().x/96)-2,(player.getTilePos().y/96)-2);
		 */
		Vector4 destination = new Vector4(moveCost);
		// System.out.println(destination);

		walkingArray = finder.findPath(player.getTilePos(), collisionRects,
				new Node2(), destination,Constants.player);
		ranOnce = true;
		int num = 0;

		for (Node2 r : walkingArray) {
			num++;
			TileLabel t = new TileLabel("" + num,num, style);
			t.setRectangle(r.getRec());
			tileLabel.add(t);
		}

	}

	public boolean isLegalMove(Vector2 point) {

		if (player.getState() != State.moving && ranOnce) {
			
			for (Node2 node : walkingArray) {

				if (node.getRec().contains(point)) {
					// if (!isMoving) {
					try {

						tilePosition = new Vector2(
								collisionRects[node.getCol()][node.getRow()].x,
								collisionRects[node.getCol()][node.getRow()].y);

						player.setDestination(getTileCenter());
						myPath = node;
						ranOnce = false;
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println(node.getArrPosition());
					}

					return true;
					// }
				}
			}
		}
		return false;
	}

	public boolean isLegalMove(int point) {

		if (player.getState() != State.moving) {

			for (TileLabel tile : tileLabel) {
				
					if(tile.value == point){
						 return isLegalMove(tile.getLocation());
					}

				
			}
		}
		return false;
	}
	
	public float getX() {
		return player.getActorPosition().x;
	}

	public float getY() {
		return player.getActorPosition().y;
	}

	public Vector2 getTileCenter() {
		return tilePosition;
	}

	/***
	 * checking all adjacent tiles for collisions
	 */
	public String findPath() {

		if (player.getState() == State.Idle) {

			player.setState(State.moving);
			//Constants.playerState = State.moving;
			movePlayer(findPathTaken(myPath));
			return "moving";
		}
		if (player.getState() == State.ATTACK) {
			if(Constants.Ammo>0){
				movePlayer(findPathTaken(myPath));
			}
			else{
				return "no Ammo";
			}
		}
		return "";
	}

	private ArrayList<Node2> findPathTaken(Node2 myPath2) {

		ArrayList<Node2> result = new ArrayList<Node2>();

		Vector2 current = myPath2.getRecPosition();
		Node2 curNode = myPath;
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

	public void movePlayer(ArrayList<Node2> result) {
		player.movePlayer(result);
	}

	@Override
	public void draw(SpriteBatch batch) {

		render.render();

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		shape.setProjectionMatrix(batch.getProjectionMatrix());
		shape.begin(ShapeType.Filled);

		// draw highlighted tiles
		if (player.getState() == State.ATTACK) {
			shape.setColor(tileRed);
		} else {
			shape.setColor(tileGreen);
		}
		if (!walkingArray.isEmpty() && ranOnce) {
			for (Node2 r : walkingArray) {
				shape.rect(r.getRecPosition().x, r.getRecPosition().y, Constants.tileSize, Constants.tileSize);
			}
		}
		shape.end();
		Gdx.gl20.glDisable(GL20.GL_BLEND);

		// Draw tile labels

		batch.begin();
		if (enableTileLabel) {
			for (int i = 0; i < tileLabel.size(); i++) {
				tileLabel.get(i).draw(batch, 1f);
			}
		}
		itemManager.draw(batch);
		batch.end();

	}

}
