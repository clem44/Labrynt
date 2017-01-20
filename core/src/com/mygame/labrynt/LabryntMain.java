package com.mygame.labrynt;

import com.badlogic.gdx.Game;
import com.mygame.speech.SpeechRecognitionResultHandler;
import com.mygame.speech.VoiceRecognitionService;
import com.mygame.statics.Resources;

public class LabryntMain extends Game implements
		SpeechRecognitionResultHandler, VoiceRecognitionService {

	// public Constants constants;
	public Resources resource;
	public VoiceRecognitionService voiceRecognitionService;
	public Menu mainMenu;
	public PlayScreen playScreen;

	@Override
	public void create() {
		
		setScreen(new SplashScreen(this));

	}

	public LabryntMain(VoiceRecognitionService voiceRecognitionService) {
		this.voiceRecognitionService = voiceRecognitionService;
	}

	public LabryntMain() {

	}

	@Override
	public void render() {
		super.render();

	}

	public void getMenu() {

	}

	public void dispose() {
		super.dispose();
		mainMenu.dispose();
		//resource.dispose();

	}

	@Override
	public boolean isServiceAvailable() {

		return this.voiceRecognitionService.isServiceAvailable();
	}

	@Override
	public void startVoiceRecognition(SpeechRecognitionResultHandler callback) {

		this.voiceRecognitionService.startVoiceRecognition(callback);

	}

	@Override
	public void onError(int error, boolean fatalError) {
		playScreen.onError(error, fatalError);

		if (!fatalError) {
			// System.out.println("Sorry, didn't catch that.");
			// SoundEngine.play(SoundEngine.Sounds.DIDNT_CATCH_THAT);
			// speechRecognitionBusy = false;
		} else {
			// System.out.println("Looks like the radio is gone. You're on your own now.");
		}

	}

	@Override
	public void onReadyForSpeech() {

		playScreen.onReadyForSpeech();
	}

	@Override
	public void gotSpeechResults(String results) {
		playScreen.gotSpeechResults(results);

	}

	@Override
	public void cancelService() {
		try{
		if(isServiceAvailable())
			voiceRecognitionService.cancelService();
		}catch(NullPointerException ie){
			System.out.println("voice service is null");
		}
		
	}

	@Override
	public void messagePrompt(String text) {
		voiceRecognitionService.messagePrompt(text);
		
	}

}
