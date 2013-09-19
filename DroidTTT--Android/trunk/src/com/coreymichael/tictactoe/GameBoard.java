package com.coreymichael.tictactoe;

//** Whoa, these two lines were automatically added. Why? Do I need them, somehow?
import com.coreymichael.tictactoe.CurrentGame.CellStatus;
import com.coreymichael.tictactoe.CurrentGame.GameStatus;

public class GameBoard {

	private char[][] mGameBoardCells;
	private int mNumCellsPerRow;
	
	// Constructs a game with the provided board (either an empty or pre-filled board)
	GameBoard(String gameBoardLocation) {
//** Import game board from file. CellsPerRow will be provided from the file itself.
	}
	
	// Constructs a new game with an empty board
	GameBoard(char InputCellsPerRow) {
		mNumCellsPerRow(InputCellsPerRow);
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
	
	public char getCellStatus(int row, int col) {
		return getGameBoardCells()[row][col];
	}
	
	public void setCellStatus(int row, int col, char status) {
		getGameBoardCells()[row][col] = status;
	}
	
}