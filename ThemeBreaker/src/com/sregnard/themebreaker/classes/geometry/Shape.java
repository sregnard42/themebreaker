package com.sregnard.themebreaker.classes.geometry;

public abstract class Shape implements ShapeInterface {

	// Corner Top Left
	protected Point pos;

	public void setPos(Point p) {
		pos = p;
	}

	public Point getPos() {
		return pos;
	}

}
