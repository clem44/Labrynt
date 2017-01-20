package com.mygame.speech;

public interface  SpeechRecognitionResultHandler {
	
	public void onError(int error, boolean fatalError);
	public void onReadyForSpeech();
	public void gotSpeechResults(String text);
	
}
