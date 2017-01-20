package com.mygame.statics;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygame.utils.Node;

/**
 * <h2>Description</h2>
 *<p> This class matches a node against a set of testing conditions. If a node fails a condition then it
 * is not added to the queue for path checking see {@link Node}  & {@link PathFinder} .</p>
 * @author Clemaurde Gumbs
 */
public class CheckNode {

	/**
	 * Checks if the Node about to be created is legal
	 * @param p  Player
	 * @param path  Path Array
	 * @param destination 
	 * @return  Boolean
	 */
	public static boolean checkMove(Vector2 p, TiledMapTileLayer  path, Node start, Vector2 destination) {

		boolean valid = false;	
		
		int sx = (int) p.x;
		int sy = (int) p.y;
		Vector2 point = new Vector2(p.x*96,p.y*96);
		
		//System.out.println("chcking node: "+point);
		//System.out.println("start:"+start.getPosition());
		//System.out.println("checking node:"+p);
		
		if(sx <0 || sx>14 || sy < 0 || sy >15){
			return false;			
		}
		
		/*if(p.equals(destination)){
			return false;
		}*/
		
		if(point.equals(start.getParent().getPosition())){
			return false;			
		}
		
		if (path.getCell(sx, sy).getTile().getProperties()
				.containsKey("path")
				|| path.getCell(sx, sy).getTile()
						.getProperties().containsKey("blocked")){
			return true;
		}
		if (path.getCell(sx, sy).getTile().getProperties()
				.containsKey("goal")
				/*|| path.getCell(sx, sy).getTile()
						.getProperties().containsKey("starting")*/){
			return true;
		}
		
		
		return valid;
}
}
