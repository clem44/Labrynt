package com.mygame.labrynt;

import com.mygame.statics.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Menu implements Screen {

	private Stage stage;
	private Table table;
	private Image menuBackground, board;
	private TextButton playButton, instrucButton, quitButton;
	private TextButtonStyle buttonStyle;
	private Skin buttonSkin;
	Label label;
	private LabelStyle style;
	//private InstructScreen intructScreen;
	
	LabryntMain game;
	private String score;

	public Menu(final LabryntMain game) {
		this.game = game;	
		
		
	}

	@Override
	public void show() {	
		
		stage =  new Stage(new StretchViewport(480,
				800));
		table = new Table();
		
		menuBackground = new Image(Resources.menuImage);
		board = new Image(Resources.buttonAtlas.findRegion("scoreboard"));		

		buttonSkin = new Skin(Resources.buttonAtlas);
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("btn1");
		buttonStyle.over = buttonSkin.getDrawable("btn1");
		buttonStyle.down = buttonSkin.getDrawable("btn1Pressed");
		buttonStyle.font = Resources.font.get(3);
		buttonStyle.fontColor = Color.WHITE;

		playButton = new TextButton("Play", buttonStyle);
		instrucButton = new TextButton("How To", buttonStyle);	
		quitButton = new TextButton ("quit", buttonStyle);

		table.center();
		table.add(playButton).padTop(200).padBottom(30);		
		table.row();
		table.add(instrucButton).padBottom(30);	
		table.row();
		table.add(quitButton).padBottom(100);	
		table.row();
		table.add(board).bottom();	
		table.setBounds(0, 0, stage.getWidth(),stage.getHeight());
		
		score = ""+ Constants.prefs.getInteger("Highscore");
		
		style = new LabelStyle(Resources.font.get(3), Color.WHITE);
		label = new Label(score, style);
		label.setPosition(Constants.viewWidthHalf-68, Constants.viewWidthQuart-60);
		label.setColor(Color.WHITE);
		//label.setFontScale(1.4f);
		
		
		stage.addActor(menuBackground);
		stage.addActor(table);
		stage.addActor(label);
		stage.getRoot().getColor().a=0;
		stage.getRoot().addAction(Actions.sequence(Actions.delay(0.5f),
				Actions.fadeIn(1.5f)));

		Gdx.input.setInputProcessor(stage);
		
		if (!Resources.musicplayer.isPlaying()) {
			Resources.musicplayer.setVolume(0.6f);
			Resources.musicplayer.play();
		}

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

		if (playButton.isPressed()) {
			stage.getRoot().addAction(
					Actions.sequence(Actions.delay(0.5f), Actions.fadeOut(1.5f),
							Actions.run(new Runnable() {

								@Override
								public void run() {
									game.setScreen(game.playScreen = new PlayScreen(game));
									// dispose();
								}
							})));
			
		}
		if (instrucButton.isPressed()) {
			game.setScreen(new InstructScreen(game));
		}
		label.setText(""+Constants.prefs.getInteger("Highscore"));
		
		if (quitButton.isPressed()) {					
			Gdx.app.exit();
			System.exit(0);
		}

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
		
		//float aspectRatio = (float)width/(float)height;
       /* float scale = 1f;
        scale = (float)height/800;
        
        this.playButton.setSize(width/scale, height/scale);
        this.instrucButton.setSize(width/scale, height/scale);*/
        

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
		//game.dispose();
		//this.dispose();

	}

}
