package com.mygame.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygame.inter.Updatable;

public class MusicManager implements Music, Updatable{

	
	Music song;
	int index = 0;
	/** The delay between tracks */
	float delay;
	/** The last time a track was played */
	long lastPlayed;
	float time = 0.0f;
	float volume;
	boolean loop;
	
	public MusicManager(String path){		
		song = Gdx.audio.newMusic(Gdx.files.internal(path));
	}
	public MusicManager(Music song){		
		this.song = song;
	}
	@Override
	public void play() {
		if(!isPlaying())
			song.play();
	}
	
	public void play(float volume, float delay) {
		this.volume = volume;
		this.delay = delay;
		if ((TimeUtils.nanoTime() - lastPlayed) > delay * 1000000000L) {
			//System.out.println(/*(TimeUtils.nanoTime() -*/ lastPlayed/*) > delay / 1000000000*/);
			Play(volume);
		}
	}
	public void beginLoop(){
		if (loop) {
			//System.out.println(/*(TimeUtils.nanoTime() -*/ lastPlayed/*) > delay / 1000000000*/);
			Play(this.volume);
		}
	}
	public void setDelay(float delay, float volume){
		this.delay = delay;
		this.volume = volume;
		time = delay;
		//song.play();
	}

	public void Play(float volume) {
	     
	         song.play();
	         song.setVolume(volume);
	         lastPlayed = TimeUtils.nanoTime();
	}
	public void setUpdateTimer(float time){
		this.time = time;
	}
	
	public void update() {
		//System.out.println(time);
		
		/*if(!song.isPlaying())
			song.play();*/
		
		if((time+=Gdx.graphics.getDeltaTime()) > delay){
			loop = true;
			song.stop();
			Play(this.volume);
			time = 0.0f;
		}
		else{
			loop = false;
		}
		
	}
	

	@Override
	public void pause() {
		song.pause();
		
	}

	@Override
	public void stop() {
		if(isPlaying())
		 song.stop();
		
	}

	@Override
	public boolean isPlaying() {
		return  song.isPlaying();
		
	}

	@Override
	public void setLooping(boolean isLooping) {
		song.setLooping(isLooping);
		
	}

	@Override
	public boolean isLooping() {
		// TODO Auto-generated method stub
		return song.isLooping();
	}

	@Override
	public void setVolume(float volume) {
		song.setVolume(volume);
		
	}

	@Override
	public float getVolume() {		
		return song.getVolume();
	}

	@Override
	public void setPan(float pan, float volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getPosition() {
		
		return song.getPosition();
	}

	@Override
	public void dispose() {
		song.dispose();
		
	}

	@Override
	public void setOnCompletionListener(OnCompletionListener listener) {
		song.setOnCompletionListener( listener);
		
	}

	@Override
	public void setPosition(float position) {
		// TODO Auto-generated method stub
		
	}

	

}