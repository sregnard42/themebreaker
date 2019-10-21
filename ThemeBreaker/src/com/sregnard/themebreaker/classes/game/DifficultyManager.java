package com.sregnard.themebreaker.classes.game;

import java.util.ArrayList;

// Liste toutes les difficultés créées

public class DifficultyManager {

	private static ArrayList<Difficulty> listDifficulty = new ArrayList<Difficulty>();

	public static void add(Difficulty difficulty) {
		listDifficulty.add(difficulty);
	}

	public static String getString(int idDifficulty) {
		for (Difficulty d : listDifficulty)
			if (d.id == idDifficulty)
				return d.name;
		return "Erreur, aucune difficulté n'a pour id : " + idDifficulty;
	}

	public static int getId(String nameDifficulty) {
		for (Difficulty d : listDifficulty)
			if (d.name.equals(nameDifficulty))
				return d.id;
		return -1;
	}

	public static Difficulty getDifficulty(int idDifficulty) {
		for (Difficulty d : listDifficulty)
			if (d.id == idDifficulty)
				return d;
		return null;
	}

	public static Difficulty getDifficulty(String nameDifficulty) {
		for (Difficulty d : listDifficulty)
			if (d.name.equals(nameDifficulty))
				return d;
		return null;
	}

	public static Difficulty maximumDifficulty() {
		int max = -1;
		for (Difficulty d : listDifficulty)
			if (d.id > max)
				max = d.id;
		return getDifficulty(max);
	}
}
