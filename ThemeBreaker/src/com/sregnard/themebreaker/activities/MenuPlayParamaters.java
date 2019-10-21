package com.sregnard.themebreaker.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.sregnard.themebreaker.R;
import com.sregnard.themebreaker.classes.game.DifficultyManager;
import com.sregnard.themebreaker.classes.game.LevelManager;

public class MenuPlayParamaters extends Activity implements
		OnItemSelectedListener {

	ArrayList<String> levels = new ArrayList<String>();
	ArrayList<String> difficulties = new ArrayList<String>();
	Spinner spinLevel;
	Spinner spinDifficulty;
	ArrayAdapter<String> adapterLevel;
	ArrayAdapter<String> adapterDifficulty;

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

		for (int i = 1; i <= ThemeBreaker.LEVEL_PROGRESSION; i++) {
			levels.add(LevelManager.getString(i));
		}

		for (int i = 1; i <= DifficultyManager.maximumDifficulty().getID(); i++) {
			difficulties.add(DifficultyManager.getString(i));
		}

		spinLevel = new Spinner(this);
		spinLevel.setLayoutParams(rParams);
		spinLevel.setOnItemSelectedListener(this);
		adapterLevel = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, levels);
		spinLevel.setAdapter(adapterLevel);

		spinDifficulty = new Spinner(this);
		spinDifficulty.setLayoutParams(rParams);
		spinDifficulty.setOnItemSelectedListener(this);
		adapterDifficulty = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, difficulties);
		spinDifficulty.setAdapter(adapterDifficulty);

		selectDefaultValues();

		vLayout.addView(spinLevel);
		vLayout.addView(spinDifficulty);
		rLayout.addView(vLayout);
		rLayout.setBackgroundResource(R.drawable.background);
		setContentView(rLayout);
	}

	public void selectDefaultValues() {
		defaultLevel();
		defaultDifficulty();
	}

	public void defaultLevel() {
		String value = LevelManager.getString(ThemeBreaker.LEVEL);
		spinLevel.setSelection(levels.indexOf(value));
	}

	public void defaultDifficulty() {
		String value = DifficultyManager.getString(ThemeBreaker.DIFFICULTY);
		spinDifficulty.setSelection(difficulties.indexOf(value));
	}

	public void setLevel(int position) {
		String value = levels.get(position);
		ThemeBreaker.setLevel(LevelManager.getLevel(value).number());
	}

	public void setDifficulty(int position) {
		String value = difficulties.get(position);
		ThemeBreaker.setDifficulty(DifficultyManager.getId(value));
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent == spinLevel)
			setLevel(position);
		else if (parent == spinDifficulty)
			setDifficulty(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		if (parent == spinLevel)
			defaultLevel();
		else if (parent == spinDifficulty)
			defaultDifficulty();
	}
}
