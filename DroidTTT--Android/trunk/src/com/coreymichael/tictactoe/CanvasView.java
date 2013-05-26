package com.coreymichael.tictactoe;

import java.io.IOException;
import java.io.InputStream;

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
	private int[][] mGameBoardCells;
	private int mNumCellsPerRow;
	
	private Bitmap mBackgroundBitmap;
	private Bitmap mPlayerOBitmap;
	private Bitmap mPlayerXBitmap;

	private static BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
	private int mCellPixelSize;
	
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

	public void setmGameBoardCells(int[][] mGameBoardCells) {
		this.mGameBoardCells = mGameBoardCells;
	}

	public int[][] getmGameBoardCells() {
		return mGameBoardCells;
	}

	public void setNumCellsPerRow(int mNumCellsPerRow) {
		this.mNumCellsPerRow = mNumCellsPerRow;
	}

	public int getNumCellsPerRow() {
		return mNumCellsPerRow;
	}

	public void setCellPixelSize(int cellPixelSize) {
		this.mCellPixelSize = cellPixelSize;
	}

	public int getCellPixelSize() {
		return mCellPixelSize;
	}

	@Override
	public void onDraw(Canvas canvas) {
		//super.onDraw(canvas);
		//Rect gridScale = new Rect(0, 0, mCellPixelSize * mNumCellsPerRow, mCellPixelSize * mNumCellsPerRow);
		//Rect gridScale = new Rect(0, 0, 800, 480);
		
		int playerScaledWidthHeight = mCellPixelSize / 4 * 3;
		// Draw the background
		//canvas.drawBitmap(mBackgroundBitmap, 0, 0, null);
		//canvas.drawColor(Color.WHITE);
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.WHITE);
		canvas.drawRect(new Rect(0, 0, mCellPixelSize * getNumCellsPerRow(), mCellPixelSize * getNumCellsPerRow()), bgPaint);
		// Draw the Background to scale, by multiplying the cellPixelSize by 3 you get the height/width of the square grid
		//canvas.drawBitmap(mBackgroundBitmap, null, gridScale, null);

		// Line paint test		
		Paint linePaint = new Paint();
		linePaint.setColor(Color.BLACK);
		for (int x = 0; x < mNumCellsPerRow; x++) {
			canvas.drawLine(x * mCellPixelSize + mCellPixelSize, 0,  x * mCellPixelSize + mCellPixelSize, mCellPixelSize * mNumCellsPerRow, linePaint);
			canvas.drawLine(0, x * mCellPixelSize + mCellPixelSize, mCellPixelSize * mNumCellsPerRow, x * mCellPixelSize + mCellPixelSize, linePaint);
		}
		
		
		// Cycle through the TicTacToe board and draw any X's or O's as necessary
		// Todo: set the padding as a variable
		// 		 set the cellPixelSize as a variable
		//mGameBoardCells[row][col]
		for (int row = 0; row < mGameBoardCells.length; row++) { // Iterates over the rows
			for (int col = 0; col < mGameBoardCells[0].length; col++) { // Iterates over the columns
				Bitmap tempBitmap = null;
				int padding = mCellPixelSize / 8;	// If each cell were 80 pixels across, this would give 1/8th of a cell on either size.
													// That would be 10 pixels padding, then 60 pixels for the image, then 10 pixels padding.
				int xTranslatedToScreenCoordinate = (col * mCellPixelSize) + padding;
				int yTranslatedToScreenCoordinate = (row * mCellPixelSize) + padding;
				if (mGameBoardCells[row][col] == TicTacToeBoard.CellStatus.PLAYER_O)
					tempBitmap = mPlayerOBitmap;
				else if (mGameBoardCells[row][col] == TicTacToeBoard.CellStatus.PLAYER_X)
					tempBitmap = mPlayerXBitmap;
				if (tempBitmap != null) //then we draw the X or O bitmap on the appropriate cell
					canvas.drawBitmap(tempBitmap, null, new Rect(xTranslatedToScreenCoordinate, yTranslatedToScreenCoordinate, xTranslatedToScreenCoordinate + playerScaledWidthHeight, yTranslatedToScreenCoordinate + playerScaledWidthHeight), null);
			}
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