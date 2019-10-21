package com.sregnard.themebreaker.classes.engine;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.sregnard.themebreaker.R;
import com.sregnard.themebreaker.activities.GameEngine;
import com.sregnard.themebreaker.activities.ThemeBreaker;
import com.sregnard.themebreaker.classes.brickbreaker.Brick;
import com.sregnard.themebreaker.classes.engine.state.StateGame;
import com.sregnard.themebreaker.classes.engine.state.StateOptions;
import com.sregnard.themebreaker.classes.engine.views.ConfigGameLayout;
import com.sregnard.themebreaker.classes.engine.views.ViewBall;
import com.sregnard.themebreaker.classes.engine.views.ViewBrick;
import com.sregnard.themebreaker.classes.engine.views.ViewGameOver;
import com.sregnard.themebreaker.classes.engine.views.ViewPaddle;
import com.sregnard.themebreaker.classes.geometry.Point;
import com.sregnard.themebreaker.classes.others.Observer;

public class ViewTB implements Observer {

	ModelTB model;
	ControllerTB controller;
	StateGame game;
	StateOptions options;

	GameEngine owner;
	Handler mainHandler;
	Runnable myRunnable;
	RelativeLayout mainLayout;
	public RelativeLayout gameLayout;

	// LayoutGame
	public ConfigGameLayout configLayout;
	public ViewBall viewBall;
	public ViewPaddle viewPaddle;
	public ArrayList<ViewBrick> viewsBrick = new ArrayList<ViewBrick>();
	Point posLayout;
	float cellSize;

	// LayoutGameOver
	ViewGameOver layoutGameOver;

	// LayoutGameInfo
	TextView txtLife;
	TextView txtScore;

	// LayoutSettings
	ImageButton btnPause;
	ImageButton btnPlay;
	ImageButton btnOptions;
	ImageButton btnMusique;
	Button btnSFX;
	ImageButton btnQuitter;

	public boolean initialized = false;

	public ViewTB(ModelTB model, ControllerTB controller, GameEngine owner) {
		this.model = model;
		this.controller = controller;
		this.owner = owner;
		configLayout = new ConfigGameLayout(owner);
	}

	public void init() {

		initialized = true;

		model.addObs(this);
		controller.addObs(this);
		game = controller.game;
		options = controller.options;

		mainHandler = new Handler(owner.getMainLooper());
		mainLayout = (RelativeLayout) owner
				.findViewById(R.id.layout_game_engine);
		mainLayout.setBackgroundColor(Color.BLACK);
		gameLayout = (RelativeLayout) owner.findViewById(R.id.layout_game);
		gameLayout.setBackgroundResource(model.level.getMap().spriteBackground
				.getID());
		gameLayout.setOnTouchListener(controller);

		// Model

		initConfigLayout();
		initGameInfo();
		initViewsBrick();
		initViewBall();
		initViewPaddle();

		// Controller

		btnPause = (ImageButton) owner.findViewById(R.id.btnPause);
		btnPlay = (ImageButton) owner.findViewById(R.id.btnPlay);
		btnOptions = (ImageButton) owner.findViewById(R.id.btnOptions);
		btnMusique = (ImageButton) owner.findViewById(R.id.btnMusique);
		btnSFX = (Button) owner.findViewById(R.id.btnSFX);
		btnQuitter = (ImageButton) owner.findViewById(R.id.btnQuitter);

		btnPause.setOnTouchListener(controller);
		btnPlay.setOnTouchListener(controller);
		btnOptions.setOnTouchListener(controller);
		btnMusique.setOnTouchListener(controller);
		btnSFX.setOnTouchListener(controller);
		btnQuitter.setOnTouchListener(controller);

		controller.btnPause = btnPause;
		controller.btnPlay = btnPlay;
		controller.btnOptions = btnOptions;
		controller.btnMusique = btnMusique;
		controller.btnSFX = btnSFX;
		controller.btnQuitter = btnQuitter;

		// Test

		model.needUpdate = true;
		controller.needUpdate = true;
		update();
	}

	public void initConfigLayout() {
		configLayout = new ConfigGameLayout(owner);
		posLayout = configLayout.posLayout;
		cellSize = configLayout.cellSize;
		controller.cellSize = cellSize;
	}

	public void initGameInfo() {
		txtLife = (TextView) owner.findViewById(R.id.txtLife);
		txtScore = (TextView) owner.findViewById(R.id.txtScore);
		txtLife.setText(Integer.toString(model.life));
		txtScore.setText(Integer.toString(model.score));
	}

	public void initViewBall() {
		model.ball.sprite = model.level.getMap().spriteBall;
		viewBall = new ViewBall(owner, model.ball, cellSize);
		viewBall.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		gameLayout.addView(viewBall);
	}

	public void initViewPaddle() {
		model.paddle.sprite = model.level.getMap().spritePaddle;
		viewPaddle = new ViewPaddle(owner, model.paddle, cellSize);
		viewPaddle.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		viewPaddle.setMinimumHeight(100);
		controller.viewPaddle = viewPaddle;
		gameLayout.addView(viewPaddle);
	}

	public void initViewsBrick() {
		for (Brick b : model.bricks) {
			initViewBrick(b);
		}
	}

	public void initViewBrick(Brick b) {
		viewsBrick.add(new ViewBrick(owner, b, cellSize));
		viewsBrick.get(viewsBrick.size() - 1).setLayoutParams(
				new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
		gameLayout.addView(viewsBrick.get(viewsBrick.size() - 1));
	}

	public void updateViewsBrick() {
		for (ViewBrick viewBrick : viewsBrick) {
			if (viewBrick.brick.needUpdate()) {
				viewBrick.invalidate();
				viewBrick.brick.updated();
			}
			if (!model.bricks.contains(viewBrick.brick))
				gameLayout.removeView(viewBrick);
		}
	}

	@Override
	public void update() {
		if (model.needUpdate()) {
			myRunnable = new Runnable() {
				@Override
				public void run() {
					updateFromModel();
				}
			};
			edit();
		}

		if (controller.needUpdate()) {
			myRunnable = new Runnable() {
				@Override
				public void run() {
					updateFromController();
				}
			};
			edit();
		}
	}

	private void updateFromModel() {
		viewBall.invalidate();
		if (model.paddle.needUpdate())
			viewPaddle.invalidate();
		if (model.updateBricks)
			updateViewsBrick();
		if (model.updateLife)
			txtLife.setText(Integer.toString(model.life));
		if (model.updateScore)
			txtScore.setText(Integer.toString(model.score));
		model.updateFinished();
	}

	private void updateFromController() {

		if (ThemeBreaker.SFX)
			btnSFX.setTextColor(Color.GREEN);
		else
			btnSFX.setTextColor(Color.RED);

		if (ThemeBreaker.MUSIC)
			btnMusique.setImageAlpha(255);
		else
			btnMusique.setImageAlpha(122);

		if (game.needUpdate()) {
			if (game.isInitialized() || game.isPaused()) {
				btnPause.setVisibility(View.INVISIBLE);
				btnPlay.setVisibility(View.VISIBLE);
			} else if (game.isRunning()) {
				btnPause.setVisibility(View.VISIBLE);
				btnPlay.setVisibility(View.INVISIBLE);
			} else if (game.isOver()) {
				btnPause.setVisibility(View.INVISIBLE);
				btnPlay.setVisibility(View.INVISIBLE);
				btnOptions.setVisibility(View.INVISIBLE);
				btnMusique.setVisibility(View.INVISIBLE);
				btnSFX.setVisibility(View.INVISIBLE);
				btnQuitter.setVisibility(View.VISIBLE);
				layoutGameOver = new ViewGameOver(owner, model, gameLayout,
						cellSize);
			}
			game.updateFinished();
		}

		if (options.needUpdate()) {
			if (options.isOff()) {
				btnOptions.setVisibility(View.INVISIBLE);
				btnMusique.setVisibility(View.INVISIBLE);
				btnSFX.setVisibility(View.INVISIBLE);
				btnQuitter.setVisibility(View.INVISIBLE);
			} else if (options.isOn()) {
				btnOptions.setVisibility(View.VISIBLE);
				btnMusique.setVisibility(View.VISIBLE);
				btnSFX.setVisibility(View.VISIBLE);
				btnQuitter.setVisibility(View.VISIBLE);
			} else if (options.isHidden()) {
				btnOptions.setVisibility(View.VISIBLE);
				btnMusique.setVisibility(View.INVISIBLE);
				btnSFX.setVisibility(View.INVISIBLE);
				btnQuitter.setVisibility(View.INVISIBLE);
			}
			options.updateFinished();
		}

		controller.updateFinished();
	}

	private void edit() {
		mainHandler.post(myRunnable);
		mainLayout.postInvalidate();
	}

	public View getLayout() {
		return mainLayout;
	}

}
