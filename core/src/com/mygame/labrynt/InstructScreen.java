package com.mygame.labrynt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygame.statics.Resources;

public class InstructScreen implements Screen {

	
	LabryntMain game;
	Stage stage;
	Table table;
	private TextButton backButton;
	private Image instructions0, instructions1, instructions2;
	private TextButton nextButton ;
	private ImageButton menuButton;
	ImageButtonStyle buttonStyle2;
	private TextButtonStyle buttonStyle;
	private Skin buttonSkin;
	private boolean transition = false, reverse = false;
	int click = 1;
	
	
	public InstructScreen(LabryntMain game){
		this.game = game;
	}
	
	@Override
	public void show() {
		stage =  new Stage(new StretchViewport(480,
				768));
		table = new Table();
		table.setTransform(true);
		
		instructions0 = new Image(Resources.instruct0);
		instructions0.setPosition(0, 0);
		instructions1 = new Image(Resources.instruct1);
		instructions1.setPosition(stage.getWidth(), 0);
		instructions2 = new Image(Resources.instruct2);
		instructions2.setPosition(stage.getWidth()*2, 0);
	

		buttonSkin = new Skin(Resources.buttonAtlas);
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("nextbtn0");
		buttonStyle.over = buttonSkin.getDrawable("nextbtn1");
		buttonStyle.down = buttonSkin.getDrawable("nextbtn1");
		buttonStyle.font = Resources.font.get(3);
		buttonStyle.fontColor = Color.WHITE;

		nextButton = new TextButton("", buttonStyle);
		backButton = new TextButton("", buttonStyle);
		backButton.setOrigin(backButton.getPrefWidth()/2,backButton.getPrefHeight()/2);
		backButton.setTransform(true);
		
		buttonStyle2 = new ImageButtonStyle();
		buttonStyle2.up = buttonSkin.getDrawable("menuBtn0");
		buttonStyle2.over = buttonSkin.getDrawable("menuBtn1");
		buttonStyle2.down = buttonSkin.getDrawable("menuBtn1");	
		
		
		menuButton = new ImageButton(buttonStyle2);

		table.bottom();
		table.add(backButton).left().padRight(80);
		table.add(menuButton).center().padRight(80);
		table.add(nextButton).right();		
					
		table.setBounds(0, 0, stage.getWidth(),stage.getHeight());
		backButton.setRotation(180);
		
		stage.addActor(instructions2);
		stage.addActor(instructions1);
		stage.addActor(instructions0);		
		stage.addActor(table);
		stage.getRoot().getColor().a=0;
		stage.getRoot().addAction(Actions.sequence(Actions.delay(0.5f),
				Actions.fadeIn(1.5f)));

		Gdx.input.setInputProcessor(stage);		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
		
		
		if (nextButton.isPressed()) {
			transition = true;			
		}
		
		if (backButton.isPressed()) {
			reverse = true;
		}
		
		if (menuButton.isPressed()) {
			stage.getRoot().addAction(
					Actions.sequence(Actions.delay(0.5f), Actions.fadeOut(1.5f),
							Actions.run(new Runnable() {
								@Override
								public void run() {
									game.setScreen(game.mainMenu);
									// dispose();
								}
							})));
			
		}
		
		if(transition || reverse){			
			screenTransition();			
		}

	}

	private void screenTransition(){
		
		if(transition && click<3){
			if(instructions0.getX() > -(stage.getWidth()*click)){
				instructions0.setPosition(instructions0.getX()-20, 0);
				instructions1.setPosition(instructions1.getX()-20, 0);
				instructions2.setPosition(instructions2.getX()-20, 0);
			}else{
				transition = false;
				click++;
			}
		}
		if(reverse && click>0){
			
			if(instructions0.getX() < -(stage.getWidth()*(click-1))){
				instructions0.setPosition(instructions0.getX()+20, 0);
				instructions1.setPosition(instructions1.getX()+20, 0);
				instructions2.setPosition(instructions2.getX()+20, 0);
			}else{
				reverse = false;
				click--;
			}
		}
		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void dispose() {
		stage.dispose();		
	}

}
