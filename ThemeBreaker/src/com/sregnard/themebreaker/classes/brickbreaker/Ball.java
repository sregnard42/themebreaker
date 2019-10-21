package com.sregnard.themebreaker.classes.brickbreaker;

import com.sregnard.themebreaker.classes.game.Sprite;
import com.sregnard.themebreaker.classes.geometry.Circle;
import com.sregnard.themebreaker.classes.geometry.Geometry;
import com.sregnard.themebreaker.classes.geometry.Point;

public class Ball extends Circle {

	public static final int D_RIGHT, D_DOWN, D_LEFT, D_UP;
	public static final float DEFAULT_SPEED = 0.1f;

	static {
		D_RIGHT = 0;
		D_DOWN = 90;
		D_LEFT = 180;
		D_UP = 270;
	}

	// angle de direction, en degrées
	float direction;
	float speed;

	// Modèle représentant la balle
	public Sprite sprite;

	public Ball(Point center, float radius) {
		super(center, radius);
		init();
	}

	public Ball(Ball b) {
		this.pos = new Point(b.pos);
		this.diameter = b.diameter;
		this.speed = b.speed;
		this.direction = b.direction;
	}

	public void init() {
		direction = D_UP;
		speed = DEFAULT_SPEED;
	}

	public Point nextPos() {
		float radians = Geometry.degreesToRadians(direction);
		float x = (float) Math.cos(radians);
		float y = (float) Math.sin(radians);
		float distance = speed * diameter;
		return new Point(pos.getX() + x * distance, pos.getY() + y * distance);
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setDirection(float direction) {
		while (direction >= 360)
			direction -= 360;
		while (direction < 0)
			direction += 360;
		this.direction = direction;
	}

	public float direction() {
		return direction;
	}

	public void move(float dx, float dy) {
		pos.setX(pos.getX() + dx);
		pos.setY(pos.getY() + dy);
	}

	public Circle getCircle() {
		Circle c = new Circle(this.center(), radius());
		return c;
	}
}
