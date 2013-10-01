package com.coreymichael.tictactoe;

import com.coreymichael.tictactoe.CurrentGame.CellStatus;
import com.coreymichael.tictactoe.CurrentGame.GameStatus;

public class GameBoard {

	private char[][] mGameBoardCells;
	private char mGameType;
	private int mNumCellsPerRow;
	private int mMovesNeededToWin;
	
	// Constructs a game by importing from file. CellsPerRow will be provided from the file itself.
	GameBoard(String gameBoardLocation) {
	}
	
	// Constructs a new game with an empty board
	GameBoard(char InputCellsPerRow) {
		setNumCellsPerRow(InputCellsPerRow);
		setGameBoardCells(new char[mNumCellsPerRow][mNumCellsPerRow]); // Initializes a new game board that is 3x3, 4x4, 6x6, etc.
		
		// Set each cell of the game board to EMPTY
		for (int row = 0; row < mGameBoardCells.length; row++) { // Iterates over the rows
			for (int col = 0; col < mGameBoardCells[0].length; col++) { // Iterates over the columns
				setCellStatus(row, col, CellStatus.EMPTY);
			}
		}
	}
	
	public void setGameBoardCells(char[][] mGameBoardCells) {
		this.mGameBoardCells = mGameBoardCells;
	}
	
	public char[][] getGameBoardCells() {
		return mGameBoardCells;
	}
	
	public void setNumCellsPerRow(int mNumCellsPerRow) {
		this.mNumCellsPerRow = mNumCellsPerRow;
	}
	
	public int getNumCellsPerRow() {
		return mNumCellsPerRow;
	}
	
	public void setGameType(char mGameType) {
		this.mGameType = mGameType;
		
		switch (mGameType) {
			case GameType.GAME_3_X_3: 
				mNumCellsPerRow = 3;
				mMovesNeededToWin = 3;
				break;
			case GameType.GAME_4_X_4:
				mNumCellsPerRow = 4;
				mMovesNeededToWin = 3;
				break;
			case GameType.GAME_6_X_6:
				mNumCellsPerRow = 6;
				mMovesNeededToWin = 4;
				break;
		}
	}
	
	public char getGameType() {
		return mGameType;
	}
	
	public int getMovesNeededToWin() {
		return mMovesNeededToWin;
	}
	
	public char getCellStatus(int row, int col) {
		return getGameBoardCells()[row][col];
	}
	
	public void setCellStatus(int row, int col, char status) {
		getGameBoardCells()[row][col] = status;
	}
	
	public static final class GameType {
		public static final char GAME_3_X_3 = '3';
		public static final char GAME_4_X_4 = '4';
		public static final char GAME_6_X_6 = '6';
		
		public static final char getGameTypeFromString (String string) {
			if (string.equals("GAME_6_X_6")) return GAME_6_X_6;
			if (string.equals("GAME_4_X_4")) return GAME_4_X_4;
			if (string.equals("GAME_3_X_3")) return GAME_3_X_3;
			return 0;
		}
	}
	
}