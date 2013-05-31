package com.coreymichael.tictactoe;

import android.graphics.Bitmap;
import java.util.*; 

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
	private int mMovesNeededToWin;
	private int mMovesSoFar;
	private int mRoomForWinningMove; // This counts both current player pieces as well as empty spaces to determine if a winning move is possible
	
	private boolean mWinningMovePossible = false;
	
	
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
		
		mGameBoardCells = new int[mNumCellsPerRow][mNumCellsPerRow]; //Initializes a new game board that is 3x3, 4x4, 6x6, etc.
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
	
	public boolean isPlayer (int cellStatus) {
		return (cellStatus == CellStatus.PLAYER_X || cellStatus == CellStatus.PLAYER_O);
	}
	
	public boolean isOpposingPlayer (int cellStatus) {
		return (isPlayer(cellStatus) && cellStatus != getCurrentPlayer());
	}
	
	public int CurrentPlayerWins () {
		return (getCurrentPlayer() == CellStatus.PLAYER_X ? GameStatus.PLAYER_X_WINS : GameStatus.PLAYER_O_WINS);
	}
	
	public int getMovesSoFar() {
		return mMovesSoFar;
	}
	
	public void setMovesSoFar(int mMovesSoFar) {
		this.mMovesSoFar = mMovesSoFar;
	}
	
	public void setGameBoardCells(int[][] mGameBoardCells) {
		this.mGameBoardCells = mGameBoardCells;
	}
	
	public int[][] getGameBoardCells() {
		return mGameBoardCells;
	}
	
	public void consumeTurn(int attemptedRow, int attemptedColumn) {
		// If a move was made, set the targeted cell to the current player, and then swap the players
		if (getCellStatus(attemptedRow, attemptedColumn) == CellStatus.EMPTY) {
			setCellStatus(attemptedRow, attemptedColumn, mCurrentPlayer);
			setMovesSoFar(getMovesSoFar() + 1);
			setGameStatus(updateGameStatus());
			swapThePlayers();
		}
	}
		
	private void swapThePlayers() {
		if (mCurrentPlayer == CellStatus.PLAYER_O)
			mCurrentPlayer = CellStatus.PLAYER_X;
		else
			mCurrentPlayer = CellStatus.PLAYER_O;
	}
	
	// Under Construction below
	
	public int checkHorizontal(int row) {
		int[] blockOfRowCoordinates = new int[mNumCellsPerRow * 2];
		for (int col = 0; col < mNumCellsPerRow; col++) {
			blockOfRowCoordinates[(col * 2)] = row;
			blockOfRowCoordinates[(col * 2) + 1] = col;
		}
		return checkBlock(blockOfRowCoordinates); // checkBlock analyzes the entire 1D array that's passed in.
	}
	
	
	public int checkVertical(int col) {
		int[] blockOfColumnCoordinates = new int[mNumCellsPerRow * 2];
		for (int row = 0; row < mNumCellsPerRow; row++) {
			blockOfColumnCoordinates[(row * 2)] = row;
			blockOfColumnCoordinates[(row * 2) + 1] = col;
		}
		return checkBlock(blockOfColumnCoordinates); // checkBlock analyzes the entire 1D array that's passed in.
	}
	
	// Check the top-left to bottom-right diagonal
	public int checkDiagonalLeftToRight(int row, int col) {
		int remainingRowLength = mNumCellsPerRow - row;
		int remainingColLength = mNumCellsPerRow - col;
		int lengthOfDiagonal = Math.min(remainingRowLength, remainingColLength);
		int[] blockOfCoordinates = new int[lengthOfDiagonal * 2];
//		// Keep looping while the calculated row / column is less than the edge of the game board
//		for (int x = 0; (mNumCellsPerRow < row + x) && (mNumCellsPerRow < col + x); x++) {
		for (int x = 0; x < lengthOfDiagonal; x++) {
			blockOfCoordinates[(x * 2)] = row + x;
			blockOfCoordinates[(x * 2) + 1] = col + x;
		}
		return checkBlock(blockOfCoordinates); // checkBlock analyzes the entire 1D array that's passed in.
	}
	
	// Check the top-right to bottom-left diagonal
	public int checkDiagonalRightToLeft(int row, int col) {
		int remainingRowLength = mNumCellsPerRow - row;
		int remainingColLength = col + 1;
		int lengthOfDiagonal = Math.min(remainingRowLength, remainingColLength);
		int[] blockOfCoordinates = new int[lengthOfDiagonal * 2];
//		// Keep looping while the calculated row / column is less than the edge of the game board
//		for (int x = 0; (mNumCellsPerRow < row + x) && (mNumCellsPerRow < col + x); x++) {
		for (int x = 0; x < lengthOfDiagonal; x++) {
			blockOfCoordinates[(x * 2)] = row + x;
			blockOfCoordinates[(x * 2) + 1] = col - x;
		}
		return checkBlock(blockOfCoordinates); // checkBlock analyzes the entire 1D array that's passed in.
	}
	
	public int checkBlock(int[] blockOfCoordinates) {
		mRoomForWinningMove = 0;
	// blockOfCoordinates stores coordinates in this format:   [row1][col1][row2][col2][row3][col3] etc...
		ArrayList<Integer> mCurrentStreak = new ArrayList<Integer>(); // List of the coordinates for the current player's winning streak
		// mCurrentStreak stores coordinates in the same format as blockOfCoordinates
		int row;
		int col;
		
		for (int x = 0; x < blockOfCoordinates.length; x += 2) { // Iterates across the block
			row = blockOfCoordinates[x];
			col = blockOfCoordinates[x + 1];
			
			// Adding one more of the current player's pieces to the 'Winning Streak'
			if (getCellStatus(row, col) == getCurrentPlayer()) {
				mRoomForWinningMove++;
				mCurrentStreak.add(row); // Add coordinates for current piece to the 'Winning Streak'
				mCurrentStreak.add(col); // Add coordinates for current piece to the 'Winning Streak'
			}
			
			// If we encounter a blank space, we reset the 'Winning Streak' but keep counting the 'Room for Winning Move'
			if (getCellStatus(row, col) == CellStatus.EMPTY) {
				mRoomForWinningMove++;
				mCurrentStreak.clear();
			}
			
			// It's back to 0 if we detect a piece by the other player
			if (isOpposingPlayer(getCellStatus(row, col))) { // If current cell belongs to the opposing player...
				mRoomForWinningMove = 0; // Reset the mRoomForWinningMove counter
				mCurrentStreak.clear();
			}
			
			// Tallying up a current player's pieces
			if (mCurrentStreak.size() >= mMovesNeededToWin * 2) {
				return CurrentPlayerWins();
			}
			
			// If there is enough room for a winning move, then we know we aren't deadlocked
			if (mRoomForWinningMove >= mMovesNeededToWin) {
				mWinningMovePossible = true;
			}
			
		}
		return GameStatus.GAME_IN_PLAY; // If no one has won, and there is not a tie, then the game is still in play
	}
	
	
	
	
	
	
	// New method for Winner Detection
	public int updateGameStatus() {  
//**		if (getMovesSoFar() >= ((mMovesNeededToWin * 2) - 1) ) { // We only check for winner if we have enough pieces on the board
			
			mWinningMovePossible = false;
			
			// check rows
			for (int row = 0; row < mNumCellsPerRow; row++) {
				int rowStatus = checkHorizontal(row);
				if (rowStatus != GameStatus.GAME_IN_PLAY)
					return rowStatus;
			}
			
			// check columns
			for (int col = 0; col < mNumCellsPerRow; col++) {
				int colStatus = checkVertical(col);
				if (colStatus != GameStatus.GAME_IN_PLAY)
					return colStatus;
			}
			
		// check diagonals
			
			// Check the top-left to bottom-right diagonal, starting cell on the left wall.
			for (int row = mNumCellsPerRow - mMovesNeededToWin; row >= 0; row--) { // Starting at the earliest diagonal that is long enough to have a winning move
				int diagonalStatus = checkDiagonalLeftToRight(row, 0);
				if (diagonalStatus != GameStatus.GAME_IN_PLAY)
					return diagonalStatus;
			}
			
			// Check the top-left to bottom-right diagonal, starting cell on the top wall.
			for (int col = 0; col <= mNumCellsPerRow - mMovesNeededToWin; col++) { // Ending at the latest diagonal that is long enough to have a winning move
				int diagonalStatus = checkDiagonalLeftToRight(0, col);
				if (diagonalStatus != GameStatus.GAME_IN_PLAY)
					return diagonalStatus;
			}
			
			// Check the top-right to bottom-left diagonal, starting cell on the top wall.
			for (int col = mMovesNeededToWin - 1; col < mNumCellsPerRow; col++) { // Starting at the earliest diagonal that is long enough to have a winning move
				int diagonalStatus = checkDiagonalRightToLeft(0, col);
				if (diagonalStatus != GameStatus.GAME_IN_PLAY)
					return diagonalStatus;
			}
			
			// Check the top-right to bottom-left diagonal, starting cell on the right wall.
			for (int row = 0; row <= mNumCellsPerRow - mMovesNeededToWin; row++) { // Ending at the latest diagonal that is long enough to have a winning move
				int diagonalStatus = checkDiagonalRightToLeft(row, mNumCellsPerRow - 1);
				if (diagonalStatus != GameStatus.GAME_IN_PLAY)
					return diagonalStatus;
			}
			
			// Are we in a deadlock?
			if (mWinningMovePossible == false) {
				return GameStatus.GAME_OVER_TIE;
			}
			
//**		}
		return GameStatus.GAME_IN_PLAY; // If no one has won, and there is not a tie, then the game is still in play
	}
	// Under Construction above
	
	public void resetGame() {
		
		// Set each cell to EMPTY
		for (int row = 0; row < mGameBoardCells.length; row++) { // Iterates over the rows
			for (int col = 0; col < mGameBoardCells[0].length; col++) { // Iterates over the columns
				setCellStatus(row, col, CellStatus.EMPTY);
			}
		}
		
		mCurrentPlayer = CellStatus.PLAYER_X;	// Set the first player to Player X;
		mGameStatus = GameStatus.GAME_IN_PLAY;
		mMovesSoFar = 0;
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
		public static final int GAME_3_X_3 = 3;
		public static final int GAME_4_X_4 = 4;
		public static final int GAME_6_X_6 = 6;
		
		public static final int getGameTypeFromString (String string) {
			if (string.equals("GAME_6_X_6")) return GAME_6_X_6;
			if (string.equals("GAME_4_X_4")) return GAME_4_X_4;
			if (string.equals("GAME_3_X_3")) return GAME_3_X_3;
			return 0;
		}
	}

}
