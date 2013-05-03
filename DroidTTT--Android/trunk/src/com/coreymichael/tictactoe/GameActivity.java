package com.coreymichael.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class GameActivity extends Activity {

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
			ticTacToeBoard.setGameBoardCells(savedInstanceState.getIntArray("gameBoardCells"));
			ticTacToeBoard.setCurrentPlayer(savedInstanceState.getInt("currentPlayer"));
			ticTacToeBoard.setNumCellsPerRow(savedInstanceState.getInt("numCellsPerRow"));
			
		}
		else {
			ticTacToeBoard.setGameType(TicTacToeBoard.GameType.GAME_4_X_4);
			ticTacToeBoard.resetGame();
		}
		
		// Load the background, player X and player O bitmaps
		ticTacToeBoard.setBoardBitmap(canvasView.loadBitmap(this,
				R.drawable.wood_background));
		canvasView.setmBackgroundBitmap(ticTacToeBoard.getBoardBitmap());

		ticTacToeBoard.setPlayerOBitmap(canvasView.loadBitmap(this,
				R.drawable.o));
		canvasView.setmPlayerOBitmap(ticTacToeBoard.getPlayerOBitmap());

		ticTacToeBoard.setPlayerXBitmap(canvasView.loadBitmap(this,
				R.drawable.x));
		canvasView.setmPlayerXBitmap(ticTacToeBoard.getPlayerXBitmap());

		canvasView.setmGameBoardCells(ticTacToeBoard.getGameBoardCells());
		canvasView.setNumCellsPerRow(ticTacToeBoard.getNumCellsPerRow());
		
		// Draw the board
		canvasView.invalidate();

		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int pixelFactor = (dm.widthPixels < dm.heightPixels? dm.widthPixels : dm.heightPixels) / ticTacToeBoard.getNumCellsPerRow();
		
		canvasView.setPixelFactor(pixelFactor);
		
		//Toast.makeText(this,"PixelFactor: " + pixelFactor, Toast.LENGTH_LONG).show();

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putIntArray("gameBoardCells", ticTacToeBoard.getGameBoardCells());
		outState.putInt("currentPlayer", ticTacToeBoard.getCurrentPlayer());
		outState.putInt("numCellsPerRow", ticTacToeBoard.getNumCellsPerRow());
	}

	View.OnTouchListener mTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int rawX = (int) event.getRawX();
			int rawY = (int) event.getRawY();
			int pixelFactor = canvasView.getPixelFactor();
			int xTranslated = rawX / pixelFactor;
			int yTranslated = rawY / pixelFactor * ticTacToeBoard.getNumCellsPerRow();
			
			int currentGameStatus = ticTacToeBoard
					.getGameStatus();
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
					if (xTranslated < ticTacToeBoard.getNumCellsPerRow() && yTranslated <= (ticTacToeBoard.getNumCellsPerRow() -1) * ticTacToeBoard.getNumCellsPerRow()) {
						ticTacToeBoard.consumeTurn(gameBoardCell);
						currentGameStatus = ticTacToeBoard
								.getGameStatus();
					}
						break;
				case TicTacToeBoard.GameStatus.GAME_OVER_TIE:
				case TicTacToeBoard.GameStatus.PLAYER_O_WINS:
				case TicTacToeBoard.GameStatus.PLAYER_X_WINS:
				default:
					ticTacToeBoard.resetGame();
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