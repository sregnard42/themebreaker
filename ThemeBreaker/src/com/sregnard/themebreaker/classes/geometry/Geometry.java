package com.sregnard.themebreaker.classes.geometry;

public class Geometry {

	public static boolean circleInterRectangle(Circle c, Rectangle r) {
		Point centerCircle = c.center();
		float radius = c.radius();

		Point centerRectangle = r.center();
		float width = r.width();
		float height = r.height();

		float dx = Math.abs(centerCircle.getX() - centerRectangle.getX());
		float dy = Math.abs(centerCircle.getY() - centerRectangle.getY());

		if (dx <= width / 2 + radius && dy <= height / 2 + radius)
			return true;

		return false;
	}

	public boolean rectangleInterCircle(Rectangle r, Circle c) {
		return circleInterRectangle(c, r);
	}

	public static float radiansToDegrees(float radians) {
		return (float) (radians * 180 / Math.PI);
	}

	public static float degreesToRadians(float degrees) {
		return (float) (degrees * Math.PI / 180);
	}

	// Retourne la tangent exacte ou alors parallèlle
	// p centre de la balle, r rectangle ciblé
	public static Line tangentImpactRectangle(Point p, Rectangle r) {

		Line tangent = new Line();

		boolean betweenLeftAndRight = p.getX() > r.left() && p.getX() < r.right();
		boolean betweenTopAndBottom = p.getY() > r.top() && p.getY() < r.bottom();
		boolean inside = betweenLeftAndRight && betweenTopAndBottom;

		// Contact avec la gauche ou la droite
		boolean contactLeftOrRight = !inside && betweenTopAndBottom;

		// Contact avec le haut ou le bas
		boolean contactTopOrBottom = !inside && betweenLeftAndRight;

		// Contact avec un des coins
		boolean contactLeftTop = p.getX() <= r.left() && p.getY() <= r.top();
		boolean contactLeftBottom = p.getX() <= r.left() && p.getY() >= r.bottom();
		boolean contactRightTop = p.getX() >= r.right() && p.getY() <= r.top();
		boolean contactRightBottom = p.getX() >= r.right() && p.getY() >= r.bottom();

		// A l'intérieur du rectangle
		if (inside) {
			float dx, dy, dLeft, dTop, dRight, dBottom;
			dLeft = Math.abs(p.getX() - r.left());
			dTop = Math.abs(p.getY() - r.top());
			dRight = Math.abs(p.getX() - r.right());
			dBottom = Math.abs(p.getY() - r.bottom());
			dx = Math.min(dLeft, dRight);
			dy = Math.min(dTop, dBottom);
			contactLeftOrRight = dx < dy;
			contactTopOrBottom = !contactLeftOrRight;
		}

		// Contact avec la gauche ou la droite
		if (contactLeftOrRight) {
			tangent = new Line(r.LT(), r.LB());
		}

		// Contact avec le haut ou le bas
		else if (contactTopOrBottom) {
			tangent = new Line(r.LT(), r.RT());
		}

		// Contact avec le coin left-top
		else if (contactLeftTop) {
			tangent = (new Line(r.LT(), p)).perpendicular(r.LT());
		}

		// Contact avec le coin left-bottom
		else if (contactLeftBottom) {
			tangent = (new Line(r.LB(), p)).perpendicular(r.LB());
		}

		// Contact avec le coin right-top
		else if (contactRightTop) {
			tangent = (new Line(r.RT(), p)).perpendicular(r.RT());
		}

		// Contact avec le coin right-bottom
		else if (contactRightBottom) {
			tangent = (new Line(r.RB(), p)).perpendicular(r.RB());
		}

		return tangent;
	}
}
