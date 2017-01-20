package com.mygame.inter;

import com.mygame.statics.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameCamera implements Updatable {

	public OrthographicCamera camera;
	public Rectangle rect;
	private Vector2 playerPos;
	private Vector3 center,middle;
	public float zoom;

	public GameCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.viewWidth, Constants.viewHeight);// camera
																			// calculate
																			// the
																			// center
																			// of
																			// window

		rect = new Rectangle();
		
		center = new Vector3();
	}

	public void init(Vector2 actorPosition) {

		playerPos = actorPosition;
	}

	public void setPlayerPos(Vector2 playerPos) {
		this.playerPos = playerPos;
	}

	@Override
	public void update() {

		center.set((MathUtils.round(playerPos.x /*+ 30*/)), 
				(MathUtils.round(playerPos.y /*+ 30*/)), 0);
		middle = new Vector3(center);
		/*		
		 * center = new Vector3((playerPos.x+30), //Prevents tileMap from
		 * bleeding by using integers ( playerPos.y+30),0);
		 */

		if (center.x < 360) {		
			//center.x = 240f;
			middle.x = 240 ;
		}
		else if (center.x >= 360 && center.x < 600) {		
			//center.x = 240f;
			middle.x = 528 ;
		}
		else if (center.x >= 600 && center.x < 840) {		
			//center.x = 240f;
			middle.x = 720;
		}		
		else if (center.x >= 840 && center.x < 1080) {
			middle.x = 1008;
		}
		else if (center.x >= 1080 && center.x < 1344) {
			middle.x = 1152;
		}
		else if (center.x >= 1344 && center.x < 1440) {
			middle.x = 1200;
		}
		
		/////////////////////////////////////
		
		if (center.y < 672) {
			middle.y = Constants.viewHeightHalf;//384
		}
		else if (center.y >= 672 && center.y < 1056) {
			middle.y = Constants.viewHeight;
		}
		else if (center.y >= 1056 && center.y < 1536) {
			middle.y = Constants.viewHeight+384;
		}
	
		else if (center.y >= 1536) {
			middle.y = 1536;
		}
		
		/*if (center.x < 240) {
			center.x = 240f;
		}
		if (center.x > 1200) {
			center.x = 1200f;
		}
		if (center.y >= 1136) {
			center.y = 1136;
		}
		if (center.y < Constants.viewHeightHalf) {
			center.y = Constants.viewHeightHalf;
		}*/
		camera.position.set(middle);// player center	

		rect.set(
				(camera.position.x) - (Constants.viewWidthHalf), // rectangle// set to// 0,0																	
				(camera.position.y) - (Constants.viewHeightHalf),
				Constants.viewWidth, Constants.viewHeight);

		
		camera.update();

	}

}
