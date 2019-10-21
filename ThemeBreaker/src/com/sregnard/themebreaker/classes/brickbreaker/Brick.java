package com.sregnard.themebreaker.classes.brickbreaker;

import java.util.ArrayList;

import com.sregnard.themebreaker.classes.game.Sprite;
import com.sregnard.themebreaker.classes.geometry.Rectangle;

public class Brick extends Rectangle {

	// Niveau de résistance 1
	public static final Brick R1_1 = new Brick(1, 1, 1);
	public static final Brick R1_2 = new Brick(1, 1, 2);
	public static final Brick R1_4 = new Brick(1, 1, 4);

	// Niveau de résistance 2
	public static final Brick R2_1 = new Brick(2, 1, 1);
	public static final Brick R2_2 = new Brick(2, 1, 2);
	public static final Brick R2_4 = new Brick(2, 1, 4);

	// Niveau de résistance 3
	public static final Brick R3_1 = new Brick(3, 1, 1);
	public static final Brick R3_2 = new Brick(3, 1, 2);
	public static final Brick R3_4 = new Brick(3, 1, 4);

	// Niveau de résistance 4
	public static final Brick R4_1 = new Brick(4, 1, 1);
	public static final Brick R4_2 = new Brick(4, 1, 2);
	public static final Brick R4_4 = new Brick(4, 1, 4);

	// Niveau de résistance 5
	public static final Brick R5_1 = new Brick(5, 1, 1);
	public static final Brick R5_2 = new Brick(5, 1, 2);
	public static final Brick R5_4 = new Brick(5, 1, 4);

	// Niveau de résistance 5
	public static final Brick R6_1 = new Brick(6, 1, 1);
	public static final Brick R6_2 = new Brick(6, 1, 2);
	public static final Brick R6_4 = new Brick(6, 1, 4);

	public int resistance;

	// Modèles servant à représenter la brique
	public ArrayList<Sprite> sprite = new ArrayList<Sprite>();

	private boolean needUpdate = false;

	public Brick(int resistance, int height, int width) {
		this.resistance = resistance;
		this.height = height;
		this.width = width;
	}

	public Brick(Brick b) {
		this.resistance = b.resistance;
		this.height = b.height;
		this.width = b.width;
		this.sprite = b.sprite;
	}

	public Rectangle getRekt() {
		Rectangle r = new Rectangle();
		r.setHeight(this.height);
		r.setWidth(this.width);
		r.setPos(this.pos);
		return r;
	}

	public String toString() {
		String s = "";
		s += "Resistance : " + resistance + " ";
		s += "Hauteur : " + height + " ";
		s += "Largeur : " + width + " ";
		s += "Position : " + pos + " ";
		return s;
	}

	public boolean needUpdate() {
		return needUpdate;
	}

	public void update() {
		needUpdate = true;
	}

	public void updated() {
		needUpdate = false;
	}
}
