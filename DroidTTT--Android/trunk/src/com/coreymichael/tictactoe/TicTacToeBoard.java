package com.coreymichael.tictactoe;

import android.graphics.Bitmap;

public class TicTacToeBoard {
	//Declaring bitmaps
	private Bitmap mBoardBitmap;
	private Bitmap mPlayerXBitmap;
	private Bitmap mPlayerOBitmap;
	
	private int[][] mGameBoardCells;
	
	private int mGameType;
	private int mNumCellsPerRow;
	private int mGameStatus;
	private int mCurrentPlayer;
	

	public void setBoardBitmap(Bitmap mBoardBitmap) {
		this.mBoardBitmap = mBoardBitmap;
	}

	public Bitmap getBoardBitmap() {
		return mBoardBitmap;
	}

	public void setPlayerXBitmap(Bitmap mPlayerXBitmap) {
		this.mPlayerXBitmap = mPlayerXBitmap;
	}

	public Bitmap getPlayerXBitmap() {
		return mPlayerXBitmap;
	}

	public void setPlayerOBitmap(Bitmap mPlayerOBitmap) {
		this.mPlayerOBitmap = mPlayerOBitmap;
	}

	public Bitmap getPlayerOBitmap() {
		return mPlayerOBitmap;
	}

	public void setGameType(int mGameType) {
		this.mGameType = mGameType;
		
		switch (getGameType()) {
			case GameType.GAME_3_X_3: 
				mNumCellsPerRow = 3;
				break;
			case GameType.GAME_4_X_4:
				mNumCellsPerRow = 4;
				break;
			case GameType.GAME_6_X_6:
				mNumCellsPerRow = 6;
				break;
		}
		
		private int[][] mGameBoardCells = new int[mNumCellsPerRow][mNumCellsPerRow]; //Initializes a new game board that is 3x3, 4x4, 6x6, etc.
	}

	public int getGameType() {
		return mGameType;
	}

	public void setGameStatus(int mGameStatus) {
		this.mGameStatus = mGameStatus;
	}

	public int getGameStatus() {
		return mGameStatus;
	}

	public void setNumCellsPerRow(int mNumCellsPerRow) {
		this.mNumCellsPerRow = mNumCellsPerRow;
	}

	public int getNumCellsPerRow() {
		return mNumCellsPerRow;
	}

	public int getCellStatus(int row, int col) {
		return getGameBoardCells()[row][col];
	}

	public void setCellStatus(int row, int col, int status) {
		getGameBoardCells()[row][col] = status;
	}
	
	public void setCurrentPlayer (int thePlayer) {
		this.mCurrentPlayer = thePlayer;
	}
	
	public int getCurrentPlayer () {
		return this.mCurrentPlayer;
	}
	
	public void setGameBoardCells(int[][] mGameBoardCells) {
		this.mGameBoardCells = mGameBoardCells;
	}

	public int[][] getGameBoardCells() {
		return mGameBoardCells;
	}

	public void consumeTurn(int attemptedRow, int attemptedColumn) {
		// If a move was made, set the targeted cell to the current player, and then cycle the player
		if (getGameBoardCells()[attemptedRow][attemptedColumn] == CellStatus.EMPTY) {
			getGameBoardCells()[attemptedRow][attemptedColumn] = mCurrentPlayer;
// Right now, this function is out-of-order			mGameStatus = getGameStatusAndCheckForWinner(mCurrentPlayer);
mGameStatus = GameStatus.GAME_IN_PLAY; // Remove this line when getGameStatusAndCheckForWinner is fixed.
			if (mCurrentPlayer == CellStatus.PLAYER_O)
				mCurrentPlayer = CellStatus.PLAYER_X;
			else
				mCurrentPlayer = CellStatus.PLAYER_O;
		}
	}

//	public int getGameStatusAndCheckForWinner(int lastPlayer) {
//		int currentCellOfCurrentRow;
//		int lastCellOfCurrentRow;
//		
//		int currentCellOfCurrentColumn; 
//		int lastCellOfCurrentColumn;
//		
//		int currentCellOfCurrentDiag;
//		int lastCellOfCurrentDiag;
//		for (int x = 0; x < mNumCellsPerRow; x++) {
//			
//			// Check each row to see if the last player won
//			currentCellOfCurrentRow = x * mNumCellsPerRow;
//			lastCellOfCurrentRow = x * mNumCellsPerRow + mNumCellsPerRow - 1;
//			while (mGameBoardCells[currentCellOfCurrentRow][???] == lastPlayer && currentCellOfCurrentRow <= lastCellOfCurrentRow) {
//				if (currentCellOfCurrentRow == lastCellOfCurrentRow)
//					return (lastPlayer == CellStatus.PLAYER_X? GameStatus.PLAYER_X_WINS : GameStatus.PLAYER_O_WINS);
//				currentCellOfCurrentRow++;
//			}
//			
//			// Check each column to see if the last player won
//			currentCellOfCurrentColumn = x;
//			lastCellOfCurrentColumn = mNumCellsPerRow * mNumCellsPerRow - mNumCellsPerRow + x;
//			while (mGameBoardCells[currentCellOfCurrentColumn][???] == lastPlayer && currentCellOfCurrentColumn <= lastCellOfCurrentColumn) {
//				if (currentCellOfCurrentColumn == lastCellOfCurrentColumn)
//					return (lastPlayer == CellStatus.PLAYER_X? GameStatus.PLAYER_X_WINS : GameStatus.PLAYER_O_WINS);
//				currentCellOfCurrentColumn += mNumCellsPerRow;
//			}			
//		}
//		
//		
//		// Check the top-left to bottom-right diagonal
//		currentCellOfCurrentDiag = 0;
//		lastCellOfCurrentDiag = mNumCellsPerRow * mNumCellsPerRow - 1;
//		while (mGameBoardCells[currentCellOfCurrentDiag][???] == lastPlayer && currentCellOfCurrentDiag <= lastCellOfCurrentDiag) {
//			if (currentCellOfCurrentDiag == lastCellOfCurrentDiag)
//				return (lastPlayer == CellStatus.PLAYER_X? GameStatus.PLAYER_X_WINS : GameStatus.PLAYER_O_WINS);
//			currentCellOfCurrentDiag += mNumCellsPerRow + 1;
//		}
//		// Check first top-right to bottom-left diagonal
//		currentCellOfCurrentDiag = mNumCellsPerRow - 1;
//		lastCellOfCurrentDiag = mNumCellsPerRow * (mNumCellsPerRow - 1);
//		while (mGameBoardCells[currentCellOfCurrentDiag][???] == lastPlayer && currentCellOfCurrentDiag <= lastCellOfCurrentDiag) {
//			if (currentCellOfCurrentDiag == lastCellOfCurrentDiag)
//				return (lastPlayer == CellStatus.PLAYER_X? GameStatus.PLAYER_X_WINS : GameStatus.PLAYER_O_WINS);
//			currentCellOfCurrentDiag += mNumCellsPerRow - 1;
//		}
//		
//		// Check for a tie (all cells are filled)
//		int x = 0;
//		int lastCell = mNumCellsPerRow * mNumCellsPerRow - 1;
//		while (mGameBoardCells[x][???] != CellStatus.EMPTY && x <= lastCell) {
//			if (x == lastCell)
//				return GameStatus.GAME_OVER_TIE;
//			x++;
//		}		
//		
//		// If no one has won, and there is not a tie, then the game is still in play
//		return GameStatus.GAME_IN_PLAY;
//	}
	
	public void resetGame() {
		
		// Set each cell to EMPTY
		for (int row = 0; row < mGameBoardCells.length; row++) { // Iterates over the rows
			for (int col = 0; col < mGameBoardCells[0].length; col++) { // Iterates over the columns
				setCellStatus(row, col, CellStatus.EMPTY);
			}
		}
		
		// Set the first player to Player X;
		mCurrentPlayer = CellStatus.PLAYER_X;
		// Set the game status to in play
		mGameStatus = GameStatus.GAME_IN_PLAY;
	}
	
	public static final class CellStatus {
		public static final int EMPTY = 0;
		public static final int PLAYER_X = 1;
		public static final int PLAYER_O = 2;
	}
	
	public static final class GameStatus {
		public static final int GAME_IN_PLAY = 0;
		public static final int PLAYER_X_WINS = 1;
		public static final int PLAYER_O_WINS = 2;
		public static final int GAME_OVER_TIE = 3;
	}
	
	public static final class GameType {
		public static final int GAME_3_X_3 = 0;
		public static final int GAME_4_X_4 = 1;
		public static final int GAME_6_X_6 = 2;
		
		public static final int getGameTypeFromString (String string) {
			if (string.equals("GAME_6_X_6")) return GAME_6_X_6;
			if (string.equals("GAME_4_X_4")) return GAME_4_X_4;
			if (string.equals("GAME_3_X_3")) return GAME_3_X_3;
			return NULL;
		}
	}

}
