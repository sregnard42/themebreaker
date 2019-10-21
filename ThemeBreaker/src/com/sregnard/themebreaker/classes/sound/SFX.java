package com.sregnard.themebreaker.classes.sound;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.sregnard.themebreaker.R;

public abstract class SFX {

	static Activity owner;
	static MediaPlayer.OnCompletionListener listener;

	public static void init(Activity owner, OnCompletionListener listener) {
		SFX.owner = owner;
		SFX.listener = listener;
	}

	static void initSFX(MediaPlayer mp) {
		mp.setOnCompletionListener(listener);
		mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
		SFXManager.add(mp);
	}

	public static void play(MediaPlayer mp) {
		mp.start();
	}

	// Collision

	public static void playCollision() {
		play(collisionSFX());
	}

	public static MediaPlayer collisionSFX() {
		MediaPlayer mp = MediaPlayer.create(owner, R.raw.sfx_collision);
		initSFX(mp);
		return mp;
	}

	// Break

	public static void playBreak() {
		play(breakSFX());
	}

	public static MediaPlayer breakSFX() {
		MediaPlayer mp = MediaPlayer.create(owner, R.raw.sfx_break);
		initSFX(mp);
		return mp;
	}

	// Fail

	public static void playFail() {
		play(failSFX());
	}

	public static MediaPlayer failSFX() {
		MediaPlayer mp = MediaPlayer.create(owner, R.raw.sfx_fail);
		initSFX(mp);
		return mp;
	}
}