package com.sregnard.themebreaker.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sregnard.themebreaker.R;
import com.sregnard.themebreaker.classes.game.LevelManager;

public class MenuSettings extends Activity {

	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		ThemeBreaker.backgroundMusic.play();
	}

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

		LayoutParams match_parent = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		LayoutParams wrap_content = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		LayoutParams match_wrap = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// LayoutParams wrap_match = new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.MATCH_PARENT);

		RelativeLayout rLayout = new RelativeLayout(this);

		rLayout.setLayoutParams(match_parent);
		rLayout.setGravity(Gravity.CENTER);

		LinearLayout vLayout = new LinearLayout(this);
		vLayout.setOrientation(LinearLayout.VERTICAL);

		vLayout.setLayoutParams(wrap_content);
		vLayout.setGravity(Gravity.CENTER);

		ArrayList<LinearLayout> hLayout = new ArrayList<LinearLayout>();
		for (int i = 0; i < 2; i++) {
			hLayout.add(new LinearLayout(this));
			hLayout.get(i).setOrientation(LinearLayout.HORIZONTAL);
			hLayout.get(i).setLayoutParams(match_wrap);
			hLayout.get(i).setGravity(Gravity.CENTER);
			vLayout.addView(hLayout.get(i));
		}

		// SFX

		TextView txtSFX = new TextView(this);
		txtSFX.setLayoutParams(wrap_content);
		txtSFX.setText("SFX");
		hLayout.get(0).addView(txtSFX);

		CheckBox chkSFX = new CheckBox(this);
		chkSFX.setLayoutParams(wrap_content);
		chkSFX.setChecked(ThemeBreaker.SFX);
		chkSFX.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				ThemeBreaker.setSFX(isChecked);
			}
		});
		hLayout.get(0).addView(chkSFX);

		// Musique

		TextView txtMusic = new TextView(this);
		txtMusic.setLayoutParams(wrap_content);
		txtMusic.setText("Musique");
		hLayout.get(1).addView(txtMusic);

		CheckBox chkMusic = new CheckBox(this);
		chkMusic.setLayoutParams(wrap_content);
		chkMusic.setChecked(ThemeBreaker.MUSIC);
		chkMusic.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				ThemeBreaker.setMusic(isChecked);
			}
		});
		hLayout.get(1).addView(chkMusic);

		// Unlock all lvl

		Button btnUnlock = new Button(this);
		btnUnlock.setLayoutParams(wrap_content);
		btnUnlock.setText("Débloquer tous les niveaux");
		btnUnlock.setBackgroundResource(R.drawable.shape_rounded);
		btnUnlock.setTextColor(Color.WHITE);
		btnUnlock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ThemeBreaker.setLevelProgression(LevelManager
						.getNumberOfLevel());
			}
		});
		vLayout.addView(btnUnlock);

		// Reset progression

		Button btnReset = new Button(this);
		btnReset.setLayoutParams(wrap_content);
		btnReset.setText("Remettre à zéro la progression");
		btnReset.setBackgroundResource(R.drawable.shape_rounded);
		btnReset.setTextColor(Color.WHITE);
		btnReset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ThemeBreaker.setLevelProgression(1);
			}
		});
		vLayout.addView(btnReset);

		rLayout.addView(vLayout);
		rLayout.setBackgroundResource(R.drawable.background);
		setContentView(rLayout);

	}
}
