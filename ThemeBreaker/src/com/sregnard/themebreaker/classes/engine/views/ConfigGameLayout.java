package com.sregnard.themebreaker.classes.engine.views;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.sregnard.themebreaker.R;
import com.sregnard.themebreaker.activities.ThemeBreaker;
import com.sregnard.themebreaker.classes.game.LevelManager;
import com.sregnard.themebreaker.classes.game.Map;
import com.sregnard.themebreaker.classes.geometry.Point;

public class ConfigGameLayout {

	public Activity owner;
	public android.graphics.Point size = new android.graphics.Point();

	// Layout
	public RelativeLayout gameLayout;
	public Point posLayout;
	public float heightLayout;
	public float widthLayout;
	public float cellSize;

	public Map map;
	ViewPaddle viewPaddle;

	public ConfigGameLayout(Activity owner) {

		// Activité
		this.owner = owner;

		// Taille de l'écran
		owner.getWindowManager().getDefaultDisplay().getSize(size);

		// Layout contenant le jeu
		gameLayout = (RelativeLayout) owner.findViewById(R.id.layout_game);

		init();
	}

	public void init() {
		adjustSize();
		adjustPosition();

		// On récupère la position du layout
		posLayout = new Point(gameLayout.getX(), gameLayout.getY());

		// On récupère le niveau en cours
		map = LevelManager.getLevel(ThemeBreaker.LEVEL).getMap();

		// On adapate la largeur d'une cellule à la largeur de la map
		cellSize = widthLayout / (float) map.width();
	}

	// Ajuste la taille du layout pour garder un ratio de 3/2 pour la
	// hauteur/largeur
	public void adjustSize() {

		// On récupère la hauteur laissée à gameLayout et calcule la largeur en
		// conséquence
		heightLayout = size.y - owner.findViewById(R.id.layout_top).getHeight();
		widthLayout = heightLayout * (2f / 3f);

		// Si la largeur calculée dépasse celle de l'écran, on réduit la taille
		// du layout
		if (widthLayout > size.x) {
			widthLayout = size.x;
			heightLayout = widthLayout * (3f / 2f);
		}

		// On applique la hauteur et la largeur définitive du layout
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) gameLayout
				.getLayoutParams();
		params.height = (int) heightLayout;
		params.width = (int) widthLayout;
		gameLayout.setLayoutParams(params);
	}

	// On positionne le layout au centre horizontalement et en bas de l'écran
	public void adjustPosition() {
		gameLayout.setX((size.x - widthLayout) / 2);
		gameLayout.setY((size.y - heightLayout));
	}
}
