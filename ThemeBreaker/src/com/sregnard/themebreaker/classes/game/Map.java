package com.sregnard.themebreaker.classes.game;

import java.util.ArrayList;

import com.sregnard.themebreaker.classes.brickbreaker.Brick;
import com.sregnard.themebreaker.classes.geometry.Point;

public class Map {

	public final static int WIDTH = 20;
	public final static int HEIGHT = 30;

	int width;
	int height;
	ArrayList<Brick> bricks = new ArrayList<Brick>();

	public Sprite spriteBackground;
	public Sprite spriteBall;
	public Sprite spritePaddle;

	private Map() {
		this.width = WIDTH;
		this.height = HEIGHT;
		this.spriteBackground = Sprite.DEFAULT_BACKGROUND;
		this.spriteBall = Sprite.DEFAULT_BALL;
		this.spritePaddle = Sprite.DEFAULT_PADDLE;
	}

	private Map(Map map) {
		this.width = map.width;
		this.height = map.height;
		this.bricks = map.bricks;
		this.spriteBackground = map.spriteBackground;
		this.spriteBall = map.spriteBall;
		this.spritePaddle = map.spritePaddle;
	}

	// Testing Map
	public static Map Map_0() {
		Map map = new Map();
		ArrayList<Brick> bricks = map.bricks;
		ArrayList<Point> pos = new ArrayList<Point>();

		Brick brick = new Brick(Brick.R2_1);
		brick.sprite = Sprite.DEFAULT_BRICKS;

		for (float i = 0; i <= WIDTH - brick.width(); i += brick.width()) {
			for (float j = 1; j <= HEIGHT * 0.5 - brick.height(); j += brick
					.height() * 2) {
				bricks.add(new Brick(brick));
				pos.add(new Point(i, j));
			}
		}

		for (int i = 0; i < bricks.size(); i++) {
			bricks.get(i).setPos(pos.get(i));
		}

		return map;
	}

	// Neon Theme
	public static Map Map_1() {
		Map map = new Map();
		ArrayList<Brick> bricks = map.bricks;
		ArrayList<Point> pos = new ArrayList<Point>();

		map.spriteBackground = Sprite.NEON_BACKGROUND;
		map.spriteBall = Sprite.NEON_BALL;
		map.spritePaddle = Sprite.NEON_PADDLE;

		Brick brick;
		for (int i = 1; i < HEIGHT * 0.5; i += 2) {
			brick = Brick.R5_1;
			brick.sprite = Sprite.NEON_BRICKS_1;
			for (int j = 0; j < 4; j++)
				bricks.add(new Brick(brick));
			pos.add(new Point(1, i));
			pos.add(new Point(6, i));
			pos.add(new Point(13, i));
			pos.add(new Point(18, i));

			brick = Brick.R5_2;
			brick.sprite = Sprite.NEON_BRICKS_2;
			for (int j = 0; j < 2; j++)
				bricks.add(new Brick(brick));
			pos.add(new Point(3, i));
			pos.add(new Point(15, i));

			brick = Brick.R5_4;
			brick.sprite = Sprite.NEON_BRICKS_4;
			bricks.add(new Brick(brick));
			pos.add(new Point(8, i));
		}

		for (int i = 0; i < bricks.size(); i++) {
			bricks.get(i).setPos(pos.get(i));
		}

		return map;
	}

	// Cinema Theme
	public static Map Map_2() {
		Map map = new Map();
		ArrayList<Brick> bricks = map.bricks;
		ArrayList<Point> pos = new ArrayList<Point>();

		map.spriteBackground = Sprite.CINEMA_BACKGROUND;
		map.spriteBall = Sprite.CINEMA_BALL;
		map.spritePaddle = Sprite.CINEMA_PADDLE;

		Brick brick = new Brick(Brick.R3_4);
		brick.sprite = Sprite.CINEMA_BRICKS;

		int rowsPerType = (int) (5 * brick.height());
		int distanceBetweenTypes = (int) (brick.height());
		int startY = 0;
		int endY = startY + rowsPerType;

		for (float i = 0; i <= WIDTH - brick.width(); i += brick.width()) {
			for (float j = startY; j < endY; j += brick.height()) {
				bricks.add(new Brick(brick));
				pos.add(new Point(i, j));
			}
		}

		brick.resistance--;
		startY = (int) (endY + distanceBetweenTypes);
		endY = startY + rowsPerType;

		for (float i = 0; i <= WIDTH - brick.width(); i += brick.width()) {
			for (float j = startY; j < endY; j += brick.height()) {
				bricks.add(new Brick(brick));
				pos.add(new Point(i, j));
			}
		}

		brick.resistance--;
		startY = (int) (endY + distanceBetweenTypes);
		endY = startY + rowsPerType;

		for (float i = 0; i <= WIDTH - brick.width(); i += brick.width()) {
			for (float j = startY; j < endY; j += brick.height()) {
				bricks.add(new Brick(brick));
				pos.add(new Point(i, j));
			}
		}

		for (int i = 0; i < bricks.size(); i++) {
			bricks.get(i).setPos(pos.get(i));
		}

		return map;
	}

	// Mario Theme
	public static Map Map_3() {
		Map map = new Map();
		ArrayList<Brick> bricks = map.bricks;
		ArrayList<Point> pos = new ArrayList<Point>();

		map.spriteBackground = Sprite.MARIO_BACKGROUND;
		map.spriteBall = Sprite.MARIO_BALL;
		map.spritePaddle = Sprite.MARIO_PADDLE;

		// Mur
		Brick wall = new Brick(1, 2, 2);
		wall.sprite = Sprite.MARIO_BRICKS;

		// Mur "?"
		Brick questionMark = new Brick(2, 2, 2);
		questionMark.sprite = Sprite.MARIO_BRICKS;

		float distanceBetweenQuestionMarkAndWall = questionMark.height()
				+ wall.height();
		float distanceBetweenQuestionMarkAndRoof = questionMark.height();
		float distanceBetweenWallAndRoof = distanceBetweenQuestionMarkAndRoof
				+ distanceBetweenQuestionMarkAndWall;

		// Placement murs
		for (float i = 0; i <= WIDTH - wall.width(); i += wall.width()) {
			for (float j = distanceBetweenWallAndRoof; j <= HEIGHT * 0.75
					- (distanceBetweenWallAndRoof); j += wall.height()) {
				bricks.add(new Brick(wall));
				pos.add(new Point(i, j));
			}
		}

		// Placement murs "?"
		for (float i = 0; i <= WIDTH - questionMark.width(); i += questionMark
				.width()) {
			bricks.add(new Brick(questionMark));
			pos.add(new Point(i, distanceBetweenQuestionMarkAndRoof));
			bricks.add(new Brick(questionMark));
			pos.add(new Point(i,
					(HEIGHT * 0.75f - distanceBetweenQuestionMarkAndRoof)));
		}

		for (int i = 0; i < bricks.size(); i++) {
			bricks.get(i).setPos(pos.get(i));
		}

		return map;
	}

	// Pokemon Theme
	public static Map Map_4() {
		Map map = new Map();
		ArrayList<Brick> bricks = map.bricks;
		ArrayList<Point> pos = new ArrayList<Point>();

		map.spriteBackground = Sprite.POKEMON_BACKGROUND;
		map.spriteBall = Sprite.POKEMON_BALL;
		map.spritePaddle = Sprite.POKEMON_PADDLE;

		Brick brick;

		// Ramoloss
		brick = new Brick(2, 2, 2);
		brick.sprite = Sprite.POKEMON_WATER;

		for (int i = 1; i <= 17; i += brick.width()) {
			bricks.add(new Brick(brick));
			pos.add(new Point(i, 1));
			bricks.add(new Brick(brick));
			pos.add(new Point(i, 17));
		}

		for (int i = 3; i <= 15; i += brick.height()) {
			bricks.add(new Brick(brick));
			pos.add(new Point(1, i));
			bricks.add(new Brick(brick));
			pos.add(new Point(17, i));
		}

		// Pikachu
		brick = new Brick(1, 2, 2);
		brick.sprite = Sprite.POKEMON_PIKACHU;

		for (int i = 3; i <= 15; i += brick.width()) {
			bricks.add(new Brick(brick));
			pos.add(new Point(i, 3));
			bricks.add(new Brick(brick));
			pos.add(new Point(i, 15));
		}

		for (int i = 5; i <= 13; i += brick.height()) {
			bricks.add(new Brick(brick));
			pos.add(new Point(3, i));
			bricks.add(new Brick(brick));
			pos.add(new Point(15, i));
		}

		// Ronflex
		brick = new Brick(2, 2, 2);
		brick.sprite = Sprite.POKEMON_NEUTRAL;

		for (int i = 5; i <= 13; i += brick.width()) {
			bricks.add(new Brick(brick));
			pos.add(new Point(i, 5));
			bricks.add(new Brick(brick));
			pos.add(new Point(i, 13));
		}

		for (int i = 7; i <= 11; i += brick.height()) {
			bricks.add(new Brick(brick));
			pos.add(new Point(5, i));
			bricks.add(new Brick(brick));
			pos.add(new Point(13, i));
		}

		// Qulbutoke
		brick = new Brick(6, 6, 6);
		brick.sprite = Sprite.POKEMON_QULBUTOKE;

		bricks.add(new Brick(brick));
		pos.add(new Point(7, 7));

		for (int i = 0; i < bricks.size(); i++) {
			bricks.get(i).setPos(pos.get(i));
		}
		return map;
	}

	// Bank Theme
	public static Map Map_5() {
		Map map = new Map();

		ArrayList<Brick> bricks = map.bricks;
		ArrayList<Point> pos = new ArrayList<Point>();

		map.spriteBackground = Sprite.BANK_BACKGROUND;
		map.spriteBall = Sprite.BANK_BALL;
		map.spritePaddle = Sprite.BANK_PADDLE;

		Brick brickIngot = new Brick(2, 1, 3);
		Brick brickCash = new Brick(1, 1, 3);
		brickIngot.sprite = Sprite.BANK_BRICKS;
		brickCash.sprite = Sprite.BANK_BRICKS;

		int distanceBetweenIngotsAndRoof = 1;
		int distanceBetweenTwoRowsOfIngots = (int) (2 * brickIngot.height() + 4 * brickCash
				.height());
		int distanceBetweenCashAndRoof = (int) (distanceBetweenIngotsAndRoof + 2 * brickIngot
				.height());
		int distanceBetweenTwoRowsOfCash = (int) (4 * brickCash.height() + 2 * brickIngot
				.height());

		for (int i = 1; i <= WIDTH - 1 - brickIngot.width(); i += brickIngot
				.width()) {
			for (int j = distanceBetweenIngotsAndRoof; j < 3
					* distanceBetweenTwoRowsOfIngots
					+ distanceBetweenIngotsAndRoof; j += distanceBetweenTwoRowsOfIngots) {
				for (int k = 0; k < 2 * brickIngot.height(); k += brickIngot
						.height()) {
					bricks.add(new Brick(brickIngot));
					pos.add(new Point(i, k + j));
				}
			}
		}

		for (int i = 1; i <= WIDTH - 1 - brickCash.width(); i += brickCash
				.width()) {
			for (int j = distanceBetweenCashAndRoof; j < 2
					* distanceBetweenTwoRowsOfCash + distanceBetweenCashAndRoof; j += distanceBetweenTwoRowsOfCash) {
				for (int k = 0; k < 4 * brickCash.height(); k += brickCash
						.height()) {
					bricks.add(new Brick(brickCash));
					pos.add(new Point(i, k + j));
				}
			}
		}

		for (int i = 0; i < bricks.size(); i++) {
			bricks.get(i).setPos(pos.get(i));
		}
		return map;
	}

	// Star Wars Theme
	public static Map Map_6() {
		Map map = new Map();

		ArrayList<Brick> bricks = map.bricks;
		ArrayList<Point> pos = new ArrayList<Point>();

		map.spriteBackground = Sprite.SW_BACKGROUND;
		map.spriteBall = Sprite.SW_BALL;
		map.spritePaddle = Sprite.SW_PADDLE;

		Brick brick = new Brick(2, 2, 2);
		brick.sprite = Sprite.SW_DROID;

		int rowsPerType = (int) (2 * brick.height());
		int distanceBetweenTypes = (int) (brick.height());
		int startY = 0;
		int endY = startY + rowsPerType;

		for (float i = 0; i <= WIDTH - brick.width(); i += brick.width()) {
			for (float j = startY; j < endY; j += brick.height()) {
				bricks.add(new Brick(brick));
				pos.add(new Point(i, j));
			}
		}

		brick.sprite = Sprite.SW_IMPERIAL;

		startY = (int) (endY + distanceBetweenTypes);
		endY = startY + rowsPerType;

		for (float i = 0; i <= WIDTH - brick.width(); i += brick.width()) {
			for (float j = startY; j < endY; j += brick.height()) {
				bricks.add(new Brick(brick));
				pos.add(new Point(i, j));
			}
		}

		brick.resistance++;
		brick.sprite = Sprite.SW_CSI;

		startY = (int) (endY + distanceBetweenTypes);
		endY = startY + rowsPerType;

		for (float i = 0; i <= WIDTH - brick.width(); i += brick.width()) {
			for (float j = startY; j < endY; j += brick.height()) {
				bricks.add(new Brick(brick));
				pos.add(new Point(i, j));
			}
		}

		for (int i = 0; i < bricks.size(); i++) {
			bricks.get(i).setPos(pos.get(i));
		}
		return map;
	}

	public static Map Map_7() {
		Map map = new Map();

		return map;
	}

	public static Map Map_8() {
		Map map = new Map();

		return map;
	}

	public static Map Map_9() {
		Map map = new Map();

		return map;
	}

	public static Map Map_10() {
		Map map = new Map();

		return map;
	}

	public float height() {
		return height;
	}

	public float width() {
		return width;
	}

	public ArrayList<Brick> bricks() {
		return bricks;
	}

}
