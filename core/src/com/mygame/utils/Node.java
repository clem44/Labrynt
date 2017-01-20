package com.mygame.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygame.statics.CheckNode;

public class Node implements Comparable<Node>, Iterable<Node> {

	public Rectangle position;
	protected Vector2 destination;
	protected float cost;	
	Node parent;
	private float distance;
	protected int row, col;
	public List<Node> edge;
	TiledMapTileLayer MapLayers;

	public Node(Rectangle rectangle, float n, Node parent,
			TiledMapTileLayer path, Vector2 destination) {

		position = rectangle;
		this.destination = destination;
		cost = n;
		this.parent = parent;
		edge = new ArrayList<Node>();
		MapLayers = path;
		row = getRow();
		col = getCol();
		calculateDist();
		// heuristics();

	}

	public Node() {
		position = new Rectangle();
		distance = 1;
	}

	public Node(Node next) {

	}

	private void calculateDist() {
		float x = (destination.x / 96) - col;
		setDist(x);
	}

	/**
	 * Sets this Node's distance from Goal row
	 * 
	 */
	public void setDist(float dist) {
		distance = Math.abs(dist);
	}
	public float getValue(){
		return cost+distance;
	}

	public Node getParent() {	
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
		return (int) position.y / 96;
	}

	public int getCol() {
		return (int) position.x / 96;
	}

	public Vector2 getPosition() {
		return new Vector2(position.x, position.y);
	}

	@Override
	public boolean equals(Object o) {
		return ((Node) o).position.equals(position);
	}

	public Vector2 getArrPosition() {
		return new Vector2(col, row);
	}

	public Vector2 getRecPosition() {
		return new Vector2(col * 96, row * 96);
	}

	@Override
	public String toString() {
		return "Path: " + getRecPosition() + " cost: " + getCost() + " dist: "
				+ getDistance();
	}

	@Override
	public int hashCode() {
		return position.hashCode();
	}

	@Override
	public Iterator<Node> iterator() {
		return new NodeIterator(this, cost);
	}

	@Override
	public int compareTo(Node o) {
		return o.cost < cost ? 1:-1;
	}

	/**
	 * More convenient method for calculating the children nodes this method is
	 * called by for each iterator.
	 */
	private class NodeIterator implements Iterator<Node> {
		List<Node> nextList = new LinkedList<Node>();
		Iterator<Node> backer;

		public NodeIterator(Node start, float n) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (i == 0 && j == -1 || i == 0 && j == 1 || i == -1
							&& j == 0 || i == 1 && j == 0) {
						boolean move = CheckNode.checkMove(new Vector2(
								(col + i), (row + j)), MapLayers, start,destination);
						if (move) {
							Rectangle plyer = new Rectangle(((col + i) * 96),
									((row + j) * 96), 96, 96);
							//System.out.println("node passed: [" + plyer.x + ":"+ plyer.y + "]");
							Node node = new Node(plyer, cost + 1, start,
									MapLayers, destination);
							//System.out.println("parent "+ node.getParent().getPosition());
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
		public Node next() {
			return backer.next();
		}

		@Override
		public void remove() {
		}
	}

}
