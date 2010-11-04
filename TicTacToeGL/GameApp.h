#include "SDL.h"
#include "SDL_opengl.h"
#include "SDL_image.h"

#ifndef GAME_CONTEXT
    #include "GameContext.h"
#endif

#ifndef GAME_SPRITE
    #include "GameSprite.h"
#endif

#ifndef GAME_BOARD
    #include "GameBoard.h"
#endif

class GameApp
{
    protected:
        SDL_TimerID timer;
        bool        done;
        GameContext context;
        int         ResW;
        int         ResH;
        int         CurrentPlayer;
        int         GameStatus;
        int         TurnsLeft;
        bool        GameOver;
        GameBoard   TicTacToeBoard;
        GameTexture CurrentPlayerStorage;


    public:
        enum {
            PLAYER_X,
            PLAYER_O
        };
        enum {
            GAME_NOT_OVER,
            GAMEOVER_X_WINS,
            GAMEOVER_O_WINS,
            GAMEOVER_TIE
        };
    public:
        // Constructor and destructor
        GameApp (void);
        virtual ~GameApp(void);

        // Accessors
        GameContext GetContext(void);
        void SetContext(GameContext theContext);

//        GameSprite GetPlayerX(void);
//        void SetPlayerX(GameSprite thePlayer);

        int GetResW(void);
        void SetResW(int theResW);

        int GetResH(void);
        void SetResH(int theResH);

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
        bool PlayerTakesTurn(int GameBoardCell);
        void SwitchPlayers(void);
        int CheckForWinner(void);
        void ResetGame(void);
        void RenderFrame(void);

};

typedef GameApp* GameAppPtr;
typedef GameApp** GameAppHandle;

const int RUN_GAME_LOOP = 1;
const float MOVEMENT_DISTANCE = 10.0f;
const float ROTATION_AMOUNT = 5.0f;
