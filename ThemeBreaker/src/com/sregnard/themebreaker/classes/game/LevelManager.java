package com.sregnard.themebreaker.classes.game;

import java.util.ArrayList;

// Liste tous les niveaux créés

public class LevelManager {

	// 0 pour accès à la TestingMap
	public static final int minLevel = 1;

	public static ArrayList<Level> listLevel = new ArrayList<Level>();

	public static void add(Level level) {
		listLevel.add(level);
	}

	public static String getString(int numLevel) {
		for (Level l : listLevel)
			if (l.number == numLevel)
				return l.toString();
		return "Erreur, le niveau " + numLevel + " n'existe pas";
	}

	public static Level getLevel(int numLevel) {
		for (Level l : listLevel)
			if (l.number == numLevel)
				return l;
		return null;
	}

	public static Level getLevel(String s) {
		for (Level l : listLevel)
			if (l.toString().equals(s))
				return l;
		return null;
	}

	public static int getNumberOfLevel() {
		return listLevel.size() - 1;
	}
}
