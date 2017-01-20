package com.mygame.statics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Constants {
	
	public static Preferences prefs;

	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	public static final int IDLE = 0;
	public static final int ACTIVE = 1;

	public static float SCALE_1 = 1f;
	
	public static State  playerState;
	public static float tileSize;
	public static float col_Size;
	public static float row_Size;

	public static float deviceWidth;
	public static float deviceHeight;
	public static float viewWidth;
	public static float viewHeight;
	public static float viewWidthHalf;
	public static float viewHeightHalf;
	public static float viewWidthQuart;
	public static float viewHeightQuart;
	public static float WaveCount;
	public static int highestLevel;
	public static int currentLevel;
	public static int Ammo;

	public static final int enemy = 0;
	public static final int player =1;
	private int w, h;

	public static final String powerBlockDialog = "These are called 'power blocks'. They allow you "
			+ "to move one extra square. The more you collect the furthur you go";

	public static final String weaponBlockDialog = "These are called 'weapon blocks'. They enable your "
			+ "weapons temporary by adding ammo.";

	public Constants() {
		
		prefs = Gdx.app.getPreferences("LBSettings");
		
		Ammo = 0;
		currentLevel = 0;
		switch (Gdx.app.getType()) {
		case Android:

			deviceWidth = w = Gdx.graphics.getWidth();
			deviceHeight = Gdx.graphics.getHeight();
			w = 480;
			h = 768;
			tileSize = 96;
			col_Size = 15;
			row_Size = 16;
			
			break;
			
		case Desktop:
			w = 480;
			tileSize = 96;
			deviceHeight = h = 768;
			col_Size = 15;
			row_Size = 16;
			System.out.println("desktop");
			break;
			
		case WebGL:
			break;
		// / HTML5 specific code
		default:
			break;
		}
		// Gdx.input.setCatchBackKey(true);
		// Gdx.input.setCatchMenuKey(false);
		prefs.putInteger("Highscore", 0);
		prefs.putInteger("powerBlock", 0);
		prefs.putInteger("weaponBlock", 0);
		

		highestLevel = prefs.getInteger("Highscore");
		viewWidth = prefs.getFloat("Width", w);
		viewHeight = prefs.getFloat("Height", h);
		viewWidthHalf = viewWidth * 0.5f;
		viewHeightHalf = viewHeight * 0.5f;
		viewWidthQuart = viewWidthHalf * 0.5f;
		viewHeightQuart = viewHeightHalf * 0.5f;

		if (w == 480) {

		}
		if (w == 1080) {

		}
	}
}
