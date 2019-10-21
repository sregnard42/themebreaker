package com.sregnard.themebreaker.classes.engine.views;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.sregnard.themebreaker.classes.brickbreaker.Brick;
import com.sregnard.themebreaker.classes.game.Sprite;
import com.sregnard.themebreaker.classes.geometry.Point;

public class ViewBrick extends View {

	public Paint paint;

	public Brick brick;
	int type;
	RectF dst;
	float width, height;

	ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();

	public ViewBrick(Activity owner, Brick brick, float cellSize) {
		super(owner);
		this.brick = brick;

		// Pinceau
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);

		// Position
		Point posEntity = new Point();
		posEntity.setX(brick.getPos().getX() * cellSize);
		posEntity.setY(brick.getPos().getY() * cellSize);

		width = brick.width() * cellSize;
		height = brick.height() * cellSize;

		// Rectangle à remplir par l'image
		dst = new RectF(posEntity.getX(), posEntity.getY(), posEntity.getX()
				+ width, posEntity.getY() + height);

		for (Sprite sprite : brick.sprite) {
			sprite.createBitmap(width, height);
			bitmap.add(sprite.getBitmap());
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Type modèle
		type = brick.resistance - 1;
		if (type > bitmap.size() - 1)
			type = bitmap.size() - 1;
		drawBrick(canvas);
	}

	protected void drawBrick(Canvas canvas) {
		if (bitmap.size() > 0)
			canvas.drawBitmap(bitmap.get(type), null, dst, paint);
		else
			canvas.drawRect(dst, paint);
	}

}
