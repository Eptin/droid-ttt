package com.cbrown.practice.droidttt;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class DroidTTT extends Activity {

	CanvasView canvasView;
	TicTacToeBoard ticTacToeBoard;
	DisplayMetrics dm;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_layout);

		canvasView = (CanvasView) findViewById(R.id.canvasView);
		canvasView.setOnTouchListener(mTouchListener);

		ticTacToeBoard = new TicTacToeBoard();
		
		// Check to see if there was a saved state and recover if so, otherwise start a new game
		if (savedInstanceState != null) {
			ticTacToeBoard.setmGameBoardCells(savedInstanceState.getIntArray("gameBoardCells"));
			ticTacToeBoard.setmCurrentPlayer(savedInstanceState.getInt("currentPlayer"));
		}
		else ticTacToeBoard.resetBoard();
		
		// Load the background, player X and player O bitmaps
		ticTacToeBoard.setmBoardBitmap(canvasView.loadBitmap(this,
				R.drawable.game_grid));
		canvasView.setmBackgroundBitmap(ticTacToeBoard.getmBoardBitmap());

		ticTacToeBoard.setmPlayerOBitmap(canvasView.loadBitmap(this,
				R.drawable.player_o));
		canvasView.setmPlayerOBitmap(ticTacToeBoard.getmPlayerOBitmap());

		ticTacToeBoard.setmPlayerXBitmap(canvasView.loadBitmap(this,
				R.drawable.player_x));
		canvasView.setmPlayerXBitmap(ticTacToeBoard.getmPlayerXBitmap());

		canvasView.setmGameBoardCells(ticTacToeBoard.getmGameBoardCells());
		
		// Draw the board
		canvasView.invalidate();

		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int pixelFactor = (dm.widthPixels < dm.heightPixels? dm.widthPixels : dm.heightPixels) / 3;
		canvasView.setPixelFactor(pixelFactor);
		
//		Toast.makeText(this,"PixelFactor: " + pixelFactor, Toast.LENGTH_LONG).show();
		

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putIntArray("gameBoardCells", ticTacToeBoard.getmGameBoardCells());
		outState.putInt("currentPlayer", ticTacToeBoard.getmCurrentPlayer());
	}

	View.OnTouchListener mTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int rawX = (int) event.getRawX();
			int rawY = (int) event.getRawY();
			int pixelFactor = canvasView.getPixelFactor();
			int xTranslated = rawX / pixelFactor;
			int yTranslated = rawY / pixelFactor * 3;

			int currentGameStatus = ticTacToeBoard
					.getGameStatusAndCheckForWinner();
			int gameBoardCell = xTranslated + yTranslated;

			// Toast.makeText(v.getContext(), "X: " +
			// rawX + " Y: " + rawY, Toast.LENGTH_LONG).show();
			// Toast.makeText(v.getContext(),
			// "Translated X: " + translatedX +
			// "\nTranslated Y: " + translatedY +
			// "\nRawX: " + rawX +
			// "\nRawY: " + rawY +
			// "\nBameboard Contrib from X: " + (int) rawX / 160 +
			// "\nBameboard Contrib from Y: " + (int) rawY / 160 * 3 +
			// "\nGameboard cell: " + gameBoardCell, Toast.LENGTH_LONG).show();

			
			/* PRE TURN CHECK
			 * If the game is still in play and the move 
			 * was not out of bounds, consume a turn.
			 * Otherwise reset the board and redraw
			 */
			switch (currentGameStatus) {
				case TicTacToeBoard.GameStatus.GAME_IN_PLAY:
					// Out of bounds check
					if (xTranslated < 3 && yTranslated <= 6) {
						ticTacToeBoard.consumeTurn(gameBoardCell);
						currentGameStatus = ticTacToeBoard
								.getGameStatusAndCheckForWinner();
					}
						break;
				case TicTacToeBoard.GameStatus.GAME_OVER_TIE:
				case TicTacToeBoard.GameStatus.PLAYER_O_WINS:
				case TicTacToeBoard.GameStatus.PLAYER_X_WINS:
				default:
					ticTacToeBoard.resetBoard();
					canvasView.invalidate();
					return false;
			}

			/* POST TURN CHECK
			 * If the game is over, display the message
			 */
			switch (currentGameStatus) {
				case TicTacToeBoard.GameStatus.PLAYER_O_WINS:
					Toast.makeText(v.getContext(), "Player O Wins!",
							Toast.LENGTH_SHORT).show();
					break;
				case TicTacToeBoard.GameStatus.PLAYER_X_WINS:
					Toast.makeText(v.getContext(), "Player X Wins!",
							Toast.LENGTH_SHORT).show();
					break;
				case TicTacToeBoard.GameStatus.GAME_OVER_TIE:
					Toast.makeText(v.getContext(), "It was a Tie!",
							Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
			}
			
			canvasView.invalidate();
			return false;
		}
	};
}