package com.sregnard.themebreaker.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.sregnard.themebreaker.R;
import com.sregnard.themebreaker.classes.game.Difficulty;
import com.sregnard.themebreaker.classes.game.Level;
import com.sregnard.themebreaker.classes.game.LevelManager;
import com.sregnard.themebreaker.classes.sound.Music;
import com.sregnard.themebreaker.classes.sound.SFXManager;

public class ThemeBreaker extends Activity {

	private final String DATA_FILENAME = "BrickBreaker_Data";
	private SharedPreferences data;
	private static Editor editor;

	public static int LEVEL; // Le dernier niveau sélectionné
	public static int LEVEL_PROGRESSION; // Le dernier niveau débloqué
	public static int DIFFICULTY; // La dernière difficulté sélectionnée
	public static boolean SFX; // Les SFX sont-ils activés ?
	public static boolean MUSIC; // La musique est-elle activée ?

	public static Music backgroundMusic;

	// Pour déterminer si l'activité vient d'être lancée ou pas
	private boolean onCreate = true;

	@Override
	protected void onResume() {
		super.onResume();

		// Si on reprend l'activité
		if (!onCreate) {
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_right);
			backgroundMusic.play();
		}
		// Sinon l'activité termine son processus de création
		else
			onCreate = false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		backgroundMusic.pause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

		System.out.println("App launched");

		// Initialise les niveaux & les niveaux de difficultés
		Level.init();
		Difficulty.init();

		// Récupération des données
		data = getSharedPreferences(DATA_FILENAME, 0);
		editor = data.edit();
		LEVEL = data.getInt("LEVEL", 1);
		LEVEL_PROGRESSION = data.getInt("LEVEL_PROGRESSION", 1);
		DIFFICULTY = data.getInt("DIFFICULTY",
				Difficulty.DIFFICULTY_VERY_HARD.getID());
		SFX = data.getBoolean("SFX", true);
		MUSIC = data.getBoolean("MUSIC", true);

		// Ajustement
		if (LEVEL_PROGRESSION < LevelManager.minLevel)
			setLevelProgression(LevelManager.minLevel);
		else if (LEVEL_PROGRESSION > LevelManager.getNumberOfLevel())
			setLevelProgression(LevelManager.getNumberOfLevel());
		if (LEVEL < LevelManager.minLevel)
			setLevel(LevelManager.minLevel);
		else if (LEVEL > LEVEL_PROGRESSION)
			setLevel(LEVEL_PROGRESSION);

		// Musique
		backgroundMusic = new Music(this);
		backgroundMusic.setMedia(Music.MUSIC_MENU);
		adjustVolume();

		// Menu
		RelativeLayout rLayout = new RelativeLayout(this);
		LayoutParams rParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		rLayout.setLayoutParams(rParams);
		rLayout.setGravity(Gravity.CENTER);

		LinearLayout vLayout = new LinearLayout(this);
		vLayout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams lParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		vLayout.setLayoutParams(lParams);
		vLayout.setGravity(Gravity.CENTER);

		// Bouton vers MenuPlay
		Button btnMenuJouer = new Button(this);
		btnMenuJouer.setText("Jouer");
		btnMenuJouer.setBackgroundResource(R.drawable.shape_rounded);
		btnMenuJouer.setTextColor(Color.WHITE);
		btnMenuJouer.setLayoutParams(rParams);
		btnMenuJouer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(ThemeBreaker.this, MenuPlay.class);
				startActivity(myIntent);
			}
		});

		// Bouton vers MenuSettings
		Button btnMenuOptions = new Button(this);
		btnMenuOptions.setText("Options");
		btnMenuOptions.setBackgroundResource(R.drawable.shape_rounded);
		btnMenuOptions.setTextColor(Color.WHITE);
		btnMenuOptions.setLayoutParams(rParams);
		btnMenuOptions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(ThemeBreaker.this,
						MenuSettings.class);
				startActivity(myIntent);
			}
		});

		vLayout.addView(btnMenuJouer);
		vLayout.addView(btnMenuOptions);
		rLayout.addView(vLayout);
		rLayout.setBackgroundResource(R.drawable.background);
		setContentView(rLayout);
	}

	public static void setLevel(int num) {
		LEVEL = num;
		editor.putInt("LEVEL", LEVEL);
		if (editor.commit())
			System.out.println("Sauvegarde réussie");
		else
			System.out.println("Echec de la sauvegarde");
	}

	public static void setLevelProgression(int num) {
		LEVEL_PROGRESSION = num;
		editor.putInt("LEVEL_PROGRESSION", LEVEL_PROGRESSION);
		editor.apply();

		if (LEVEL_PROGRESSION < LEVEL)
			setLevel(LEVEL_PROGRESSION);
	}

	public static void setDifficulty(int id) {
		DIFFICULTY = id;
		editor.putInt("DIFFICULTY", DIFFICULTY);
		editor.apply();
	}

	public static void setSFX(boolean b) {
		SFX = b;
		editor.putBoolean("SFX", SFX);
		editor.apply();
		adjustVolume();
	}

	public static void setMusic(boolean b) {
		MUSIC = b;
		editor.putBoolean("MUSIC", MUSIC);
		editor.apply();
		adjustVolume();
	}

	public static void adjustVolume() {
		if (MUSIC)
			backgroundMusic.setVolume(1);
		else
			backgroundMusic.setVolume(0);
		if (SFX)
			SFXManager.setVolume(1);
		else
			SFXManager.setVolume(0);
	}
}
