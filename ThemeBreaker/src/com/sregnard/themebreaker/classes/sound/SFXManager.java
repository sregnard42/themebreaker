package com.sregnard.themebreaker.classes.sound;

import java.util.ArrayList;

import android.app.Activity;
import android.media.MediaPlayer;

public class SFXManager implements MediaPlayer.OnCompletionListener {

	public static ArrayList<MediaPlayer> sfx = new ArrayList<MediaPlayer>();
	public static int volume = 100;

	public SFXManager(Activity owner) {
		SFX.init(owner, this);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		sfx.remove(mp);
		if (mp != null) {
			if (mp.isPlaying())
				mp.stop();
			mp.reset();
			mp.release();
			mp = null;
		}
	}

	public static void add(MediaPlayer mp) {
		mp.setVolume(volume, volume);
		sfx.add(mp);
	}

	public static void setVolume(int i) {
		volume = i;
		for (MediaPlayer mp : sfx)
			mp.setVolume(i, i);
	}
}
