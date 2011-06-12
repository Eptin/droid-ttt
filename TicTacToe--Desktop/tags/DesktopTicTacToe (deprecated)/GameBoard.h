#define GAME_BOARD

class GameBoard
{
    protected:
        int Cell[9];
    public:
        enum {
            BOARD_CELL_EMPTY,
            BOARD_CELL_PLAYERX,
            BOARD_CELL_PLAYERO
        };
    public:
        // Constructor and destructor
        GameBoard (void);
        virtual ~GameBoard(void);

        // Accessors
        int GetCellStatus(int CellNumber);
        void SetCellStatus(int CellNumber, int CellStatus);
//
//        int GetGameStatus();
//
//        int GetTurnsLeft();
//        void SubtractATurn();

        // GameBoard Functions
        void ResetBoard(void);

};

