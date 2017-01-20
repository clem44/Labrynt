package com.mygame.utils;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.badlogic.gdx.math.Rectangle;
import com.mygame.statics.Direction;

public class PathFinder {

	PriorityQueue<Node2> toVisit;
	private Node2 playerNode;
	ArrayList<Node2> seen;
	//HashSet<Node2> history = new HashSet<Node2>();

	public PathFinder(Rectangle player, Square[][] map, Node2 start,
			Vector4 destination) {

		playerNode = new Node2(player, 0, new Node2(),  map, destination);

		toVisit = new PriorityQueue<Node2>();
		toVisit.add(playerNode);
		seen = new ArrayList<Node2>();
//		System.out.println("pathfinder created");
//		System.out.println("player pos: "+ player);
	}
	public PathFinder() {

		//playerNode = new Node2();

		toVisit = new PriorityQueue<Node2>();
		seen = new ArrayList<Node2>();
		
	}
	
	public ArrayList<Node2> findPath(Rectangle player, Square[][] map, Node2 start,
			Vector4 destination, int actor ){
		
		playerNode = new Node2(player, 0, new Node2(), map, destination);	
		playerNode.setActor(actor);
		toVisit.add(playerNode);
		
		ArrayList<Node2> mypath = calculatePath(toVisit);
		return mypath;
	}
	
	public ArrayList<Node2> findPath(){
		//System.out.println("starting path finder");
		ArrayList<Node2> mypath = calculatePath(toVisit);
		return mypath;
	}

	private ArrayList<Node2> calculatePath(PriorityQueue<Node2> toVisit) {

		seen.clear();// Save all those that have
													// been visited
		while (!toVisit.isEmpty()) {
			Node2 curr = toVisit.poll();
			
			for (Node2 next : curr) {

				if (!seen.contains(next)) {
					seen.add(next);
					setChildDirection(next);
					//System.out.println(next.getParent().getArrPosition()+": current note"+next.getArrPosition());				
					toVisit.add(next);
				}
			}

		}
		return seen;

	}
	private void setChildDirection(Node2 start){		
		
		if(start.getParent().getArrPosition().x> start.getArrPosition().x){
			start.direction = Direction.West;
		}
		if(start.getParent().getArrPosition().x< start.getArrPosition().x){
			start.direction= Direction.East;
		}
		if(start.getParent().getArrPosition().y> start.getArrPosition().y){
			start.direction = Direction.South;
		}
		if(start.getParent().getArrPosition().y<start.getArrPosition().y){
			start.direction = Direction.North;
		}
	}

}
