package com.sregnard.themebreaker.classes.geometry;

public class Circle extends Shape {

	protected float diameter;

	public Circle(Point center, float radius) {
		diameter = radius * 2;
		pos = new Point(center.getX() - radius, center.getY() - radius);
	}

	public Circle() {
		diameter = 0;
		pos = new Point(0, 0);
	}

	public void setCenter(Point p) {
		pos.setX(p.getX() - radius());
		pos.setY(p.getY() - radius());
	}

	public float radius() {
		return diameter / 2;
	}

	public float diameter() {
		return diameter;
	}

	public Point center() {
		return new Point(pos.getX() + radius(), pos.getY() + radius());
	}

}
