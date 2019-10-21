package com.sregnard.themebreaker.classes.engine.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.sregnard.themebreaker.classes.brickbreaker.Paddle;
import com.sregnard.themebreaker.classes.geometry.Point;

public class ViewPaddle extends View {

	public Paint paint;
	public Point posEntity;
	public float cellSize;

	public Paddle paddle;
	RectF dst;
	float width, height;

	Bitmap bitmap;

	public ViewPaddle(Activity owner, Paddle paddle, float cellSize) {
		super(owner);
		this.paddle = paddle;
		this.cellSize = cellSize;

		// Pinceau
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);

		posEntity = new Point();
		dst = new RectF();

		width = paddle.width() * cellSize;
		height = paddle.height() * cellSize;
		paddle.sprite.createBitmap(width, height);

		bitmap = paddle.sprite.getBitmap();
	}

	@Override
	public boolean performClick() {
		super.performClick();
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Position
		posEntity.setX(paddle.getPos().getX() * cellSize);
		posEntity.setY(paddle.getPos().getY() * cellSize);

		// Rectangle à remplir par l'image
		dst.set(posEntity.getX(), posEntity.getY(), posEntity.getX() + width,
				posEntity.getY() + height);
		drawPaddle(canvas);
	}

	public void drawPaddle(Canvas canvas) {
		// canvas.drawRect(dst, paint);
		canvas.drawBitmap(bitmap, null, dst, paint);
	}
}
