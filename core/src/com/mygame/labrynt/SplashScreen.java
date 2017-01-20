package com.mygame.labrynt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygame.statics.Constants;
import com.mygame.statics.Resources;

public class SplashScreen implements Screen{

	public Constants constants;	
	public Resources resource;	
	float percent;
	Image img;
	Stage stage;
	SequenceAction sequence;
	BitmapFont font;
	Texture splashscreen;
	Label label  ;
	LabelStyle style;
	int transition = 0;
	// Menu menu;
	
	SplashScreen(final LabryntMain game){
		
		splashscreen = new Texture(Gdx.files.internal("SplashScreenV.jpg"));		
		font = new BitmapFont(Gdx.files.internal("fonts/ArialBold.fnt"),false);					
		
		style = new LabelStyle(font, Color.WHITE);
		label = new Label("00.0", style);
		
		
		constants = new Constants();
		
		//this.game = game;
		sequence = new SequenceAction();
		stage =  new Stage(new FitViewport(480,
				800));
		img = new Image(splashscreen);
		//menu = new Menu(game);
				
		sequence.addAction(Actions.delay(1f));
		sequence.addAction(Actions.fadeOut(1.5f));		
		sequence.addAction(Actions.run(new Runnable(){

			@Override
			public void run() {
				
				game.setScreen(game.mainMenu = new Menu(game));
				
			}
			
		}));
		
		
		stage.addActor(img);	
		stage.addActor(label);
		//stage.getRoot().addAction(sequence);
		
		game.resource = new Resources();	
	}
	
	@Override
	public void show() {		
		
	}

	@Override
	public void render(float delta) {	
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		
		// Interpolate the percentage to make it more smooth
			
		percent = Interpolation.linear.apply(percent, Resources.manager.getProgress()*100, 0.1f);
		percent = Math.round(percent);
		
		if(!Resources.manager.update()){
			label.setPosition(stage.getWidth()*0.5f,
					stage.getHeight()*0.25f, Align.center);
			label.setText(percent+"%");
			//System.out.println(percent+"%");			
		}else{			
			transition++;
		}		
        
		
        if(transition==1){
        	Resources.load();
        	stage.getRoot().addAction(sequence);
        }
        
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		img.setSize(stage.getWidth(), stage.getHeight());
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		//stage.dispose();
		
	}

	@Override
	public void dispose() {		
		stage.dispose();
		
	}

}
