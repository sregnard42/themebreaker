package com.sregnard.themebreaker.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sregnard.themebreaker.R;
import com.sregnard.themebreaker.classes.game.DifficultyManager;
import com.sregnard.themebreaker.classes.game.LevelManager;

public class MenuPlay extends Activity {

	TextView displayLevel;
	TextView displayDifficulty;

	// Pour déterminer si l'activité vient d'être lancée ou pas
	private boolean onCreate = true;

	@Override
	protected void onPause() {
		super.onPause();
		ThemeBreaker.backgroundMusic.pause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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

		Button btnMenuJouerParametres = new Button(this);
		btnMenuJouerParametres.setText("Paramètres de jeu");
		btnMenuJouerParametres.setBackgroundResource(R.drawable.shape_rounded);
		btnMenuJouerParametres.setTextColor(Color.WHITE);
		btnMenuJouerParametres.setLayoutParams(rParams);
		btnMenuJouerParametres.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MenuPlay.this,
						MenuPlayParamaters.class);
				startActivity(myIntent);
			}
		});

		Button btnMenuOptions = new Button(this);
		btnMenuOptions.setText("Lancer la partie");
		btnMenuOptions.setBackgroundResource(R.drawable.shape_rounded);
		btnMenuOptions.setTextColor(Color.WHITE);
		btnMenuOptions.setLayoutParams(rParams);
		btnMenuOptions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MenuPlay.this, GameEngine.class);
				startActivity(myIntent);
			}
		});

		displayLevel = new TextView(this);
		displayLevel.setLayoutParams(rParams);
		setDisplayLevel();

		displayDifficulty = new TextView(this);
		displayDifficulty.setLayoutParams(rParams);
		setDisplayDifficulty();

		vLayout.addView(btnMenuJouerParametres);
		vLayout.addView(btnMenuOptions);
		vLayout.addView(displayLevel);
		vLayout.addView(displayDifficulty);
		rLayout.addView(vLayout);
		rLayout.setBackgroundResource(R.drawable.background);
		setContentView(rLayout);
	}

	public void setDisplayLevel() {
		String selectedLevel = LevelManager.getString(ThemeBreaker.LEVEL);
		displayLevel.setText(selectedLevel);
		displayLevel.setGravity(Gravity.CENTER);
	}

	public void setDisplayDifficulty() {
		String selectedDifficulty = DifficultyManager
				.getString(ThemeBreaker.DIFFICULTY);
		displayDifficulty.setText("Difficulté : " + selectedDifficulty);
		displayDifficulty.setGravity(Gravity.CENTER);

	}

	@Override
	public void onResume() {
		super.onResume();

		// Si on reprend l'activité
		if (!onCreate) {
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_right);
		}
		// Sinon l'activité termine son processus de création
		else
			onCreate = false;

		ThemeBreaker.backgroundMusic.play();

		onCreate = false;
		setDisplayLevel();
		setDisplayDifficulty();
	}
}
