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
	CurrentGame currentGame;
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
    	// Nothing here but us chickens! 🐔 🐔 🐔
    }
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_layout);

		canvasView = (CanvasView) findViewById(R.id.canvasView);
		canvasView.setOnTouchListener(mTouchListener);
		
		// Check to see if there was a saved state and recover if so, otherwise start a new game
		if (savedInstanceState != null) { // We are resuming a game in progress
			SaveData saveData = (SaveData) getLastNonConfigurationInstance();
		    if (saveData != null) {
		    	currentGame = new CurrentGame(saveData.gameType, saveData.gameBoardCells);
				currentGame.setCurrentPlayer(saveData.currentPlayer);
		    }

		} else { // We have no saved game data. Starting from scratch
			Intent intent = getIntent();
			char gameType = CurrentGame.GameType.getGameTypeFromString((intent.getStringExtra(MainActivity.GAME_TYPE)));
			currentGame = new CurrentGame(gameType); // Starting a new game by calling a constructor
//			mp = MediaPlayer.create(this, R.raw.beat);
//			mp.start();

		}
		
		// Load the background, player X and player O bitmaps
		currentGame.setBoardBitmap(canvasView.loadBitmap(this,
				R.drawable.wood_background));
		canvasView.setmBackgroundBitmap(currentGame.getBoardBitmap());

		currentGame.setPlayerOBitmap(canvasView.loadBitmap(this,
				R.drawable.o));
		canvasView.setmPlayerOBitmap(currentGame.getPlayerOBitmap());

		currentGame.setPlayerXBitmap(canvasView.loadBitmap(this,
				R.drawable.x));
		canvasView.setmPlayerXBitmap(currentGame.getPlayerXBitmap());

		canvasView.setmGameBoardCells(currentGame.getGameBoardCells());
		canvasView.setNumCellsPerRow(currentGame.getNumCellsPerRow());
		
		// Draw the board
		canvasView.invalidate();

		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int cellPixelSize = Math.min(dm.widthPixels, dm.heightPixels) / currentGame.getNumCellsPerRow();
		
		canvasView.setCellPixelSize(cellPixelSize);
		
		//Toast.makeText(this,"CellPixelSize: " + cellPixelSize, Toast.LENGTH_LONG).show();

	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
	    SaveData saveData = new SaveData();
	    saveData.currentPlayer = currentGame.getCurrentPlayer();
	    saveData.numCellsPerRow = currentGame.getNumCellsPerRow();
	    saveData.gameType = currentGame.getGameType();
	    saveData.gameBoardCells = currentGame.getGameBoardCells();
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
			
			int currentGameStatus = currentGame.getGameStatus();

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
				case CurrentGame.GameStatus.GAME_IN_PLAY:
					// Out of bounds check
					boolean colAlright;
					boolean rowAlright;
					colAlright = colClicked < currentGame.getNumCellsPerRow();
					rowAlright = rowClicked < currentGame.getNumCellsPerRow();
					if (colAlright && rowAlright) {
						currentGame.consumeTurn(rowClicked, colClicked);
						cycleMusic();
						currentGameStatus = currentGame.getGameStatus();
					}
						break;
				case CurrentGame.GameStatus.GAME_OVER_TIE:
				case CurrentGame.GameStatus.PLAYER_O_WINS:
				case CurrentGame.GameStatus.PLAYER_X_WINS:
				default:
					char gameType = currentGame.getGameType();
//** probably unnecessary					currentGame = null; // Out with the old, in with the new
					currentGame = new CurrentGame(gameType); // Starting a new game by calling a constructor
					canvasView.invalidate();
					return false;
			}

			// POST TURN CHECK
			// If the game is over, display the message
			switch (currentGameStatus) {
				case CurrentGame.GameStatus.PLAYER_O_WINS:
					Toast.makeText(v.getContext(), "Player O Wins!",
							Toast.LENGTH_SHORT).show();
					break;
				case CurrentGame.GameStatus.PLAYER_X_WINS:
					Toast.makeText(v.getContext(), "Player X Wins!",
							Toast.LENGTH_SHORT).show();
					break;
				case CurrentGame.GameStatus.GAME_OVER_TIE:
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