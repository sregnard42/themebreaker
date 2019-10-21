package com.sregnard.themebreaker.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.sregnard.themebreaker.R;
import com.sregnard.themebreaker.classes.engine.ControllerTB;
import com.sregnard.themebreaker.classes.engine.ModelTB;
import com.sregnard.themebreaker.classes.engine.ViewTB;
import com.sregnard.themebreaker.classes.engine.state.StateGame;
import com.sregnard.themebreaker.classes.engine.state.StateOptions;
import com.sregnard.themebreaker.classes.game.Sprite;
import com.sregnard.themebreaker.classes.sound.SFXManager;

public class GameEngine extends Activity {

	ModelTB model;
	ControllerTB controller;
	ViewTB view;

	StateGame game = new StateGame();
	StateOptions options = new StateOptions();

	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("GameEngine : onCreate()");
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window w = getWindow();
		w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_game_engine);

		Sprite.init(this);
		new SFXManager(this);

		model = new ModelTB(this);
		controller = new ControllerTB(model, this);
		view = new ViewTB(model, controller, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (game.isRunning()) {
			game.pause();
		}
	}

	@Override
	public void onBackPressed() {
		model.closeActivity();
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (!view.initialized)
			view.init();
		if (!model.initialized)
			model.init();
	}

	public StateGame getGameState() {
		return game;
	}

	public StateOptions getGameOptionsState() {
		return options;
	}
}
