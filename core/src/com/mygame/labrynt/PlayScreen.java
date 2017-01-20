package com.mygame.labrynt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygame.actor.Bullet;
import com.mygame.actor.Enemy;
import com.mygame.actor.Player;
import com.mygame.inter.GameCamera;
import com.mygame.inter.Updatable;
import com.mygame.inter.Drawable;
import com.mygame.speech.CommandMatcher;
import com.mygame.speech.SpeechRecognitionResultHandler;
import com.mygame.speech.VoiceRecognitionService;
import com.mygame.statics.*;
import com.mygame.handlers.*;

/**
 * 
 * @author Clemaurde
 * 
 */

public class PlayScreen extends InputAdapter implements Screen,
		SpeechRecognitionResultHandler, VoiceRecognitionService {

	SpriteBatch batch;
	LabryntMain game;

	private State view;
	// public static final long UPS = 50L;
	// public static final long UPDATE_TIME = 1000000000 / UPS; // frames per
	// second nanotime
	private float deltaTime = 0.0f;
	private float timer = 0.0f, slowTime = 1;
	int transition = 0, commandNum;
	float trim = 0, taps = 0;

	private Stage stage;
	private Table table;
	private Window window;
	private TextButton exitButton, resumeButton, okButton;
	private ImageButton musicButton, soundButton, speechButton;
	private TextButtonStyle buttonStyle, btnStyle;
	private ImageButtonStyle musicStyle, soundStyle, buttonStyle2;
	private Skin buttonSkin;

	private static Array<Updatable> updatables;
	private static Array<Drawable> drawables;
	private static Array<Disposable> disposables;

	public Label clockLabel, ammoCount, gameStatus, dialog, windowDialog,
			commandView;
	private LabelStyle style, style2, style3, style4;
	private Image clock, foreground;

	private Player player;
	private EnemyManager enemyManager;
	private ExplosionManager explosionManager;
	private BulletManager bulletManager;
	private CommandMatcher commandMatcher;
	public GameCamera cam;
	private TileMap field;
	private InputMultiplexer inputMultiplexer;
	private boolean speechRecognitionBusy;

	public PlayScreen(LabryntMain game) {

		this.game = game;
		batch = new SpriteBatch();
		inputMultiplexer = new InputMultiplexer();// sends input to two

		stage = new Stage(new StretchViewport(Constants.viewWidth,
				Constants.viewHeight), batch);
		table = new Table();
		table.setColor(1, 1, 1, 0.9f);

		cam = new GameCamera();
		player = new Player(Resources.playerAtlas, "Sprite");

		commandMatcher = new CommandMatcher();
		enemyManager = new EnemyManager(Resources.playerAtlas, player);
		explosionManager = new ExplosionManager(Resources.explosionAtlas,
				"explosion");

		bulletManager = new BulletManager(Resources.explosionAtlas);

		player.setExplosionManager(explosionManager);
		enemyManager.setExplosionManager(explosionManager);
		player.setBulletManager(bulletManager);

		clock = new Image(Resources.myClock);

		buttonSkin = new Skin(Resources.buttonAtlas);
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("btn1");
		buttonStyle.down = buttonSkin.getDrawable("btn1Pressed");
		buttonStyle.font = Resources.font.get(3);
		buttonStyle.fontColor = Color.WHITE;
		resumeButton = new TextButton("resume", buttonStyle);
		okButton = new TextButton("OK", buttonStyle);

		buttonStyle2 = new ImageButtonStyle();
		buttonStyle2.checked = buttonSkin.getDrawable("mic-on");
		buttonStyle2.up = buttonSkin.getDrawable("mic-off");
		speechButton = new ImageButton(buttonStyle2);

		soundStyle = new ImageButtonStyle();
		soundStyle.checked = buttonSkin.getDrawable("soundOn");
		soundStyle.up = buttonSkin.getDrawable("soundOff");
		soundButton = new ImageButton(soundStyle);

		musicStyle = new ImageButtonStyle();
		musicStyle.checked = buttonSkin.getDrawable("musicOn");
		musicStyle.up = buttonSkin.getDrawable("musicOff");
		musicButton = new ImageButton(musicStyle);

		btnStyle = new TextButtonStyle();
		btnStyle.up = buttonSkin.getDrawable("button0");
		btnStyle.down = buttonSkin.getDrawable("button1");
		btnStyle.font = Resources.font.get(4);
		btnStyle.fontColor = Color.WHITE;
		exitButton = new TextButton("exit", btnStyle);

		style = new LabelStyle(Resources.font.get(0), Color.WHITE);
		style2 = new LabelStyle(Resources.font.get(2), Color.WHITE);
		style3 = new LabelStyle(Resources.font.get(3), Color.WHITE);
		style4 = new LabelStyle(Resources.font.get(4), Color.WHITE);

		field = new TileMap(Resources.map, player, style3);
		// field.setLabelStyle(style4);

		dialog = new Label("", style3);
		gameStatus = new Label("GAMEOVER", style);
		gameStatus.setColor(Color.BLUE);
		gameStatus.setPosition(Constants.viewWidthHalf,
				Constants.viewHeightHalf, Align.center);

		commandView = new Label("        ", style4);
		commandView.setColor(Color.PINK);
		commandView.setPosition(Constants.viewWidthHalf,
				Constants.viewHeightHalf * 0.15f, Align.center);

		commandView.setVisible(false);

		// style2.font.setScale(0.9f);
		clockLabel = new Label(" ", style2);

		ammoCount = new Label("", style2);
		windowDialog = new Label(Constants.powerBlockDialog, style4);

		windowDialog.setWrap(true);
		windowDialog.setAlignment(Align.center);

		window = new Window("", Resources.skin);

		window.padTop(20).padBottom(50);
		window.add(windowDialog).width(300).row();
		window.add(okButton).padTop(20).center().row();
		window.setSize(stage.getWidth() * 0.75f, stage.getHeight() * 0.50f);
		window.setPosition(stage.getWidth() / 2 - window.getWidth() / 2,
				stage.getHeight() / 2 - window.getHeight() / 2);
		window.setColor(1, 1, 1, 0.95f);

		// window.pack();

		// table.setBackground(new TextureRegionDrawable(pauseMenu));
		table.setBackground(Resources.skin.getDrawable("default-rect"));
		table.center();
		table.add(speechButton).colspan(3).center().padBottom(40);
		table.row();
		table.add(musicButton)/* .width(146).height(148) */.left().padRight(40);
		table.add(soundButton).right().padLeft(40);
		table.row();
		table.add(resumeButton).colspan(3).center().pad(60, 0, 80, 0);
		table.row();
		table.add(exitButton).colspan(3).center().bottom();
		table.setBounds(0, 0, Constants.viewWidth, Constants.viewHeight);
		// table.debug();

		foreground = new Image(Resources.background);
		foreground.setSize(Constants.viewWidth, Constants.deviceHeight);

		initArrays();
		initialize();

		stage.addActor(gameStatus);
		stage.addActor(foreground);
		stage.addActor(dialog);
		stage.addActor(table);
		stage.addActor(window);
		stage.addActor(commandView);

	}

	@Override
	public void show() {

		// initialize();
	}

	private void initialize() {

		enemyManager.setMapArray(field.getPath());

		dialog.setText("Get ready\n" + "field " + (Constants.currentLevel + 1));
		dialog.setPosition(
				Constants.viewWidthHalf/*-(dialog.getPrefWidth()/2.0f)*/,
				Constants.viewHeightHalf);
		dialog.setAlignment(Align.center);

		// foreground.getColor().a=0;
		foreground.addAction(Actions.sequence(Actions.delay(3f),
				Actions.fadeOut(1.2f)));
		window.setVisible(false);

		view = State.Idle;
		table.setVisible(false);

		deltaTime = 0.0f;
		timer = 120.0f;
		transition = 0;
		speechRecognitionBusy = true;

		player.init();
		trim = (player.center().y * 0.35f);
		enemyManager.init();
		enemyManager.createEnemy();

		clock.setPosition(player.center().x, player.center().y + trim,
				Align.center);

		cam.init(player.getActorPosition());
		cam.update();
		field.initVar();
		field.render.setView(cam.camera);

		clockLabel.setColor(Color.BLACK);
		clockLabel.setText("  " + (int) timer);
		clockLabel.setPosition(
				clock.getX(Align.center) - clockLabel.getPrefWidth() * 0.53f,
				clock.getY(Align.center) + clockLabel.getPrefHeight() * 0.60f,
				Align.center);
		ammoCount.setColor(Color.BLACK);
		ammoCount.setText("  " + Constants.Ammo);
		ammoCount.setPosition(
				clock.getX(Align.right) - ammoCount.getPrefWidth() * 2.3f,
				clock.getY(Align.center) - ammoCount.getPrefHeight() * 0.60f,
				Align.center);

		updateClockLables();

		dialog.setVisible(true);
		gameStatus.setVisible(false);

		if (Constants.currentLevel < 1) {

			inputMultiplexer.addProcessor(stage);
			inputMultiplexer.addProcessor(this);

		}

		if (Gdx.app.getType().equals(ApplicationType.Android)) { // if android
																	// phone
			Gdx.input.setInputProcessor(inputMultiplexer);
			// Gdx.input.setCatchMenuKey(true);
			Gdx.input.setCatchBackKey(true);
		} else {

			Gdx.input.setInputProcessor(inputMultiplexer);
		}
	}

	private void initArrays() {

		updatables = new Array<Updatable>();
		updatables.add(player);
		updatables.add(enemyManager);
		updatables.add(explosionManager);
		updatables.add(bulletManager);
		updatables.add(cam);
		updatables.add(field);

		drawables = new Array<Drawable>();
		drawables.add(player);
		drawables.add(enemyManager);
		drawables.add(bulletManager);
		drawables.add(explosionManager);

		// drawables.add(field);

		disposables = new Array<Disposable>();
		disposables.add(batch);
		disposables.add(field);
		disposables.add(stage);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Gdx.gl.glViewport(x, y, width, height);
		deltaTime += delta;

		stage.act();

		if (foreground.getColor().a == 0 && view == State.Idle) {
			view = State.PLAYING;
			dialog.setVisible(false);
		}
		// ************Game States ************

		if (deltaTime >= 0.009f) {
			deltaTime -= 0.01;

			switch (view) {

			case LOADING:
				break;

			case PLAYING:

				cam.setPlayerPos(player.getActorPosition());
				field.render.setView(cam.camera);

				for (int i = 0; i < updatables.size; i++) {
					updatables.get(i).update();
				}

				updateClockLables();

				/*
				 * Gdx.app.log("FPSLogger", "fps: " +
				 * Gdx.graphics.getFramesPerSecond());
				 */

				// first powerblock item dialog

				if (Constants.prefs.getInteger("powerBlock") == 1) {
					eventTrigger(1);
				}
				// first weapon block item dialog

				if (Constants.prefs.getInteger("weaponBlock") == 1) {
					eventTrigger(2);
				}

				if (!player.Alive) {
					player.explosionManager.addExplosion(player.center());
					view = State.GAMEOVER;
					gameStatus.setText("GAMEOVER");
					gameStatus.setVisible(true);
				}

				checkBulletCollision();

				// countdown clock
				if (!speechButton.isChecked()) {
					if (timer >= 0) { // Stop Watch
						clockLabel.setText(""
								+ (MathUtils.round(timer -= Gdx.graphics
										.getDeltaTime())));
					}
				} else {
					slowTime = slowTime + Gdx.graphics.getDeltaTime();
					System.out.println(slowTime);
					if (slowTime >= 4) {
						slowTime = 0;
						clockLabel.setText("" + MathUtils.round(timer--));
					}
				}

				if (timer < 10f) {
					clockLabel.setColor(Color.RED);
				}

				if (timer <= 0f && !player.atGoal) {
					view = State.GAMEOVER;
					gameStatus.setText("GAMEOVER");
					gameStatus.setVisible(true);
				}
				if (timer != 0f && player.atGoal) {
					view = State.LEVEL_PASSED;
				}

				/*
				 * if (TimeUtils.nanoTime() - lastUpdateTime > UPDATE_TIME) {
				 * lastUpdateTime = TimeUtils.nanoTime(); }
				 */
				break;
			case PAUSED:

				table.setVisible(true);

				if (resumeButton.isPressed()) {
					view = State.PLAYING;
					table.setVisible(false);
				}

				if (exitButton.isPressed()) {
					view = State.GAMEOVER;
					table.setVisible(false);
				}

				if (speechButton.isChecked()) {
					Resources.musicplayer.stop();
					if (!field.enableTileLabel) {
						field.enableTileLabel = true;
						if (Gdx.app.getType().equals(ApplicationType.Android)) {
							game.startVoiceRecognition(this);
							commandView.setVisible(true);
						}
					}
				} else {
					if (field.enableTileLabel) {
						field.enableTileLabel = false;
						game.cancelService();
						commandView.setVisible(false);

					}
				}
				if (musicButton.isChecked()) {
					Resources.musicplayer.setVolume(0.6f);
					Resources.musicplayer.play();

				} else {
					Resources.musicplayer.stop();

				}

				break;

			case EVENT:

				updateClockLables();

				window.setVisible(true);
				if (okButton.isPressed()) {
					view = State.PLAYING;
					window.setVisible(false);
				}
				break;

			case EXIT:

				break;

			case GAMEOVER:

				explosionManager.update();
				bulletManager.update();

				updateClockLables();

				transition++;
				if (transition <= 1) {
					Constants.Ammo = 0;

					int num = Constants.prefs.getInteger("Highscore");
					if (num < Constants.currentLevel) {
						Constants.prefs.putInteger("Highscore",
								Constants.currentLevel);
						Constants.prefs.flush();
					}

					Constants.currentLevel = 0;
					// gameStatus.setText("GAMEOVER");
					// gameStatus.setVisible(true);
					this.cancelService();
					foreground.addAction(Actions.sequence(Actions.delay(3f),
							Actions.fadeIn(2f), Actions.run(new Runnable() {

								@Override
								public void run() {
									game.setScreen(game.mainMenu);
									// dispose();
								}
							})));
				}

				break;
			case LEVEL_PASSED:

				updateClockLables();

				gameStatus.setText("    Winner");

				gameStatus.setPosition(Constants.viewWidthHalf,
						Constants.viewHeightHalf, Align.center);

				gameStatus.setVisible(true);
				Constants.currentLevel++;
				// Constants.prefs.putInteger("highestLevel",
				// Constants.currentLevel);
				// Constants.prefs.flush();
				foreground.addAction(Actions.sequence(Actions.delay(3f),
						Actions.fadeIn(3f), Actions.run(new Runnable() {

							@Override
							public void run() {
								field.setMap(Constants.currentLevel);
								enemyManager.purge();
								initialize();
							}
						})));
				view = State.LOADING;

				break;
			default:
				break;
			}

		}

		drawContent();
	}

	private void drawContent() {

		batch.setProjectionMatrix(cam.camera.combined);

		// field.render.render();
		field.draw(batch);

		batch.begin();
		for (int i = 0; i < drawables.size; i++) {
			drawables.get(i).draw(batch);
		}

		clock.draw(batch, 1f);
		clockLabel.draw(batch, 1f);
		ammoCount.draw(batch, 1f);
		batch.end();

		stage.draw();
		// System.out.println("playscreen drawing");
	}

	public void eventTrigger(int event) {

		switch (event) {
		case 1:
			view = State.EVENT;
			Constants.prefs.putInteger("powerBlock", 2);
			Constants.prefs.flush();
			windowDialog.setText(Constants.powerBlockDialog);
			break;
		case 2:
			view = State.EVENT;
			Constants.prefs.putInteger("weaponBlock", 2);
			Constants.prefs.flush();
			windowDialog.setText(Constants.weaponBlockDialog);
			break;

		}

	}

	public void updateClockLables() {

		clock.setPosition(player.center().x, player.center().y + trim,
				Align.center);
		clockLabel
				.setPosition(
						clock.getX(Align.center) - clockLabel.getPrefWidth()
								* 0.54f,
						clock.getY(Align.center) + clockLabel.getPrefHeight()
								* 0.15f/* , Align.center */);

		ammoCount.setText("" + Constants.Ammo);
		ammoCount
				.setPosition(clock.getX(Align.right) - ammoCount.getPrefWidth()
						* 2.2f,
						clock.getY(Align.center) - ammoCount.getPrefHeight()
								* 0.60f/* ,Align.center */);
	}

	public void checkBulletCollision() {
		try {
			for (Bullet b : bulletManager.getArray()) {
				for (Enemy e : enemyManager.getArray()) {
					if (b.rectangle.overlaps(e.getCollisionRec())) {
						e.Alive = false;
					}
				}
			}
		} catch (NullPointerException ex) {

		}
	}

	@Override
	public void resize(int width, int height) {

		window.setSize(stage.getWidth() * 0.75f, stage.getHeight() * 0.50f);
		window.setPosition(stage.getWidth() / 2 - window.getWidth() / 2,
				stage.getHeight() / 2 - window.getHeight() / 2);
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

		for (Disposable d : disposables) {
			d.dispose();
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (view == State.PLAYING && player.getState() != State.moving) {

			Vector3 point2 = new Vector3(screenX, screenY, 0);
			cam.camera.unproject(point2);

			// Switch player from attack mode to idle mode
			if (player.getTilePos().contains(point2.x, point2.y)) {
				if (player.getState() != State.ATTACK)
					player.setState(State.ATTACK);
				else {
					player.setState(State.Idle);
				}

			} else {
				// check if touched point is a valid tile on the map to
				// travel
				// towards
				if (field.isLegalMove(new Vector2(point2.x, point2.y))) {

					field.findPath();

				}
			}

		}

		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (/* keycode == Keys.MENU || */keycode == Keys.ESCAPE
				|| keycode == Keys.BACK) {
			view = State.PAUSED;
		}

		return false;
	}

	@Override
	public boolean isServiceAvailable() {
		return game.isServiceAvailable();
	}

	@Override
	public void startVoiceRecognition(SpeechRecognitionResultHandler callback) {

		System.out.println("startVoiceRecognition main");
		game.startVoiceRecognition(callback);

	}

	@Override
	public void onError(int error, boolean fatalError) {
		if (!fatalError) {
			System.out.println("Sorry, didn't catch that.");
			// SoundEngine.play(SoundEngine.Sounds.DIDNT_CATCH_THAT);
			speechRecognitionBusy = false;
		} else {
			System.out
					.println("Looks like the radio is gone. You're on your own now.");
		}

	}

	@Override
	public void onReadyForSpeech() {
		System.out.println("Awaiting command sir");

	}

	@Override
	public void gotSpeechResults(String results) {

		System.out.println("processing\n");
		Gdx.app.log("PlayScreen", "gotSpeechResults: " + results);

		if (speechRecognitionBusy) {
			speechRecognitionBusy = false;

			commandNum = commandMatcher.stringToInt(results);

			commandView.setText(results);
			commandView.setPosition(Constants.viewWidthHalf,
					Constants.viewHeightHalf * 0.15f, Align.center);

			if (commandNum != 0)
				if (view == State.PLAYING && player.getState() != State.moving)
					if (field.isLegalMove(commandNum)) {						
						messagePrompt(field.findPath());
					} else {
						messagePrompt("illegal move");
					}

			speechRecognitionBusy = true;

		}

	}

	@Override
	public void cancelService() {
		game.cancelService();

	}

	@Override
	public void messagePrompt(String text) {
		game.messagePrompt(text);

	}

}
