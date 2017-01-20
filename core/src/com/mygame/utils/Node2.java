package com.mygame.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.mygame.statics.Constants;
import com.mygame.statics.Direction;


public class Node2  implements Comparable<Node2>, Iterable<Node2>, Disposable{

	public Rectangle position;
	protected Vector4 destination;
	public Direction direction;
	protected float cost;	
	Node2 parent;
	protected int actor;
	private float distance;
	protected int row, col;	
	protected Square[][]  MapLayers;

	public Node2(Rectangle rectangle, float n, Node2 parent,
			Square[][] map, Vector4 destination2) {

		position = rectangle;
		this.destination = destination2;
		cost = n;
		this.parent = parent;
		direction = Direction.Center;
		MapLayers = map;
		row = getRow();
		col = getCol();
		calculateDist();
		// heuristics();
		actor=1;

	}

	public Node2() {
		position = new Rectangle();
		distance = 1;
	}

	public Node2(Node2 next) {

	}
	
	private void calculateDist() {
		
		setDist(destination.x - col);
	}
	
	public void setActor(int actor){
		this.actor = actor;
	}
	
	public void setDist(float dist) {
		distance = Math.abs(dist);
	}
	public float getValue(){
		return cost+distance;
	}

	public Node2 getParent() {	
		return this.parent;
	}

	public float getCost() {
		return cost;
	}

	/**
	 * Gets node's distance from Goal row
	 * 
	 * @return distance
	 */
	public float getDistance() {
		return distance;
	}


	public int getRow() {
		return (int) ( position.y / Constants.tileSize);
	}

	public int getCol() {
		return (int) ( position.x / Constants.tileSize);
	}

	public Vector2 getPosition() {
		return new Vector2(position.x, position.y);
	}
	
	@Override
	public boolean equals(Object o) {
		return ((Node2) o).position.equals(position);
	}

	public Vector2 getArrPosition() {
		return new Vector2(col, row);
	}
	public Rectangle getRec(){
		return new Rectangle (col*96, row*96,96,96);
	}

	public Vector2 getRecPosition() {
		return new Vector2(col * 96, row * 96);
	}

	@Override
	public String toString() {
		return "Node2: " + getRecPosition() + " cost: " + getCost() + " dist: "
				+ getDistance();
	}


	
	@Override
	public int hashCode() {
		return position.hashCode();
	}
	@Override
	public int compareTo(Node2 o) {
		return o.cost < cost ? 1:-1;
	}

	@Override
	public Iterator<Node2> iterator() {
		return new NodeIterator(this, cost);
	}

	private class NodeIterator implements Iterator<Node2> {
		List<Node2> nextList = new LinkedList<Node2>();
		Iterator<Node2> backer;	
		
		public NodeIterator(Node2 start, float n) {			
			
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (i == 0 && j == -1 || i == 0 && j == 1 || i == -1
							&& j == 0 || i == 1 && j == 0) {
						boolean move =  legalHighlighter(new Vector2(
								(col + i), (row + j)), start);
						
						if (move) {
							Rectangle plyer = new Rectangle(((col + i) * Constants.tileSize),
									((row + j) *  Constants.tileSize),  Constants.tileSize,  Constants.tileSize);
//							System.out.println("node passed: [" + plyer.x/96 +
//							":"+ plyer.y/96 + "]");
							Node2 node = new Node2(plyer, cost + 1, start,
									MapLayers, destination);
							node.direction = direction;
							// System.out.println("parent "+
							// node.getParent().getPosition());
							nextList.add(node);
							
						}
					}
				}
			}
			backer = nextList.iterator();
		}

		@Override
		public boolean hasNext() {
			return backer.hasNext();
			
		}

		@Override
		public Node2 next() {
			return backer.next();
		}

		@Override
		public void remove() {
			backer.remove();
		}
	}

	private boolean legalHighlighter(Vector2 p, Node2 start) {

		boolean valid = false;

		int sx = (int) p.x;
		int sy = (int) p.y;
		Vector2 point = new Vector2(p.x *  Constants.tileSize, p.y *  Constants.tileSize);

		try {
			
		
			if (sx < 0 || sx > 14 || sy < 0 || sy > 15) {
				return false;
			}
			
			if (this.getCost() >= destination.x) {
				return false;
			}

			if (point.equals(start.getParent().getPosition())) {
				return false;
			}
			if (actor != Constants.enemy) {
				if (MapLayers[sx][sy].getName().contains("path")
						||MapLayers[sx][sy].getName().contains("blocked")
						||MapLayers[sx][sy].getName().contains("start") 
						||MapLayers[sx][sy].getName().contains("goal")) {
					return true;
				}
			}
			else
				if (MapLayers[sx][sy].getName().contains("path")
						|| MapLayers[sx][sy].getName().contains("blocked")) {
					return true;
				}
			
			
		} catch (NullPointerException ie) {
			System.out.println("null pointer caught " + p);
		}

		return valid;
	}
	
	

	@Override
	public void dispose() {
		parent.dispose();

	}

	
}
