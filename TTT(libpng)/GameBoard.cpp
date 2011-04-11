#include "GameBoard.h"

// Constructor and destructor
GameBoard::GameBoard (void)
{
    ResetBoard();
}
GameBoard::~GameBoard(void)
{

}

// Accessors
int GameBoard::GetCellStatus(int CellNumber)
{
    return Cell[CellNumber];
}

void GameBoard::SetCellStatus(int CellNumber, int CellStatus)
{
    Cell[CellNumber] = CellStatus;
}


// GameBoard Functions
void GameBoard::ResetBoard(void)
{
    for(int x = 0; x < 9; x++) SetCellStatus(x, BOARD_CELL_EMPTY);

}


