package com.sregnard.themebreaker.classes.brickbreaker;

import com.sregnard.themebreaker.classes.game.Sprite;
import com.sregnard.themebreaker.classes.geometry.Point;
import com.sregnard.themebreaker.classes.geometry.Rectangle;

public class Paddle extends Rectangle {

	private boolean needUpdate = false;

	// Modèle représentant la raquette
	public Sprite sprite;

	public Paddle(Point center, float height, float width) {
		super(center, height, width);
	}

	public Paddle(Paddle paddle) {
		this.height = paddle.height;
		this.width = paddle.width;
		this.pos = new Point(paddle.pos);
	}

	// Expérimental
	public float ballBounce(Ball ball) {
		Point ballCenter = ball.center();
		boolean betweenTopAndBottom = ballCenter.getY() > top()
				&& ballCenter.getY() < bottom();
		boolean betweenLeftAndRight = ballCenter.getX() > left()
				&& ballCenter.getX() < right();
		boolean inside = betweenLeftAndRight && betweenTopAndBottom;

		// Contact avec le haut ou le bas
		boolean contactTopOrBottom = !inside && betweenLeftAndRight;
		if (!contactTopOrBottom)
			return ball.direction();

		float percentage = (ballCenter.getX() - center().getX()) / width * 100;
		return percentage / 2 - ball.direction;
	}

	public void move(float dx) {
		pos.setX(nextPos(dx));
	}

	public float nextPos(float dx) {
		return pos.getX() + dx;
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
