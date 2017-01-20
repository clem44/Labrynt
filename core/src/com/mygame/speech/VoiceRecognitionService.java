package com.mygame.speech;


public interface VoiceRecognitionService {
	
	public boolean isServiceAvailable();
	public void startVoiceRecognition(SpeechRecognitionResultHandler callback);
	public void cancelService();
	public void messagePrompt(String text);

}