package com.sregnard.themebreaker.classes.brickbreaker;

public class BounceType {

	public final static BounceType GROUND;
	public static final BounceType NONE;
	public static final BounceType BRICK;
	public static final BounceType WALL;
	public static final BounceType ROOF;
	public static final BounceType BALL_PADDLE;

	static {
		GROUND = new BounceType(-1);
		NONE = new BounceType(0);
		BRICK = new BounceType(1);
		WALL = new BounceType(2);
		ROOF = new BounceType(3);
		BALL_PADDLE = new BounceType(4);
	}

	public int type;

	public BounceType() {
		type = 0;
	}

	public BounceType(int type) {
		this.type = type;
	}

	public boolean is(BounceType r) {
		return this.type == r.type;
	}

}
