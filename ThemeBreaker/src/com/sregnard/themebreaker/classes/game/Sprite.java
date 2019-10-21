package com.sregnard.themebreaker.classes.game;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sregnard.themebreaker.R;

public class Sprite {

	static Resources res;
	static ArrayList<Sprite> list = new ArrayList<Sprite>();

	// Default Theme
	public static Sprite DEFAULT_BACKGROUND, DEFAULT_BALL, DEFAULT_PADDLE;
	public static ArrayList<Sprite> DEFAULT_BRICKS;

	// Neon Theme
	public static Sprite NEON_BACKGROUND, NEON_BALL, NEON_PADDLE;
	public static ArrayList<Sprite> NEON_BRICKS_1, NEON_BRICKS_2,
			NEON_BRICKS_4;

	// Cinema Theme
	public static Sprite CINEMA_BACKGROUND, CINEMA_BALL, CINEMA_PADDLE;
	public static ArrayList<Sprite> CINEMA_BRICKS;

	// Mario Theme
	public static Sprite MARIO_BACKGROUND, MARIO_BALL, MARIO_PADDLE;
	public static ArrayList<Sprite> MARIO_BRICKS;

	// Pokemon Theme
	public static Sprite POKEMON_BACKGROUND, POKEMON_BALL, POKEMON_PADDLE;
	public static ArrayList<Sprite> POKEMON_WATER, POKEMON_NEUTRAL,
			POKEMON_PIKACHU, POKEMON_QULBUTOKE;

	// Bank Theme
	public static Sprite BANK_BACKGROUND, BANK_BALL, BANK_PADDLE;
	public static ArrayList<Sprite> BANK_BRICKS;

	// Star Wars Theme
	public static Sprite SW_BACKGROUND, SW_BALL, SW_PADDLE;
	public static ArrayList<Sprite> SW_CSI, SW_IMPERIAL, SW_DROID;

	static {

		// Neon Theme
		NEON_BACKGROUND = new Sprite(R.drawable.neon_background);
		NEON_BALL = new Sprite(R.drawable.neon_ball);
		NEON_PADDLE = new Sprite(R.drawable.neon_paddle);
		NEON_BRICKS_1 = new ArrayList<Sprite>();
		NEON_BRICKS_1.add(new Sprite(R.drawable.neon_brick_red_1));
		NEON_BRICKS_1.add(new Sprite(R.drawable.neon_brick_blue_1));
		NEON_BRICKS_1.add(new Sprite(R.drawable.neon_brick_green_1));
		NEON_BRICKS_1.add(new Sprite(R.drawable.neon_brick_yellow_1));
		NEON_BRICKS_1.add(new Sprite(R.drawable.neon_brick_purple_1));
		NEON_BRICKS_2 = new ArrayList<Sprite>();
		NEON_BRICKS_2.add(new Sprite(R.drawable.neon_brick_red_2));
		NEON_BRICKS_2.add(new Sprite(R.drawable.neon_brick_blue_2));
		NEON_BRICKS_2.add(new Sprite(R.drawable.neon_brick_green_2));
		NEON_BRICKS_2.add(new Sprite(R.drawable.neon_brick_yellow_2));
		NEON_BRICKS_2.add(new Sprite(R.drawable.neon_brick_purple_2));
		NEON_BRICKS_4 = new ArrayList<Sprite>();
		NEON_BRICKS_4.add(new Sprite(R.drawable.neon_brick_red_4));
		NEON_BRICKS_4.add(new Sprite(R.drawable.neon_brick_blue_4));
		NEON_BRICKS_4.add(new Sprite(R.drawable.neon_brick_green_4));
		NEON_BRICKS_4.add(new Sprite(R.drawable.neon_brick_yellow_4));
		NEON_BRICKS_4.add(new Sprite(R.drawable.neon_brick_purple_4));

		// Cinema Theme
		CINEMA_BACKGROUND = new Sprite(R.drawable.cinema_background);
		CINEMA_BALL = new Sprite(R.drawable.cinema_ball);
		CINEMA_PADDLE = new Sprite(R.drawable.cinema_paddle);
		CINEMA_BRICKS = new ArrayList<Sprite>();
		CINEMA_BRICKS.add(new Sprite(R.drawable.cinema_brick));
		CINEMA_BRICKS.add(new Sprite(R.drawable.cinema_brick_2));
		CINEMA_BRICKS.add(new Sprite(R.drawable.cinema_brick_3));

		// Mario Theme
		MARIO_BACKGROUND = new Sprite(R.drawable.mario_background);
		MARIO_BALL = new Sprite(R.drawable.mario_ball);
		MARIO_PADDLE = new Sprite(R.drawable.mario_paddle);
		MARIO_BRICKS = new ArrayList<Sprite>();
		MARIO_BRICKS.add(new Sprite(R.drawable.mario_brick));
		MARIO_BRICKS.add(new Sprite(R.drawable.mario_brick_2));

		// Pokemon Theme
		POKEMON_BACKGROUND = new Sprite(R.drawable.pokemon_background);
		POKEMON_BALL = new Sprite(R.drawable.pokemon_ball);
		POKEMON_PADDLE = new Sprite(R.drawable.pokemon_paddle);
		POKEMON_WATER = new ArrayList<Sprite>();
		POKEMON_WATER.add(new Sprite(R.drawable.pokemon_brick_magicarpe));
		POKEMON_WATER.add(new Sprite(R.drawable.pokemon_brick_ramoloss));
		POKEMON_NEUTRAL = new ArrayList<Sprite>();
		POKEMON_NEUTRAL.add(new Sprite(R.drawable.pokemon_brick_excelangue));
		POKEMON_NEUTRAL.add(new Sprite(R.drawable.pokemon_brick_ronflex));
		POKEMON_PIKACHU = new ArrayList<Sprite>();
		POKEMON_PIKACHU.add(new Sprite(R.drawable.pokemon_brick_pikachu));
		POKEMON_QULBUTOKE = new ArrayList<Sprite>();
		POKEMON_QULBUTOKE.add(new Sprite(R.drawable.pokemon_brick_qulbutoke));

		// Bank Theme
		BANK_BACKGROUND = new Sprite(R.drawable.bank_background);
		BANK_BALL = new Sprite(R.drawable.bank_ball);
		BANK_PADDLE = new Sprite(R.drawable.bank_paddle);
		BANK_BRICKS = new ArrayList<Sprite>();
		BANK_BRICKS.add(new Sprite(R.drawable.bank_brick_cash));
		BANK_BRICKS.add(new Sprite(R.drawable.bank_brick_ingot));

		// Star Wars
		SW_BACKGROUND = new Sprite(R.drawable.sw_background);
		SW_BALL = new Sprite(R.drawable.sw_ball);
		SW_PADDLE = new Sprite(R.drawable.sw_paddle);
		SW_CSI = new ArrayList<Sprite>();
		SW_CSI.add(new Sprite(R.drawable.sw_brick_csi_combat));
		SW_CSI.add(new Sprite(R.drawable.sw_brick_csi_super));
		SW_CSI.add(new Sprite(R.drawable.sw_brick_csi_droideka));
		SW_IMPERIAL = new ArrayList<Sprite>();
		SW_IMPERIAL.add(new Sprite(R.drawable.sw_brick_imperial_stormtrooper));
		SW_IMPERIAL.add(new Sprite(R.drawable.sw_brick_imperial_scout));
		SW_DROID = new ArrayList<Sprite>();
		SW_DROID.add(new Sprite(R.drawable.sw_brick_droid_c3po));
		SW_DROID.add(new Sprite(R.drawable.sw_brick_droid_r2d2));

		// Default Theme
		DEFAULT_BACKGROUND = SW_BACKGROUND;
		DEFAULT_BALL = SW_BALL;
		DEFAULT_PADDLE = SW_PADDLE;
		DEFAULT_BRICKS = SW_DROID;
	}

	public int resId;
	public Bitmap bitmap;

	public Sprite(int resId) {
		this.resId = resId;
		this.bitmap = null;
		list.add(this);
	}

	public int getID() {
		return resId;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void createBitmap(float width, float height) {
		if (bitmap != null)
			return;
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, width, height);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeResource(res, resId);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			float width2, float height2) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > height2 || width > width2) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > height2
					&& (halfWidth / inSampleSize) > width2) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public void destroyBitmap() {
		bitmap = null;
	}

	public static void init(Activity owner) {
		Sprite.res = owner.getResources();
	}

	public static void release() {
		unload();
	}

	private static void unload() {
		for (Sprite sprite : list)
			sprite.destroyBitmap();
		;
	}
}
