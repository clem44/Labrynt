package com.mygame.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;

public class SoundEffect implements Sound{

	Sound effect;
	int index = 0;

	/** The delay between tracks */
	long delay;

	/** The last time a track was played */
	long lastPlayed;
	
	public SoundEffect(String path){
		
		effect = Gdx.audio.newSound(Gdx.files.internal(path));
	}
	
	
	
	@Override
	public long play() {
		
		return effect.play();
	}
	
	public void play(float volume, double delay) {
		if ((TimeUtils.nanoTime() - lastPlayed) > delay * 1000000000) {
			//System.out.println(/*(TimeUtils.nanoTime() -*/ lastPlayed/*) > delay / 1000000000*/);
			Play(volume);
		}
	}

	public void Play(float volume) {
	     
	         effect.play(volume);
	         lastPlayed = TimeUtils.nanoTime();
	}
	

	@Override
	public long play(float volume) {
		
		return effect.play(volume);
	}

	@Override
	public long play(float volume, float pitch, float pan) {
		
		return effect.play(volume,pitch,pan);
	}

	@Override
	public long loop() {
		return effect.loop();
		
	}

	@Override
	public long loop(float volume) {
		
		return effect.loop(volume);
		
	}

	@Override
	public long loop(float volume, float pitch, float pan) {
		
		return effect.loop(volume, pitch, pan);
	}

	@Override
	public void stop() {
		effect.stop();
		
	}

	@Override
	public void pause() {
		effect.pause();
		
	}

	@Override
	public void resume() {
		effect.resume();
		
	}

	@Override
	public void dispose() {
		effect.dispose();
		
	}

	@Override
	public void stop(long soundId) {
		effect.stop(soundId);
		
	}

	@Override
	public void pause(long soundId) {
		effect.pause(soundId);
		
	}

	@Override
	public void resume(long soundId) {
		effect.resume(soundId);
		
	}

	@Override
	public void setLooping(long soundId, boolean looping) {
		effect.setLooping(soundId, looping);
		
	}

	@Override
	public void setPitch(long soundId, float pitch) {
		effect.setPitch(soundId, pitch);
		
	}

	@Override
	public void setVolume(long soundId, float volume) {
		effect.setVolume(soundId, volume);
		
	}

	@Override
	public void setPan(long soundId, float pan, float volume) {
		effect.setPan(soundId, pan, volume);
		
	}

	@Override
	public void setPriority(long soundId, int priority) {
		effect.setPriority(soundId, priority);
		
	}
}
