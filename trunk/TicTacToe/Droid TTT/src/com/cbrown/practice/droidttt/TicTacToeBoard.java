package com.cbrown.practice.droidttt;

import android.graphics.Bitmap;

public class TicTacToeBoard {
	private Bitmap mBoardBitmap;
	private Bitmap mPlayerXBitmap;
	private Bitmap mPlayerOBitmap;

	private int[] mGameBoardCells = new int[9];
	private int mCurrentPlayer;

	public void setmBoardBitmap(Bitmap mBoardBitmap) {
		this.mBoardBitmap = mBoardBitmap;
	}

	public Bitmap getmBoardBitmap() {
		return mBoardBitmap;
	}

	public void setmPlayerXBitmap(Bitmap mPlayerXBitmap) {
		this.mPlayerXBitmap = mPlayerXBitmap;
	}

	public Bitmap getmPlayerXBitmap() {
		return mPlayerXBitmap;
	}

	public void setmPlayerOBitmap(Bitmap mPlayerOBitmap) {
		this.mPlayerOBitmap = mPlayerOBitmap;
	}

	public Bitmap getmPlayerOBitmap() {
		return mPlayerOBitmap;
	}

	public int getCellStatus(int cell) {
		return getmGameBoardCells()[cell];
	}

	public void setCellStatus(int cell, int status) {
		getmGameBoardCells()[cell] = status;
	}
	
	public void setmCurrentPlayer (int thePlayer) {
		this.mCurrentPlayer = thePlayer;
	}
	
	public int getmCurrentPlayer () {
		return this.mCurrentPlayer;
	}
	
	public void setmGameBoardCells(int[] mGameBoardCells) {
		this.mGameBoardCells = mGameBoardCells;
	}

	public int[] getmGameBoardCells() {
		return mGameBoardCells;
	}

	public void consumeTurn(int attemptedCell) {
		// If a move was made, set the targeted cell to the current player, and
		// then cycle the player
		if (getmGameBoardCells()[attemptedCell] == CellStatus.EMPTY) {
			getmGameBoardCells()[attemptedCell] = mCurrentPlayer;
			if (mCurrentPlayer == CellStatus.PLAYER_O)
				mCurrentPlayer = CellStatus.PLAYER_X;
			else
				mCurrentPlayer = CellStatus.PLAYER_O;
		}
	}

	public int getGameStatusAndCheckForWinner() {
		for (int x = 0; x < 3; x++) {
			// Check each row for Player X
			if (getmGameBoardCells()[(x * 3)] == TicTacToeBoard.CellStatus.PLAYER_X
					&& getmGameBoardCells()[(x * 3 + 1)] == TicTacToeBoard.CellStatus.PLAYER_X
					&& getmGameBoardCells()[(x * 3 + 2)] == TicTacToeBoard.CellStatus.PLAYER_X)
				return TicTacToeBoard.GameStatus.PLAYER_X_WINS;

			// Check each column X for Player X
			if (getmGameBoardCells()[(x)] == TicTacToeBoard.CellStatus.PLAYER_X
					&& getmGameBoardCells()[(x + 3)] == TicTacToeBoard.CellStatus.PLAYER_X
					&& getmGameBoardCells()[(x + 6)] == TicTacToeBoard.CellStatus.PLAYER_X)
				return TicTacToeBoard.GameStatus.PLAYER_X_WINS;

			// Check Row X for Player O
			if (getmGameBoardCells()[(x * 3)] == TicTacToeBoard.CellStatus.PLAYER_O
					&& getmGameBoardCells()[(x * 3 + 1)] == TicTacToeBoard.CellStatus.PLAYER_O
					&& getmGameBoardCells()[(x * 3 + 2)] == TicTacToeBoard.CellStatus.PLAYER_O)
				return TicTacToeBoard.GameStatus.PLAYER_O_WINS;

			// Check Column X for Player O
			if (getmGameBoardCells()[(x)] == TicTacToeBoard.CellStatus.PLAYER_O
					&& getmGameBoardCells()[(x + 3)] == TicTacToeBoard.CellStatus.PLAYER_O
					&& getmGameBoardCells()[(x + 6)] == TicTacToeBoard.CellStatus.PLAYER_O)
				return TicTacToeBoard.GameStatus.PLAYER_O_WINS;
		}

		// Check Diagonals for Player X
		if (getmGameBoardCells()[(0)] == TicTacToeBoard.CellStatus.PLAYER_X
				&& getmGameBoardCells()[(4)] == TicTacToeBoard.CellStatus.PLAYER_X
				&& getmGameBoardCells()[(8)] == TicTacToeBoard.CellStatus.PLAYER_X)
			return TicTacToeBoard.GameStatus.PLAYER_X_WINS;

		if (getmGameBoardCells()[(6)] == TicTacToeBoard.CellStatus.PLAYER_X
				&& getmGameBoardCells()[(4)] == TicTacToeBoard.CellStatus.PLAYER_X
				&& getmGameBoardCells()[(2)] == TicTacToeBoard.CellStatus.PLAYER_X)
			return TicTacToeBoard.GameStatus.PLAYER_X_WINS;

		// Check Diagonals for Player O
		if (getmGameBoardCells()[(0)] == TicTacToeBoard.CellStatus.PLAYER_O
				&& getmGameBoardCells()[(4)] == TicTacToeBoard.CellStatus.PLAYER_O
				&& getmGameBoardCells()[(8)] == TicTacToeBoard.CellStatus.PLAYER_O)
			return TicTacToeBoard.GameStatus.PLAYER_O_WINS;

		if (getmGameBoardCells()[(6)] == TicTacToeBoard.CellStatus.PLAYER_O
				&& getmGameBoardCells()[(4)] == TicTacToeBoard.CellStatus.PLAYER_O
				&& getmGameBoardCells()[(2)] == TicTacToeBoard.CellStatus.PLAYER_O)
			return TicTacToeBoard.GameStatus.PLAYER_O_WINS;

		boolean movesLeft = false;
		for (int x = 0; x < getmGameBoardCells().length; x++) {
			if (getmGameBoardCells()[x] == CellStatus.EMPTY)
				movesLeft = true;
		}

		if (!movesLeft)
			return GameStatus.GAME_OVER_TIE;
		return GameStatus.GAME_IN_PLAY;
	}

	public void resetBoard() {
		// Set each cell to EMPTY
		for (int x = 0; x < getmGameBoardCells().length; x++) {
			getmGameBoardCells()[x] = CellStatus.EMPTY;
		}

		// Set the first player to Player X;
		mCurrentPlayer = CellStatus.PLAYER_X;
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

}
