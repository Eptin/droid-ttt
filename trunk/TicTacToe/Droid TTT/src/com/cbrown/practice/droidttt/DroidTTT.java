package com.cbrown.practice.droidttt;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class DroidTTT extends Activity {

	CanvasView canvasView;
	TicTacToeBoard ticTacToeBoard;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_layout);

		setContentView(R.layout.my_layout);

		canvasView = (CanvasView) findViewById(R.id.cavasView);
		canvasView.setOnTouchListener(mTouchListener);

		ticTacToeBoard = new TicTacToeBoard();

		ticTacToeBoard.resetBoard();

		ticTacToeBoard.setmBoardBitmap(canvasView.loadBitmap(this,
				R.drawable.new_grid));
		canvasView.setmBackgroundBitmap(ticTacToeBoard.getmBoardBitmap());

		ticTacToeBoard.setmPlayerOBitmap(canvasView.loadBitmap(this,
				R.drawable.android));
		canvasView.setmPlayerOBitmap(ticTacToeBoard.getmPlayerOBitmap());

		ticTacToeBoard.setmPlayerXBitmap(canvasView.loadBitmap(this,
				R.drawable.android2));
		canvasView.setmPlayerXBitmap(ticTacToeBoard.getmPlayerXBitmap());

		canvasView.setmGameBoardCells(ticTacToeBoard.getmGameBoardCells());
		canvasView.invalidate();

		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		//
		// Toast.makeText(this,
		// "Width: " + dm.widthPixels + "\n" +
		// "Height: " + dm.heightPixels + "\n", Toast.LENGTH_LONG).show();
		//

	}

	View.OnTouchListener mTouchListener = new OnTouchListener() {
//		@Override
		public boolean onTouch(View v, MotionEvent event) {
			float rawX = event.getRawX();
			float rawY = event.getRawY();

			// int translatedX = (int) rawX / 160 * 160;
			// int translatedY = (int) rawY / 160 * 160;

			int currentGameStatus = ticTacToeBoard
					.getGameStatusAndCheckForWinner();
			int gameBoardCell = (int) rawX / 160 + (int) rawY / 160 * 3;

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

			// Pre-turn Check
			// if (currentGameStatus == TicTacToeBoard.GameStatus.GAME_IN_PLAY)
			// if (gameBoardCell < 9) {
			// ticTacToeBoard.consumeTurn(gameBoardCell);
			// //canvasView.invalidate();
			// currentGameStatus =
			// ticTacToeBoard.getGameStatusAndCheckForWinner();
			// }

			switch (currentGameStatus) {
			case TicTacToeBoard.GameStatus.GAME_IN_PLAY:
				if (gameBoardCell < 9) {
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

			// If the game is over, display a message and reset the game
			switch (currentGameStatus) {
			case TicTacToeBoard.GameStatus.PLAYER_O_WINS:
				Toast.makeText(v.getContext(), "Player O Wins!",
						Toast.LENGTH_LONG).show();
				break;
			case TicTacToeBoard.GameStatus.PLAYER_X_WINS:
				Toast.makeText(v.getContext(), "Player X Wins!",
						Toast.LENGTH_LONG).show();
				break;
			case TicTacToeBoard.GameStatus.GAME_OVER_TIE:
				Toast.makeText(v.getContext(), "It was a Tie!",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;

			}

			canvasView.invalidate();
			return false;
		}
	};
}