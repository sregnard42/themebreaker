package com.sregnard.themebreaker.classes.sound;

import android.app.Activity;
import android.media.MediaPlayer;

import com.sregnard.themebreaker.R;

public class Music {

	public static int MUSIC_WIN, MUSIC_LOSE, MUSIC_MENU, MUSIC_GAME;

	static {
		MUSIC_WIN = R.raw.music_win;
		MUSIC_LOSE = R.raw.music_lose;
		MUSIC_MENU = R.raw.music_menu;
		MUSIC_GAME = R.raw.music_game;
	}

	Activity owner;
	MediaPlayer media;
	int volume;

	public Music(Activity owner) {
		super();
		this.owner = owner;
		media = new MediaPlayer();
	}

	void init() {
		media.setLooping(true);
		media.setVolume(volume, volume);
		play();
	}

	public void setMedia(int resid) {
		destroyMedia();

		media = MediaPlayer.create(owner, resid);
		init();
	}

	public void destroyMedia() {
		media.pause();
		media.stop();
		media.release();
	}

	public void setLooping(Boolean b) {
		media.setLooping(b);
	}

	public void setVolume(int i) {
		volume = i;
		setVolumeMedia(volume);
	}

	public void setVolumeMedia(int i) {
		if (media != null) {
			media.setVolume(i, i);
		}
	}

	public boolean isPlaying() {
		return media.isPlaying();
	}

	public boolean isPaused() {
		return !isPlaying();
	}

	public void play() {
		if (isPaused())
			media.start();
	}

	public void pause() {
		if (isPlaying())
			media.pause();
	}

}
