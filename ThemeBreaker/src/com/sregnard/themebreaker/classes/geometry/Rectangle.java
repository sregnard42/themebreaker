package com.sregnard.themebreaker.classes.geometry;

public class Rectangle extends Shape {

	protected float height;
	protected float width;

	public Rectangle(Point center, float height, float width) {
		this.width = width;
		this.height = height;
		pos = new Point(center.getX() - width / 2, center.getY() - height / 2);
	}

	public Rectangle(float left, float top, float right, float bottom) {
		width = Math.abs(right - left);
		height = Math.abs(bottom - top);
		pos = new Point(left, top);
	}

	public Rectangle() {
		this.width = 0;
		this.height = 0;
		pos = new Point(0, 0);
	}

	// On combine deux rectangles adjacents
	public static Rectangle merge(Rectangle r1, Rectangle r2) {

		float left = 0, top = 0, right = 0, bottom = 0;

		boolean horizontallyAligned = r1.top() == r2.top()
				&& r1.bottom() == r2.bottom();
		boolean verticallyAligned = r1.left() == r2.left()
				&& r1.right() == r2.right();

		// Si les deux rectangles sont alignés horizontalement
		if (horizontallyAligned) {
			top = r1.top();
			bottom = r2.bottom();
			// r1 à gauche et r2 à droite
			if (r1.right() == r2.left()) {
				left = r1.left();
				right = r2.right();
			}
			// r2 à gauche et r1 à droite
			else if (r2.right() == r1.left()) {
				left = r2.left();
				right = r1.right();
			} else {
				System.out.println("Rectangles non adjacents !");
			}

		}
		// Si les deux rectangles sont alignés verticalement
		else if (verticallyAligned) {
			left = r1.left();
			right = r2.right();
			// r1 en haut et r2 en bas
			if (r1.bottom() == r2.top()) {
				top = r1.top();
				bottom = r2.bottom();
			}
			// r2 en haut et r1 en bas
			else if (r2.bottom() == r1.top()) {
				top = r2.top();
				bottom = r1.bottom();
			} else {
				System.out.println("Rectangles non adjacents !");
			}
		} else {
			System.out.println("Rectangles non adjacents !");
		}

		return new Rectangle(left, top, right, bottom);
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setCenter(Point p) {
		pos.setX(p.getX() - width / 2);
		pos.setY(p.getY() - height / 2);
	}

	public float width() {
		return width;
	}

	public float height() {
		return height;
	}

	public Point center() {
		return new Point(pos.getX() + width / 2, pos.getY() + height / 2);
	}

	public Point LT() {
		return new Point(left(), top());
	}

	public Point LB() {
		return new Point(left(), bottom());
	}

	public Point RT() {
		return new Point(right(), top());
	}

	public Point RB() {
		return new Point(right(), bottom());
	}

	public float left() {
		return pos.getX();
	}

	public float top() {
		return pos.getY();
	}

	public float right() {
		return left() + width();
	}

	public float bottom() {
		return top() + height();
	}

}
