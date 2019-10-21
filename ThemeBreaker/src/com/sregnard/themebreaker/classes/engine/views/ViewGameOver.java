package com.sregnard.themebreaker.classes.engine.views;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sregnard.themebreaker.R;
import com.sregnard.themebreaker.classes.engine.ModelTB;

public class ViewGameOver extends LinearLayout {

	ModelTB model;

	RelativeLayout gameLayout;
	float width, height;
	RectF dst;

	public ViewGameOver(Activity owner, ModelTB model,
			RelativeLayout gameLayout, float cellSize) {
		super(owner);

		this.gameLayout = gameLayout;

		this.setBackgroundColor(Color.BLACK);
		this.setAlpha(0.8f);

		// Statut fin de partie
		String msg;
		if (model.gameWon) {
			msg = "Victoire !";
		} else {
			msg = "Défaite !";
		}

		// On calcule la taille du layout contenant la vue
		width = (float) (gameLayout.getWidth() * 0.75);
		height = (float) (gameLayout.getHeight() * 0.75);

		// On applique les dimensions calculées
		LayoutParams params = new LayoutParams((int) width, (int) height);
		this.setLayoutParams(params);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setGravity(Gravity.CENTER);

		// On le centre dans gameLayout
		this.setX((gameLayout.getWidth() - width) / 2);
		this.setY((gameLayout.getHeight() - height) / 2);

		// Rectangle représentant le layout
		dst = new RectF(0, 0, width, height);

		// TextView
		params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		TextView txtLevelAndDifficulty = new TextView(owner);
		txtLevelAndDifficulty.setText(model.level.toString() + " ("
				+ model.difficulty.toString() + ")");
		txtLevelAndDifficulty.setLayoutParams(params);
		this.addView(txtLevelAndDifficulty);

		TextView txtResultGame = new TextView(owner);
		txtResultGame.setText(msg);
		txtResultGame.setLayoutParams(params);
		this.addView(txtResultGame);

		TextView txtScore = new TextView(owner);
		txtScore.setText(Integer.toString(model.score));
		txtScore.setLayoutParams(params);
		this.addView(txtScore);

		// Button
		ImageButton btnExit = (ImageButton) owner.findViewById(R.id.btnQuitter);
		((ViewGroup) btnExit.getParent()).removeView(btnExit);
		this.addView(btnExit);

		// On ajoute le layout dans gameLayout
		gameLayout.addView(this);
	}
}
