package com.cbrown.practice.droidttt;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

	private int[] mGameBoardCells;
	private Bitmap mBackgroundBitmap;
	private Bitmap mPlayerOBitmap;
	private Bitmap mPlayerXBitmap;

	private static BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

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

	public void loadBitmaps() {
		sBitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;

		// setFocusable(true);
		Resources r = this.getContext().getResources();

		Drawable backgroundTile = r.getDrawable(R.drawable.new_grid);
		Bitmap backgroundBitmap = Bitmap.createBitmap(480, 800,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(backgroundBitmap);
		backgroundTile.setBounds(0, 0, 480, 800);
		backgroundTile.draw(canvas);
		setmBackgroundBitmap(backgroundBitmap);

		Drawable playerOTile = r.getDrawable(R.drawable.android);
		Bitmap playerOBitmap = Bitmap.createBitmap(120, 120,
				Bitmap.Config.ARGB_8888);
		Canvas playerOCanvas = new Canvas(playerOBitmap);
		playerOTile.setBounds(0, 0, 120, 120);
		playerOTile.draw(playerOCanvas);
		setmPlayerOBitmap(playerOBitmap);

	}

	@Override
	public void onDraw(Canvas canvas) {
		// super.onDraw(canvas);

		// // Draw the background
		canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);

		for (int x = 0; x < mGameBoardCells.length; x++) {
			if (mGameBoardCells[x] == TicTacToeBoard.CellStatus.PLAYER_O)
				canvas.drawBitmap(mPlayerOBitmap, (x % 3 * 160 + 20),
						(x / 3 * 160 + 20), null);
			else if (mGameBoardCells[x] == TicTacToeBoard.CellStatus.PLAYER_X)
				canvas.drawBitmap(mPlayerXBitmap, (x % 3 * 160 + 20),
						(x / 3 * 160 + 20), null);
		}

		// // Draw player O
		// canvas.drawBitmap(playerO, 20, 20, null);
		// canvas.drawBitmap(playerO, 180, 180, null);
		// canvas.drawBitmap(playerO, 340, 340, null);
		//
		// canvas.drawBitmap(playerX, 340, 20, null);
		//
		// //this.invalidate();
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
