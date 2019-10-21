package com.sregnard.themebreaker.classes.engine;

import java.util.ArrayList;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.sregnard.themebreaker.activities.GameEngine;
import com.sregnard.themebreaker.activities.ThemeBreaker;
import com.sregnard.themebreaker.classes.engine.state.StateGame;
import com.sregnard.themebreaker.classes.engine.state.StateOptions;
import com.sregnard.themebreaker.classes.engine.views.ViewPaddle;
import com.sregnard.themebreaker.classes.others.Observable;
import com.sregnard.themebreaker.classes.others.Observer;

public class ControllerTB implements Observable, Observer, OnTouchListener {

	ArrayList<Observer> obs = new ArrayList<Observer>();
	boolean needUpdate = true;

	ModelTB model;
	GameEngine owner;
	StateGame game;
	StateOptions options;

	// Ecran
	int height, width;

	// OnTouch
	private float lastX;
	float cellSize;
	public ViewPaddle viewPaddle;

	ImageButton btnPause;
	ImageButton btnPlay;
	ImageButton btnOptions;
	ImageButton btnMusique;
	Button btnSFX;
	ImageButton btnQuitter;

	public ControllerTB(ModelTB model, GameEngine owner) {
		this.model = model;
		this.owner = owner;
		game = owner.getGameState();
		options = owner.getGameOptionsState();
		game.addObs(this);
		options.addObs(this);

		Point size = new Point();
		owner.getWindowManager().getDefaultDisplay().getSize(size);
		height = size.y;
		width = size.x;
	}

	public void btnPlay() {
		if (!game.isRunning())
			game.run();
	}

	public void btnPause() {
		if (!game.isPaused())
			game.pause();
	}

	public void btnOptions() {
		if (options.isHidden())
			options.on();
		else if (options.isOn())
			options.hide();
	}

	public void btnMusique() {
		ThemeBreaker.setMusic(!ThemeBreaker.MUSIC);
		update();
	}

	public void btnSFX() {
		ThemeBreaker.setSFX(!ThemeBreaker.SFX);
		update();
	}

	public void btnQuitter() {
		model.closeActivity();
	}

	/***** Touch Listener *****/

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float dx = 0;
		final int x = (int) event.getRawX();
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			if (v == btnPause)
				btnPause();
			if (v == btnPlay)
				btnPlay();
			if (v == btnOptions)
				btnOptions();
			if (v == btnMusique)
				btnMusique();
			if (v == btnSFX)
				btnSFX();
			if (v == btnQuitter)
				btnQuitter();
			break;
		case MotionEvent.ACTION_UP:
			v.performClick();
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			break;
		case MotionEvent.ACTION_POINTER_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			dx = (x - lastX) / cellSize;
			if (dx != 0) {
				model.movePaddle((int) Math.signum(dx), Math.abs(dx));
			}
			break;
		}
		lastX = x;
		return true;
	}

	/***** Observable *****/

	@Override
	public void addObs(Observer obs) {
		this.obs.add(obs);
	}

	@Override
	public void delObs(Observer obs) {
		this.obs.remove(obs);
	}

	@Override
	public void delAllObs() {
		this.obs.clear();
	}

	@Override
	public void updateAll() {
		needUpdate = true;
		for (Observer obs : this.obs)
			obs.update();
	}

	@Override
	public void updateFinished() {
		needUpdate = false;
	}

	public boolean needUpdate() {
		return needUpdate;
	}

	@Override
	public void update() {
		updateAll();
	}

}
