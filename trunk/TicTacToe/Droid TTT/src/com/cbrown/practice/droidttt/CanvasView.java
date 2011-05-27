package com.cbrown.practice.droidttt;

import java.io.IOException;
import java.io.InputStream;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

	private int[] mGameBoardCells;
	private Bitmap mBackgroundBitmap;
	private Bitmap mPlayerOBitmap;
	private Bitmap mPlayerXBitmap;

	private static BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
	private int mPixelFactor;
	
	public CanvasView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CanvasView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void setmBackgroundBitmap(Bitmap mBackgroundBitmap) {
		this.mBackgroundBitmap = mBackgroundBitmap;
	}

	public Bitmap getmBackgroundBitmap() {
		return mBackgroundBitmap;
	}

	public void setmPlayerOBitmap(Bitmap mPlayerOBitmap) {
		this.mPlayerOBitmap = mPlayerOBitmap;
	}

	public Bitmap getmPlayerOBitmap() {
		return mPlayerOBitmap;
	}

	public void setmPlayerXBitmap(Bitmap mPlayerXBitmap) {
		this.mPlayerXBitmap = mPlayerXBitmap;
	}

	public Bitmap getmPlayerXBitmap() {
		return mPlayerXBitmap;
	}

	public void setmGameBoardCells(int[] mGameBoardCells) {
		this.mGameBoardCells = mGameBoardCells;
	}

	public int[] getmGameBoardCells() {
		return mGameBoardCells;
	}

	public void setPixelFactor(int pixelFactor) {
		this.mPixelFactor = pixelFactor;
	}

	public int getPixelFactor() {
		return mPixelFactor;
	}

	@Override
	public void onDraw(Canvas canvas) {
		//super.onDraw(canvas);
		Rect gridScale = new Rect(0, 0, mPixelFactor * 3, mPixelFactor * 3);
		int playerScaledWidthHeight = mPixelFactor / 4 * 3;
		// Draw the background
		//canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
		//canvas.drawColor(Color.BLUE);
		
		// Draw the Background to scale, by multiplying the pixelFactor by 3 you get the height/width of the square grid
		canvas.drawBitmap(mBackgroundBitmap, null, gridScale, null);
		
//	Line paint test		
//		Paint linePaint = new Paint();
//		linePaint.setColor(Color.GREEN);
//		canvas.drawLine(0, 0, 480, 480, linePaint);
		
		// Cycle through the TicTacToe board and draw any X's or O's as necessary
		// Todo: set the padding as a variable
		// 		 set the pixelFactor as a variable
		for (int x = 0; x < mGameBoardCells.length; x++) {
			Bitmap tempBitmap = null;
			int xTranslatedToScreenCoordinate = x % 3 * mPixelFactor + mPixelFactor / 8;
			int yTranslatedToScreenCoordinate = x / 3 * mPixelFactor + mPixelFactor / 8;
			if (mGameBoardCells[x] == TicTacToeBoard.CellStatus.PLAYER_O)
				tempBitmap = mPlayerOBitmap;
			else if (mGameBoardCells[x] == TicTacToeBoard.CellStatus.PLAYER_X)
				tempBitmap = mPlayerXBitmap;
			if (tempBitmap != null) 
				canvas.drawBitmap(tempBitmap, null, new Rect(xTranslatedToScreenCoordinate,
						yTranslatedToScreenCoordinate, xTranslatedToScreenCoordinate + playerScaledWidthHeight, yTranslatedToScreenCoordinate + playerScaledWidthHeight), null);
		}
	}

	protected Bitmap loadBitmap(Context context, int resourceId) {
		Bitmap bitmap = null;
		if (context != null) {

			InputStream is = context.getResources().openRawResource(resourceId);
			try {
				bitmap = BitmapFactory.decodeStream(is, null, sBitmapOptions);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					// Ignore.
				}
			}
		}

		return bitmap;
	}

}
