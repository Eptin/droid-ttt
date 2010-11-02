#include "SDL.h"
#include "SDL_opengl.h"
#include "SDL_image.h"

#ifndef GAME_CONTEXT
    #include "GameContext.h"
#endif

#ifndef GAME_SPRITE
    #include "GameSprite.h"
#endif

class GameApp
{
    protected:
        SDL_TimerID timer;
        bool done;
        GameContext context;
        GameSprite  playerX;
        GameSprite  playerY;
        int ResW, ResH;
        int GameBoard[8];

    private:
        enum
        {
            BOARD_CELL_EMPTY,
            BOARD_CELL_PLAYERX,
            BOARD_CELL_PLAYERY
        };

    public:
        // Constructor and destructor
        GameApp (void);
        virtual ~GameApp(void);

        // Accessors
        GameContext GetContext(void);
        void SetContext(GameContext theContext);

        GameSprite GetPlayerX(void);
        void SetPlayerX(GameSprite thePlayer);

        int GetResW(void);
        void SetResW(int theResW);

        int GetResH(void);
        void SetResH(int theResH);

        int GetGameBoardCellStatus(int CellNumber);
        void SetGameBoardCellStatus(int CellNumber, int CellStatus);

        // GameBoard Functions
        void ResetGame(void);

        // Initialization functions
        void InitApp(void);
        void InitializeSDL(void);
        void InitializeDrawContext(Uint16 width, Uint16 height);
        void InstallTimer(void);
        static Uint32 GameLoopTimer(Uint32 interval, void* param);

        // Cleanup functions
        void Cleanup(void);

        // Event-related functions
        void EventLoop(void);
        void HandleUserEvents(SDL_Event* event);
        void HandleKeyPress(SDL_Event* event);
        void HandleMouseMovement(SDL_Event* event);
        void HandleMouseLeftClick(int x, int y);

        // Game related functions
        void GameLoop(void);
        void RenderFrame(void);

};

typedef GameApp* GameAppPtr;
typedef GameApp** GameAppHandle;

const int RUN_GAME_LOOP = 1;
const float MOVEMENT_DISTANCE = 10.0f;
const float ROTATION_AMOUNT = 5.0f;
