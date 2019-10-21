package com.sregnard.themebreaker.classes.geometry;

public class Line {

	// inclinaison
	float slope;

	// ordonnée à l'origine
	float yIntercept;

	public Line() {
		slope = 0;
		yIntercept = 0;
	}

	// Deux points sont nécessaires à la création d'une droite
	public Line(Point p1, Point p2) {
		slope = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
		yIntercept = p1.getY() - slope * p1.getX();
	}

	public float XtoY(float x) {
		return slope * x + yIntercept;
	}

	public float YtoX(float y) {
		return (y - yIntercept) / slope;
	}

	// Retourne l'angle en °
	public float getAngle() {
		float angle = 0f;
		if (slope == Float.NEGATIVE_INFINITY
				|| slope == Float.POSITIVE_INFINITY) {
			angle = 90;
		} else {
			angle = Geometry.radiansToDegrees((float) Math.atan(slope));
		}
		return angle;
	}

	// Retourne la droite passant par le point p, qui est perpendiculaire à la
	// droite cible
	public Line perpendicular(Point p) {
		Line perpendiculaire = new Line();
		perpendiculaire.slope = -1 / this.slope;
		perpendiculaire.yIntercept = p.getY() - perpendiculaire.slope * p.getX();
		return perpendiculaire;
	}

	public String toString() {
		String s = "";
		s += "y = ";
		if (slope == 0 && yIntercept == 0)
			s += "0";
		else if (slope != 0) {
			s += slope + "x";
			if (yIntercept != 0)
				s += " + " + yIntercept;
		} else
			s += yIntercept;
		return s;
	}

	public boolean equals(Line l) {
		return this.slope == l.slope && this.yIntercept == l.yIntercept;
	}
}
