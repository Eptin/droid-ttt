package com.cbrown.practice.droidttt;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CanvasSprite extends Renderable {
	private Bitmap mBitmap;

	public CanvasSprite(Bitmap bitmap) {
		mBitmap = bitmap;
	}

	public void draw(Canvas canvas) {
		// The Canvas system uses a screen-space coordinate system, that is,
		// 0,0 is the top-left point of the canvas. But in order to align
		// with OpenGL's coordinate space (which places 0,0 and the lower-left),
		// for this test I flip the y coordinate.
		canvas.drawBitmap(mBitmap, x, canvas.getHeight() - (y + height), null);
	}
}
