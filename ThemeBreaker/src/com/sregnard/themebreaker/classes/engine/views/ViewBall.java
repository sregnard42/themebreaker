package com.sregnard.themebreaker.classes.engine.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.sregnard.themebreaker.classes.brickbreaker.Ball;
import com.sregnard.themebreaker.classes.geometry.Point;

public class ViewBall extends View {

	Paint paint;
	Point posEntity;
	float cellSize;

	Ball ball;
	RectF dst;
	float width, height;

	Bitmap bitmap;

	public ViewBall(Activity owner, Ball ball, float cellSize) {
		super(owner);
		this.ball = ball;
		this.cellSize = cellSize;

		// Pinceau
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);

		posEntity = new Point();
		dst = new RectF();

		width = ball.diameter() * cellSize;
		height = ball.diameter() * cellSize;
		ball.sprite.createBitmap(width, height);

		bitmap = ball.sprite.getBitmap();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Position
		posEntity.setX(ball.getPos().getX() * cellSize);
		posEntity.setY(ball.getPos().getY() * cellSize);

		// Rectangle à remplir par l'image
		dst.set(posEntity.getX(), posEntity.getY(), posEntity.getX() + width,
				posEntity.getY() + height);
		drawBall(canvas);
	}

	protected void drawBall(Canvas canvas) {
		// canvas.drawCircle(posEntity.getX() + ball.radius() * cellSize,
		// posEntity.getY() + ball.radius() * cellSize, ball.radius()
		// * cellSize, paint);
		canvas.drawBitmap(bitmap, null, dst, paint);
	}

}
