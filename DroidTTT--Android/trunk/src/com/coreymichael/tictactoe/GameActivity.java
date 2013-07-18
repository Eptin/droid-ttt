package com.coreymichael.tictactoe;

import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class GameActivity extends Activity {

	CanvasView canvasView;
	TicTacToeBoard ticTacToeBoard;
	DisplayMetrics dm;
//	MediaPlayer mp;

	
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
//        if (mp != null) {
//            mp.stop();
//            mp.reset();
//            mp.release();
//        }
    }
	
    public void cycleMusic() {
    	
    }
	
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
			//ticTacToeBoard.setGameBoardCells(savedInstanceState.getIntArray("gameBoardCells"));
			SaveData saveData = (SaveData) getLastNonConfigurationInstance();
		    if (saveData != null) {
				ticTacToeBoard.setCurrentPlayer(saveData.currentPlayer);
				ticTacToeBoard.setNumCellsPerRow(saveData.numCellsPerRow);
				ticTacToeBoard.setGameType(saveData.gameType);
		    	ticTacToeBoard.setGameBoardCells(saveData.gameBoardCells);
		        
		    }

		}
		else {
			Intent intent = getIntent();
			char gameType = TicTacToeBoard.GameType.getGameTypeFromString((intent.getStringExtra(MainActivity.GAME_TYPE)));

			ticTacToeBoard.setGameType(gameType);
			ticTacToeBoard.resetGame();
//			mp = MediaPlayer.create(this, R.raw.beat);
//			mp.start();

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
		int cellPixelSize = Math.min(dm.widthPixels, dm.heightPixels) / ticTacToeBoard.getNumCellsPerRow();
		
		canvasView.setCellPixelSize(cellPixelSize);
		
		//Toast.makeText(this,"CellPixelSize: " + cellPixelSize, Toast.LENGTH_LONG).show();

	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
	    SaveData saveData = new SaveData();
	    saveData.currentPlayer = ticTacToeBoard.getCurrentPlayer();
	    saveData.numCellsPerRow = ticTacToeBoard.getNumCellsPerRow();
	    saveData.gameType = ticTacToeBoard.getGameType();
	    saveData.gameBoardCells = ticTacToeBoard.getGameBoardCells();
	    return saveData;
	}

	View.OnTouchListener mTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int rawX = (int) event.getRawX();
			int rawY = (int) event.getRawY();
			int cellPixelSize = canvasView.getCellPixelSize();
			int colClicked = rawX / cellPixelSize;
			int rowClicked = rawY / cellPixelSize;
			
			int currentGameStatus = ticTacToeBoard.getGameStatus();

			// Toast.makeText(v.getContext(), "X: " +
			// rawX + " Y: " + rawY, Toast.LENGTH_LONG).show();
			// Toast.makeText(v.getContext(),
			// "Translated X: " + colClicked +
			// "\nTranslated Y: " + rowClicked +
			// "\nRawX: " + rawX +
			// "\nRawY: " + rawY +
			// "\nBameboard Contrib from X: " + (int) rawX / 160 +
			// "\nBameboard Contrib from Y: " + (int) rawY / 160 * 3 +
			// "\nGameboard cell: " + gameBoardCell, Toast.LENGTH_LONG).show(); // gameBoardCell is no more, so remove that.

			
			/* PRE TURN CHECK
			 * If the game is still in play and the move 
			 * was not out of bounds, consume a turn.
			 * Otherwise reset the board and redraw
			 */
			switch (currentGameStatus) {
				case TicTacToeBoard.GameStatus.GAME_IN_PLAY:
					// Out of bounds check
					boolean colAlright;
					boolean rowAlright;
//					int numCellsPerRow;
//					numCellsPerRow = ticTacToeBoard.getNumCellsPerRow();
					colAlright = colClicked < ticTacToeBoard.getNumCellsPerRow();
					rowAlright = rowClicked < ticTacToeBoard.getNumCellsPerRow();
					if (colAlright && rowAlright) {
						ticTacToeBoard.consumeTurn(rowClicked, colClicked);
						cycleMusic();
						currentGameStatus = ticTacToeBoard.getGameStatus();
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

	public class SaveData {
		public char currentPlayer;
		public int numCellsPerRow;
		public char gameType;
		public char [][] gameBoardCells;
		
	}

}