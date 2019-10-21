package com.sregnard.themebreaker.classes.game;

public class Level {

	public static final Level LVL_0, LVL_1, LVL_2, LVL_3, LVL_4, LVL_5, LVL_6;

	static {
		LVL_0 = new Level(0, "TestingMap");
		LVL_1 = new Level(1, "Neon");
		LVL_2 = new Level(2, "Cinema");
		LVL_3 = new Level(3, "Mario");
		LVL_4 = new Level(4, "Pokemon");
		LVL_5 = new Level(5, "Bank");
		LVL_6 = new Level(6, "Star Wars");
	}

	int number;
	String desc;
	Map map;

	public Level(int number, String desc) {
		this.number = number;
		this.desc = desc;
		this.map = null;
		LevelManager.add(this);
	}

	public static void init() {
	};

	public String toString() {
		String s = "";
		s += "Niveau " + number;
		if (!desc.isEmpty()) {
			s += " : " + desc;
		}
		return s;
	}

	public int number() {
		return number;
	}

	public Map getMap() {
		return map;
	}

	public String getDesc() {
		return desc;
	}

	public void generateMap() {
		switch (number) {
		case (0):
			// Default Theme
			map = Map.Map_0();
			break;
		case (1):
			// Neon Theme
			map = Map.Map_1();
			break;
		case (2):
			// Cinema Theme
			map = Map.Map_2();
			break;
		case (3):
			// Mario Theme
			map = Map.Map_3();
			break;
		case (4):
			// Pokemon Theme
			map = Map.Map_4();
			break;
		case (5):
			// Bank Theme
			map = Map.Map_5();
			break;
		case (6):
			// Star Wars Theme
			map = Map.Map_6();
			break;
		case (7):
			map = Map.Map_7();
			break;
		case (8):
			map = Map.Map_8();
			break;
		case (9):
			map = Map.Map_9();
			break;
		case (10):
			map = Map.Map_10();
			break;
		}
	}
}
