package com.sregnard.themebreaker.classes.geometry;

public class Point {

	protected float x;
	protected float y;

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public Point() {
		this.x = 0;
		this.y = 0;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void incrX() {
		x++;
	}

	public void decrX() {
		x--;
	}

	public float getX() {
		return x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void incrY() {
		y++;
	}

	public void decrY() {
		y--;
	}

	public float getY() {
		return y;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public boolean equals(Point p) {
		return (x == p.x && y == p.y);
	}

	public void dx(float dx) {
		x += dx;
	}

	public void dy(float dy) {
		y += dy;
	}

}
