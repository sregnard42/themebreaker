package com.sregnard.themebreaker.classes.game;

public class Difficulty {

	public static final Difficulty DIFFICULTY_VERY_EASY, DIFFICULTY_EASY, DIFFICULTY_MEDIUM,
			DIFFICULTY_HARD, DIFFICULTY_VERY_HARD, DIFFICULTY_INSANE;

	static {
		DIFFICULTY_VERY_EASY = new Difficulty(1, "Très facile");
		DIFFICULTY_EASY = new Difficulty(2, "Facile");
		DIFFICULTY_MEDIUM = new Difficulty(3, "Normal");
		DIFFICULTY_HARD = new Difficulty(4, "Difficile");
		DIFFICULTY_VERY_HARD = new Difficulty(5, "Très difficile");
		DIFFICULTY_INSANE = new Difficulty(6, "Dément");
	}

	int id;
	String name;

	private Difficulty(int id, String name) {
		this.id = id;
		this.name = name;
		DifficultyManager.add(this);
	}

	public static void init() {
	};

	public String toString() {
		return name;
	}

	public int getID() {
		return id;
	}
}
