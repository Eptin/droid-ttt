package com.coreymichael.tictactoe;

import android.graphics.Bitmap;
import java.util.*;

public class CurrentGame {
	
	//Declaring bitmaps
	private Bitmap mBoardBitmap;
	private Bitmap mPlayerXBitmap;
	private Bitmap mPlayerOBitmap;
	
	private char mGameStatus;
	private char mCurrentPlayer;
	private int mMovesNeededToWin;
	private int mMovesSoFar;
	private int mRoomForWinningMove; // This counts both current player pieces as well as empty spaces to determine if a winning move is possible
	private boolean mWinningMovePossible = false;
	
	private GameBoard mGameBoard;
	
	// Constructs a game with the provided board (either an empty or pre-filled board)
	CurrentGame(String gameBoardLocation) {
		setCurrentPlayer(CellStatus.PLAYER_X);
		setGameStatus(GameStatus.GAME_IN_PLAY);
		mMovesSoFar = 0;
		GameBoard mGameBoard = new GameBoard(gameBoardLocation); // Builds a pre-filled game board by passing the location of the board's file.
	}
	
	// Constructs a new game with an empty board
	CurrentGame(char gameType) {
		setCurrentPlayer(CellStatus.PLAYER_X);
		setGameStatus(GameStatus.GAME_IN_PLAY);
		mMovesSoFar = 0;
		GameBoard mGameBoard = new GameBoard(gameType); // Initializes a new game board that is 3x3, 4x4, 6x6, etc.
	}
	
	
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
	
	public void setGameStatus(char mGameStatus) {
		this.mGameStatus = mGameStatus;
	}
	
	public char getGameStatus() {
		return mGameStatus;
	}
	
	public void setCurrentPlayer (char thePlayer) {
		this.mCurrentPlayer = thePlayer;
	}
	
	public char getCurrentPlayer () {
		return this.mCurrentPlayer;
	}
	
	public char getOpposingPlayer () {
		return (this.mCurrentPlayer == CellStatus.PLAYER_X ? CellStatus.PLAYER_O : CellStatus.PLAYER_X);
	}
	
	public boolean isPlayer (char cellStatus) {
		return (cellStatus == CellStatus.PLAYER_X || cellStatus == CellStatus.PLAYER_O);
	}
	
	// Returns a boolean for whether the cellstatus belongs to a player opposing the current player
	public boolean isOpposingPlayer (char cellStatus) {
		return (isPlayer(cellStatus) && cellStatus != getCurrentPlayer());
	}
	
	// Returns a boolean for whether the cellstatus belongs to a player opposing the player passed in
	public boolean isOpposingPlayerToThisPlayer (char cellStatus, char otherPlayer) {
		return (isPlayer(cellStatus) && cellStatus != otherPlayer);
	}
	
	public char CurrentPlayerWins () {
		return (getCurrentPlayer() == CellStatus.PLAYER_X ? GameStatus.PLAYER_X_WINS : GameStatus.PLAYER_O_WINS);
	}
	
	public char ThisPlayerWins (char player) {
		return (player == CellStatus.PLAYER_X ? GameStatus.PLAYER_X_WINS : GameStatus.PLAYER_O_WINS);
	}
	
	public int getMovesSoFar() {
		return mMovesSoFar;
	}
	
	public void setMovesSoFar(int mMovesSoFar) {
		this.mMovesSoFar = mMovesSoFar;
	}
	
	public void consumeTurn(int attemptedRow, int attemptedColumn) {
		// If a move was made, set the targeted cell to the current player, and then swap the players
		if (mGameBoard.getCellStatus(attemptedRow, attemptedColumn) == CellStatus.EMPTY) {
			mGameBoard.setCellStatus(attemptedRow, attemptedColumn, mCurrentPlayer);
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

	// Checks a single horizontal row
	public char checkHorizontal(int row) {
		int[] blockOfRowCoordinates = new int[mGameBoard.getNumCellsPerRow() * 2];
		for (int col = 0; col < mGameBoard.getNumCellsPerRow(); col++) {
			blockOfRowCoordinates[(col * 2)] = row;
			blockOfRowCoordinates[(col * 2) + 1] = col;
		}
		checkBlockForRoomForWinningMove(blockOfRowCoordinates, getOpposingPlayer());
		return checkBlock(blockOfRowCoordinates, getCurrentPlayer()); // checkBlock analyzes the entire 1D array that's passed in.
	}
	
	// Checks a single vertical column
	public char checkVertical(int col) {
		int[] blockOfColumnCoordinates = new int[mGameBoard.getNumCellsPerRow() * 2];
		for (int row = 0; row < mGameBoard.getNumCellsPerRow(); row++) {
			blockOfColumnCoordinates[(row * 2)] = row;
			blockOfColumnCoordinates[(row * 2) + 1] = col;
		}
		checkBlockForRoomForWinningMove(blockOfColumnCoordinates, getOpposingPlayer());
		return checkBlock(blockOfColumnCoordinates, getCurrentPlayer()); // checkBlock analyzes the entire 1D array that's passed in.
	}
	
	// Checks a single top-left to bottom-right diagonal
	public char checkDiagonalLeftToRight(int row, int col) {
		int remainingRowLength = mGameBoard.getNumCellsPerRow() - row;
		int remainingColLength = mGameBoard.getNumCellsPerRow() - col;
		int lengthOfDiagonal = Math.min(remainingRowLength, remainingColLength);
		int[] blockOfCoordinates = new int[lengthOfDiagonal * 2];
//		// Keep looping while the calculated row / column is less than the edge of the game board
//		for (int x = 0; (mGameBoard.getNumCellsPerRow() < row + x) && (mGameBoard.getNumCellsPerRow() < col + x); x++) {
		for (int x = 0; x < lengthOfDiagonal; x++) {
			blockOfCoordinates[(x * 2)] = row + x;
			blockOfCoordinates[(x * 2) + 1] = col + x;
		}
		checkBlockForRoomForWinningMove(blockOfCoordinates, getOpposingPlayer());
		return checkBlock(blockOfCoordinates, getCurrentPlayer()); // checkBlock analyzes the entire 1D array that's passed in.
	}
	
	// Checks a single top-right to bottom-left diagonal
	public char checkDiagonalRightToLeft(int row, int col) {
		int remainingRowLength = mGameBoard.getNumCellsPerRow() - row;
		int remainingColLength = col + 1;
		int lengthOfDiagonal = Math.min(remainingRowLength, remainingColLength);
		int[] blockOfCoordinates = new int[lengthOfDiagonal * 2];
//		// Keep looping while the calculated row / column is less than the edge of the game board
//		for (int x = 0; (mGameBoard.getNumCellsPerRow() < row + x) && (mGameBoard.getNumCellsPerRow() < col + x); x++) {
		for (int x = 0; x < lengthOfDiagonal; x++) {
			blockOfCoordinates[(x * 2)] = row + x;
			blockOfCoordinates[(x * 2) + 1] = col - x;
		}
		checkBlockForRoomForWinningMove(blockOfCoordinates, getOpposingPlayer());
		return checkBlock(blockOfCoordinates, getCurrentPlayer()); // checkBlock analyzes the entire 1D array that's passed in.
	}
	
	public char checkBlock(int[] blockOfCoordinates, char player) {
		mRoomForWinningMove = 0;
	// blockOfCoordinates stores coordinates in this format:   [row1][col1][row2][col2][row3][col3] etc...
		ArrayList<Integer> mCurrentStreak = new ArrayList<Integer>(); // List of the coordinates for the player's winning streak
		// mCurrentStreak stores coordinates in the same format as blockOfCoordinates
		int row;
		int col;
		
		for (int x = 0; x < blockOfCoordinates.length; x += 2) { // Iterates across the block
			row = blockOfCoordinates[x];
			col = blockOfCoordinates[x + 1];
			
			// Adding one more of the player's pieces to the 'Winning Streak'
			if (mGameBoard.getCellStatus(row, col) == player) {
				mRoomForWinningMove++;
				mCurrentStreak.add(row); // Add coordinates for current piece to the 'Winning Streak'
				mCurrentStreak.add(col); // Add coordinates for current piece to the 'Winning Streak'
			}
			
			// If we encounter a blank space, we reset the 'Winning Streak' but keep counting the 'Room for Winning Move'
			if (mGameBoard.getCellStatus(row, col) == CellStatus.EMPTY) {
				mRoomForWinningMove++;
				mCurrentStreak.clear();
			}
			
			// It's back to 0 if we detect a piece by the opposing player
			if (isOpposingPlayerToThisPlayer(mGameBoard.getCellStatus(row, col), player)) { // If current cell belongs to the opposing player...
				mRoomForWinningMove = 0; // Reset the mRoomForWinningMove counter
				mCurrentStreak.clear();
			}
			
			// Tallying up the player's pieces
			if (mCurrentStreak.size() >= mMovesNeededToWin * 2) {
				return ThisPlayerWins(player);
			}
			
			// If there is enough room for a winning move, then we know we aren't deadlocked
			if (mRoomForWinningMove >= mMovesNeededToWin) {
				mWinningMovePossible = true;
			}
			
		}
		return GameStatus.GAME_IN_PLAY; // If no one has won, and there is not a tie, then the game is still in play
	}
	
	public void checkBlockForRoomForWinningMove(int[] blockOfCoordinates, char player) {
		mRoomForWinningMove = 0;
	// blockOfCoordinates stores coordinates in this format:   [row1][col1][row2][col2][row3][col3] etc...
		int row;
		int col;
		
		for (int x = 0; x < blockOfCoordinates.length; x += 2) { // Iterates across the block
			row = blockOfCoordinates[x];
			col = blockOfCoordinates[x + 1];
			
			// We add to mRoomForWinningMove if we encounter either the player's pieces or a blank space
			if ((mGameBoard.getCellStatus(row, col) == player) || (mGameBoard.getCellStatus(row, col) == CellStatus.EMPTY)) {
				mRoomForWinningMove++;
			}
			
			// It's back to 0 if we detect a piece by the opposing player
			if (isOpposingPlayerToThisPlayer(mGameBoard.getCellStatus(row, col), player)) { // If current cell belongs to the opposing player...
				mRoomForWinningMove = 0; // Reset the mRoomForWinningMove counter
			}
			
			// If there is enough room for a winning move, then we know we aren't deadlocked
			if (mRoomForWinningMove >= mMovesNeededToWin) {
				mWinningMovePossible = true;
			}
			
		}
	}
		
	// Winner Detection
	public char updateGameStatus() {  
		if (getMovesSoFar() >= ((mMovesNeededToWin * 2) - 1) ) { // We only check for winner if we have enough pieces on the board
			
			mWinningMovePossible = false;
			
			// Check all rows
			for (int row = 0; row < mGameBoard.getNumCellsPerRow(); row++) {
				char rowStatus = checkHorizontal(row);
				if (rowStatus != GameStatus.GAME_IN_PLAY)
					return rowStatus;
			}
			
			// Check all columns
			for (int col = 0; col < mGameBoard.getNumCellsPerRow(); col++) {
				char colStatus = checkVertical(col);
				if (colStatus != GameStatus.GAME_IN_PLAY)
					return colStatus;
			}
			
		// Check diagonals (only the ones long enough to contain a possible winner)
			
			// Check the top-left to bottom-right diagonal, starting cell on the left wall.
			for (int row = mGameBoard.getNumCellsPerRow() - mMovesNeededToWin; row >= 0; row--) { // Starting at the earliest diagonal that is long enough to have a winning move
				char diagonalStatus = checkDiagonalLeftToRight(row, 0);
				if (diagonalStatus != GameStatus.GAME_IN_PLAY)
					return diagonalStatus;
			}
			
			// Check the top-left to bottom-right diagonal, starting cell on the top wall.
			for (int col = 0; col <= mGameBoard.getNumCellsPerRow() - mMovesNeededToWin; col++) { // Ending at the latest diagonal that is long enough to have a winning move
				char diagonalStatus = checkDiagonalLeftToRight(0, col);
				if (diagonalStatus != GameStatus.GAME_IN_PLAY)
					return diagonalStatus;
			}
			
			// Check the top-right to bottom-left diagonal, starting cell on the top wall.
			for (int col = mMovesNeededToWin - 1; col < mGameBoard.getNumCellsPerRow(); col++) { // Starting at the earliest diagonal that is long enough to have a winning move
				char diagonalStatus = checkDiagonalRightToLeft(0, col);
				if (diagonalStatus != GameStatus.GAME_IN_PLAY)
					return diagonalStatus;
			}
			
			// Check the top-right to bottom-left diagonal, starting cell on the right wall.
			for (int row = 0; row <= mGameBoard.getNumCellsPerRow() - mMovesNeededToWin; row++) { // Ending at the latest diagonal that is long enough to have a winning move
				char diagonalStatus = checkDiagonalRightToLeft(row, mGameBoard.getNumCellsPerRow() - 1);
				if (diagonalStatus != GameStatus.GAME_IN_PLAY)
					return diagonalStatus;
			}
			
			// Are we in a deadlock?
			if (mWinningMovePossible == false) {
				return GameStatus.GAME_OVER_TIE;
			}
			
		}
		return GameStatus.GAME_IN_PLAY; // If no one has won, and there is not a tie, then the game is still in play
	}
	
	public static final class CellStatus {
		public static final char EMPTY    = '_';
		public static final char PLAYER_X = 'X';
		public static final char PLAYER_O = 'O';
		public static final char DEAD     = 'D'; // Dead cells aka "craters" are formerly-playable cells
		public static final char VOID     = 'V'; // Void cells are used to remove cells from the game board altogether. They can be used to shape the board.
	}
	
	public static final class GameStatus {
		public static final char GAME_IN_PLAY  = 'P';
		public static final char PLAYER_X_WINS = 'X';
		public static final char PLAYER_O_WINS = 'O';
		public static final char GAME_OVER_TIE = 'T';
	}

}