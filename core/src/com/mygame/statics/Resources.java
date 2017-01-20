package com.mygame.statics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygame.audio.*;

public class Resources implements Disposable {

	public final static AssetManager manager = new AssetManager();

//	private static final int FRAME_ROWS = 1;
//	private static final int FRAME_COLS =6;
	public static TextureAtlas playerAtlas;
	public static TextureAtlas itemAtlas, explosionAtlas;
	public static TextureAtlas buttonAtlas;
	public static Skin skin;	
	public static Array <BitmapFont> font = new Array<BitmapFont>();
	public static TextureRegion myClock;
	public static Array<AtlasRegion> playerSprite;
	public static Texture playerTexture, enemyTexture;	
	public static TiledMap[] map;
	public static Texture background,background2, menuImage, instructScreen /*, splashscreen*/;
	public static Texture instruct0,instruct1,instruct2;
	public static Array<SoundEffect> effects = new Array<SoundEffect>();
	public static MusicManager musicplayer;

	public static Texture itemSheet;
	public static TextureRegion[] itemFrames;
	
	static String screenS = ""+480;
	
	
	
	public Resources(){	
		
		manager.load("foreground.jpg", Texture.class);	
		//manager.load("pausebg.png", Texture.class);	
		manager.load("Labrynt.jpg", Texture.class);
		
		manager.load(screenS +"/instructions0.jpg", Texture.class);
		manager.load(screenS +"/instructions1.jpg", Texture.class);
		manager.load(screenS +"/instructions2.jpg", Texture.class);		
		
		manager.load(screenS +"/itempack.pack", TextureAtlas.class);
		manager.load(screenS +"/buttonpack.pack", TextureAtlas.class);
		manager.load(screenS +"/spritepack.pack", TextureAtlas.class);	
		manager.load(screenS +"/explosionpack.pack", TextureAtlas.class);	
		//manager.load(screenS +"/moveUp.png",Texture.class);
		manager.load("fonts/ArialBlack80.fnt", BitmapFont.class);
		manager.load("fonts/ArialBold.fnt", BitmapFont.class);
		manager.load("fonts/LCD.fnt", BitmapFont.class);
		manager.load("fonts/ArialBlack.fnt", BitmapFont.class);	
		manager.load("fonts/Arial24.fnt", BitmapFont.class);		
		manager.load("audio/Make_It_Bun_Dem.mp3", Music.class);
		
		manager.setLoader(TiledMap.class, new TmxMapLoader(
			      new InternalFileHandleResolver()));		
		for(int i = 1; i <6;i++){
			manager.load(screenS +"/maps/map"+i+".tmx",TiledMap.class);
		}		
		manager.load("skin/uiskin.json", Skin.class, new SkinLoader.SkinParameter("skin/uiskin.atlas"));		
		
	}
	
	public static void load(){	
		
		map = new TiledMap[5];
		
		for(int i = 0; i <5;i++){
			map[i]=manager.get(screenS +"/maps/map"+(i+1)+".tmx",TiledMap.class);
			
		}

		instruct0 = manager.get(screenS +"/instructions0.jpg", Texture.class);
		instruct1 = manager.get(screenS +"/instructions1.jpg", Texture.class);
		instruct2 = manager.get(screenS +"/instructions2.jpg", Texture.class);
		
		background = manager.get("foreground.jpg", Texture.class);
		//background2 = manager.get("pausebg.png", Texture.class);		
		menuImage = manager.get("Labrynt.jpg", Texture.class);
		buttonAtlas = manager.get(screenS +"/buttonpack.pack", TextureAtlas.class);		
		myClock = buttonAtlas.findRegion("panel");
		
		playerAtlas = manager.get(screenS +"/spritepack.pack", TextureAtlas.class);
		itemAtlas = manager.get(screenS +"/itempack.pack", TextureAtlas.class);
		explosionAtlas=manager.get(screenS +"/explosionpack.pack", TextureAtlas.class);	
		skin = manager.get("skin/uiskin.json", Skin.class);			
		font.add( manager.get("fonts/ArialBlack80.fnt", BitmapFont.class));
		font.add(manager.get("fonts/ArialBold.fnt", BitmapFont.class));
		font.add(manager.get("fonts/LCD.fnt", BitmapFont.class));
		font.add(manager.get("fonts/ArialBlack.fnt", BitmapFont.class));
		font.add(manager.get("fonts/Arial24.fnt", BitmapFont.class));	
		
		musicplayer = new MusicManager(manager.get("audio/Make_It_Bun_Dem.mp3", Music.class));
		
		for(int i = 0; i <font.size;i++){
			font.get(i).getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	@Override
	public void dispose() {		
		
		try{
			manager.dispose();
		}
		catch(NullPointerException ie){
			System.out.println("null exception caught");
			
		}
		
	}

}
