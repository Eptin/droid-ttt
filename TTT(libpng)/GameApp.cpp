#include "GameApp.h"

// Constructor
GameApp::GameApp(void)
{
    done = false;
    printf("GameApp constructed...\n");
}

// Destructor
GameApp::~GameApp(void)
{
    printf("GameApp Destructed...\n");
}


// Accessors
GameContext GameApp::GetContext(void)
{
    return context;
}

void GameApp::SetContext(GameContext theContext)
{
    context = theContext;
}

//GameSprite GameApp::GetPlayerX(void)
//{
//    return PlayerX;
//}
//
//void GameApp::SetPlayerX(GameSprite thePlayer)
//{
//    PlayerX = thePlayer;
//}


int GameApp::GetResW(void)
{
    return ResW;
}

void GameApp::SetResW(int theResW)
{
    ResW = theResW;
}

int GameApp::GetResH(void)
{
    return ResH;
}

void GameApp::SetResH(int theResH)
{
    ResH = theResH;
}

// Initialization functions
void GameApp::InitApp(void)
{
    #ifndef GL_ARB_texture_non_power_of_two
        printf("DOES NOT SUPPORT NON POWER OF TWO!!\n");
    #else
        printf("DOES SUPPORT NON POWER OF TWO!!\n");
    #endif
    printf("InitApp...\n");
    ResW = 600;
    ResH = 600;
//    ilInit();
    InitializeSDL();
    InitializeDrawContext(ResW, ResH);
    ResetGame();
    InstallTimer();

    //player1 = new GameSprite;
//    PlayerX.SetWorldX(0);
//    PlayerX.SetWorldY(0);
//    PlayerX.SetOrientation(0.0);
    //context.DrawTexture(context.GetBackground(),0 , 0);
}

void GameApp::InitializeSDL(void)
{
    int error;
    SDL_Surface* drawContext;

    error = SDL_Init(SDL_INIT_EVERYTHING);

    // Create a double-buffered draw context
    SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1);

    // Use 32-bit color: 8 bits of red, green, blue, and alpha.
    SDL_GL_SetAttribute(SDL_GL_RED_SIZE, 8);
    SDL_GL_SetAttribute(SDL_GL_GREEN_SIZE, 8);
    SDL_GL_SetAttribute(SDL_GL_BLUE_SIZE, 8);
    SDL_GL_SetAttribute(SDL_GL_ALPHA_SIZE, 8);

    Uint32 flags;
    flags = SDL_OPENGL;// | SDL_FULLSCREEN;
    drawContext = SDL_SetVideoMode(ResW, ResH, 0, flags);

}

void GameApp::InitializeDrawContext(Uint16 width, Uint16 height)
{
    // Create the draw context
	/*
    context = new GameContext;
	if (context == NULL)
		// Quit
        done = true;
   */

    // Create the background texture map
    GameTexture textureMap;// = new GameTexture;

    textureMap.SetInternalFormat(GL_RGBA8);

    textureMap.SetFormat(GL_RGBA);

    // The texture was created on a big endian computer so reverse
    // the byte order on a little endian computer.
    #if __BIG_ENDIAN__
        textureMap.SetType(GL_UNSIGNED_INT_8_8_8_8);
    #else
        textureMap.SetType(GL_UNSIGNED_INT_8_8_8_8_REV);
    #endif

    // Look in the application bundle for the background texture file on Mac OS X.
    // Look in the directory where the executable file resides on other operating systems.
    #if __MACOSX__
        textureMap.Load("SDLAnimation.app/Contents/Resources/Background.png");
    #else
        textureMap.Load("gfx/grid.png");
    #endif

    context.SetBackground(textureMap);


    // Create the sprite storage texture map.
    GameTexture PlayerXTextureMap;// = new GameTexture;

    PlayerXTextureMap.SetInternalFormat(GL_RGBA8);

    PlayerXTextureMap.SetFormat(GL_RGBA);

    // The texture was created on a big endian computer so reverse
    // the byte order on a little endian computer.
    #if __BIG_ENDIAN__
        PlayerXTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8);
    #else
        PlayerXTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8_REV);
    #endif

    // Look in the application bundle for the player ship texture file on Mac OS X.
    // Look in the directory where the executable file resides on other operating systems.
    #if __MACOSX__
        PlayerXTextureMap.Load("SDLAnimation.app/Contents/Resources/PlayerShip.png");
    #else
        PlayerXTextureMap.Load("gfx/x.png");
    #endif

    context.SetPlayerXTexture(PlayerXTextureMap);


    // Create the sprite storage texture map.
    GameTexture PlayerOTextureMap;// = new GameTexture;

    PlayerOTextureMap.SetInternalFormat(GL_RGBA8);

    PlayerOTextureMap.SetFormat(GL_RGBA);

    // The texture was created on a big endian computer so reverse
    // the byte order on a little endian computer.
    #if __BIG_ENDIAN__
        PlayerOTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8);
    #else
        PlayerOTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8_REV);
    #endif

    // Look in the application bundle for the player ship texture file on Mac OS X.
    // Look in the directory where the executable file resides on other operating systems.
    #if __MACOSX__
        PlayerOTextureMap.Load("SDLAnimation.app/Contents/Resources/PlayerShip.png");
    #else
        PlayerOTextureMap.Load("gfx/o.png");
    #endif

    context.SetPlayerOTexture(PlayerOTextureMap);


    // Create the sprite storage texture map.
    GameTexture XWinsTextureMap;// = new GameTexture;

    XWinsTextureMap.SetInternalFormat(GL_RGBA8);

    XWinsTextureMap.SetFormat(GL_RGBA);

    // The texture was created on a big endian computer so reverse
    // the byte order on a little endian computer.
    #if __BIG_ENDIAN__
        XWinsTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8);
    #else
        XWinsTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8_REV);
    #endif

    // Look in the application bundle for the player ship texture file on Mac OS X.
    // Look in the directory where the executable file resides on other operating systems.
    #if __MACOSX__
        XWinsTextureMap.Load("SDLAnimation.app/Contents/Resources/PlayerShip.png");
    #else
        XWinsTextureMap.Load("gfx/xwins.png");
    #endif

    context.SetXWinsScreen(XWinsTextureMap);


    // Create the sprite storage texture map.
    GameTexture YWinsTextureMap;// = new GameTexture;

    YWinsTextureMap.SetInternalFormat(GL_RGBA8);

    YWinsTextureMap.SetFormat(GL_RGBA);

    // The texture was created on a big endian computer so reverse
    // the byte order on a little endian computer.
    #if __BIG_ENDIAN__
        YWinsTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8);
    #else
        YWinsTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8_REV);
    #endif

    // Look in the application bundle for the player ship texture file on Mac OS X.
    // Look in the directory where the executable file resides on other operating systems.
    #if __MACOSX__
        YWinsTextureMap.Load("SDLAnimation.app/Contents/Resources/PlayerShip.png");
    #else
        YWinsTextureMap.Load("gfx/owins.png");
    #endif

    context.SetOWinsScreen(YWinsTextureMap);


    // Create the sprite storage texture map.
    GameTexture TieTextureMap;// = new GameTexture;

    TieTextureMap.SetInternalFormat(GL_RGBA8);

    TieTextureMap.SetFormat(GL_RGBA);

    // The texture was created on a big endian computer so reverse
    // the byte order on a little endian computer.
    #if __BIG_ENDIAN__
        TieTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8);
    #else
        TieTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8_REV);
    #endif

    // Look in the application bundle for the player ship texture file on Mac OS X.
    // Look in the directory where the executable file resides on other operating systems.
    #if __MACOSX__
        TieTextureMap.Load("SDLAnimation.app/Contents/Resources/PlayerShip.png");
    #else
        TieTextureMap.Load("gfx/tie.png");
    #endif

    context.SetTieScreen(TieTextureMap);




    CurrentPlayerStorage = context.GetPlayerXTexture();

    // Set the size of the context.
    context.SetWidth(width);
    context.SetHeight(height);

    // ShortCut
//    gluOrtho2D(0.0, width, 0.0, height);

    // The Long way...
    glViewport(0, 0, width, height);

    glMatrixMode(GL_PROJECTION);

    glLoadIdentity();

    // Use this one to use the Bottom Left Origin
    glOrtho(0, width, 0, height, 1, -1);

    // Use this one to match the way SDL maps screen, with top left origin
    //glOrtho(0, width, height, 0, 1, -1);

    glMatrixMode(GL_MODELVIEW);

    glLoadIdentity();

}

void GameApp::InstallTimer(void)
{
    timer = SDL_AddTimer(30, GameLoopTimer, this);
}

Uint32 GameApp::GameLoopTimer(Uint32 interval, void* param)
{
    //GameAppPtr currentApp = (GameAppPtr)param;

    //currentApp->GameLoop();

    // Create a user event to call the game loop.
    SDL_Event event;

    event.type = SDL_USEREVENT;
    event.user.code = RUN_GAME_LOOP;
    event.user.data1 = 0;
    event.user.data2 = 0;
    SDL_PushEvent(&event);

    return interval;
}


// Cleanup functions
void GameApp::Cleanup(void)
{
    SDL_bool success;
    success = SDL_RemoveTimer(timer);

    CurrentPlayerStorage.Delete();
    context.GetPlayerXTexture().Delete();
    context.GetPlayerOTexture().Delete();
    context.GetBackground().Delete();

    SDL_Quit();
}

// Event-related functions
void GameApp::EventLoop(void)
{
    SDL_Event event;

    while((!done) && (SDL_WaitEvent(&event))) {
        switch(event.type) {
            case SDL_USEREVENT:
                HandleUserEvents(&event);
                break;

            case SDL_KEYDOWN:
                HandleKeyPress(&event);
                break;

            case SDL_MOUSEMOTION:
                HandleMouseMovement(&event);
                break;

            case SDL_MOUSEBUTTONDOWN: {
                HandleMouseLeftClick(event.button.x, event.button.y);
                break;
            }
            case SDL_QUIT:
                done = true;
                break;

            default:
                break;
        }   // End switch

    }   // End while

}

void GameApp::HandleUserEvents(SDL_Event* event)
{
    switch (event->user.code) {
        case RUN_GAME_LOOP:
            GameLoop();
            break;

        default:
            break;
    }
}

void GameApp::HandleKeyPress(SDL_Event* event)
{
    switch(event->key.keysym.sym) {
        case SDLK_LEFT:
//            PlayerX.MoveLeft(MOVEMENT_DISTANCE);
//            PlayerX.SetOrientation(90.0f);
            break;

        case SDLK_RIGHT:
//            PlayerX.MoveRight(MOVEMENT_DISTANCE);
//            PlayerX.SetOrientation(270.0f);
            break;

        case SDLK_UP:
//            PlayerX.MoveUp(MOVEMENT_DISTANCE);
//            PlayerX.SetOrientation(0.0f);
            break;

        case SDLK_DOWN:
//            PlayerX.MoveDown(MOVEMENT_DISTANCE);
//            PlayerX.SetOrientation(180.0f);
            break;

        default:
            break;
    }
}

void GameApp::HandleMouseMovement(SDL_Event* event)
{
    float newOrientation;

    Sint16 xMovement;
    xMovement = event->motion.xrel;

    // Move left
    if (xMovement < 0) {
//        newOrientation = PlayerX.GetOrientation() + ROTATION_AMOUNT;
//        if (newOrientation >= 360.0)
//            newOrientation = 0.0f;
//        PlayerX.SetOrientation(newOrientation);
    }

    // Move right
    if (xMovement > 0) {
//        newOrientation = PlayerX.GetOrientation() - ROTATION_AMOUNT;
//        if (newOrientation <= 0.0)
//            newOrientation = 360.0f;
//        PlayerX.SetOrientation(newOrientation);
    }
}

void GameApp::HandleMouseLeftClick(int x, int y)
{
    int TranslatedX = (x / 200) * 200;
    int TranslatedY = 400 - (y / 200) * 200;

    int GameBoardCell = (x / 200) + (6 - (y / 200 * 3));

    if(PlayerTakesTurn(GameBoardCell) == true) SwitchPlayers();

    switch(GameStatus = CheckForWinner())
    {
        case GAMEOVER_X_WINS:
//                    printf("PLAYER X WINS!!\n");
                    break;

        case GAMEOVER_O_WINS:
//                    printf("PLAYER O WINS!!\n");
                    break;

        case GAMEOVER_TIE:
//                    printf("ITS A TIE!!\n");
                    break;
        default:
                    break;
    }

//    printf("Board Location: %d\n", GameBoardCell);
//    printf("x: %d\n", (x / 200));
//    printf("y: %d\n", 6 - (y / 200 * 3));

}

// Game related functions
void GameApp::GameLoop(void)
{
    RenderFrame();
}

bool GameApp::PlayerTakesTurn(int GameBoardCell)
{
//    printf("Cell %d: %d\n", GameBoardCell, TicTacToeBoard.GetCellStatus(GameBoardCell));
    if(TicTacToeBoard.GetCellStatus(GameBoardCell) == TicTacToeBoard.BOARD_CELL_EMPTY) {
        TicTacToeBoard.SetCellStatus(GameBoardCell, CurrentPlayer + 1);
        --TurnsLeft;
        // Turn was accepted
        return true;
    }
    // Turn was rejected
    return false;
}

void GameApp::SwitchPlayers(void)
{
//    printf("Player was: %d\n", CurrentPlayer);
    if(CurrentPlayer == PLAYER_X) {
        CurrentPlayer = PLAYER_O;
        CurrentPlayerStorage = context.GetPlayerOTexture();
    }
    else {
        CurrentPlayer = PLAYER_X;
        CurrentPlayerStorage = context.GetPlayerXTexture();
    }
//    printf("Player is: %d\n", CurrentPlayer);

}

void GameApp::ResetGame(void)
{
    TicTacToeBoard.ResetBoard();
    TurnsLeft = 9;
    CurrentPlayer = PLAYER_X;
    GameStatus = GAME_NOT_OVER;
    GameOver = false;
}

int GameApp::CheckForWinner(void)
{
    // Check All Rows for a winner...
    //int Cell0, Cell1, Cell2;
    for(int x = 0; x < 3; x++){
//        if(TicTacToeBoard.GetCellStatus(x * 3) == TicTacToeBoard.BOARD_CELL_EMPTY
//           && TicTacToeBoard.GetCellStatus(x * 3 + 1) != TicTacToeBoard.BOARD_CELL_EMPTY
//           && TicTacToeBoard.GetCellStatus(x * 3 + 2) != TicTacToeBoard.BOARD_CELL_EMPTY)
//            {
                // Check Row X for Player X
                if(TicTacToeBoard.GetCellStatus(x * 3) == TicTacToeBoard.BOARD_CELL_PLAYERX
                   && TicTacToeBoard.GetCellStatus(x * 3 + 1) == TicTacToeBoard.BOARD_CELL_PLAYERX
                    && TicTacToeBoard.GetCellStatus(x * 3 + 2) == TicTacToeBoard.BOARD_CELL_PLAYERX)
                        return GAMEOVER_X_WINS;

                // Check Column X for PLayer X
                if(TicTacToeBoard.GetCellStatus(x) == TicTacToeBoard.BOARD_CELL_PLAYERX
                   && TicTacToeBoard.GetCellStatus(x + 3) == TicTacToeBoard.BOARD_CELL_PLAYERX
                    && TicTacToeBoard.GetCellStatus(x + 6) == TicTacToeBoard.BOARD_CELL_PLAYERX)
                        return GAMEOVER_X_WINS;

                // Check Row X for PLayer O
                if(TicTacToeBoard.GetCellStatus(x * 3) == TicTacToeBoard.BOARD_CELL_PLAYERO
                   && TicTacToeBoard.GetCellStatus(x * 3 + 1) == TicTacToeBoard.BOARD_CELL_PLAYERO
                    && TicTacToeBoard.GetCellStatus(x * 3 + 2) == TicTacToeBoard.BOARD_CELL_PLAYERO)
                        return GAMEOVER_O_WINS;

                // Check Column X for Player O
                if(TicTacToeBoard.GetCellStatus(x) == TicTacToeBoard.BOARD_CELL_PLAYERO
                   && TicTacToeBoard.GetCellStatus(x + 3) == TicTacToeBoard.BOARD_CELL_PLAYERO
                    && TicTacToeBoard.GetCellStatus(x + 6) == TicTacToeBoard.BOARD_CELL_PLAYERO)
                        return GAMEOVER_O_WINS;


//            }
    }
    // Check Diagonals for X
    if(TicTacToeBoard.GetCellStatus(0) == TicTacToeBoard.BOARD_CELL_PLAYERX
       && TicTacToeBoard.GetCellStatus(4) == TicTacToeBoard.BOARD_CELL_PLAYERX
        && TicTacToeBoard.GetCellStatus(8) == TicTacToeBoard.BOARD_CELL_PLAYERX)
            return GAMEOVER_X_WINS;

    if(TicTacToeBoard.GetCellStatus(6) == TicTacToeBoard.BOARD_CELL_PLAYERX
       && TicTacToeBoard.GetCellStatus(4) == TicTacToeBoard.BOARD_CELL_PLAYERX
        && TicTacToeBoard.GetCellStatus(2) == TicTacToeBoard.BOARD_CELL_PLAYERX)
            return GAMEOVER_X_WINS;

    // Check Diagonals for Y
    if(TicTacToeBoard.GetCellStatus(0) == TicTacToeBoard.BOARD_CELL_PLAYERO
       && TicTacToeBoard.GetCellStatus(4) == TicTacToeBoard.BOARD_CELL_PLAYERO
        && TicTacToeBoard.GetCellStatus(8) == TicTacToeBoard.BOARD_CELL_PLAYERO)
            return GAMEOVER_O_WINS;

    if(TicTacToeBoard.GetCellStatus(6) == TicTacToeBoard.BOARD_CELL_PLAYERO
       && TicTacToeBoard.GetCellStatus(4) == TicTacToeBoard.BOARD_CELL_PLAYERO
        && TicTacToeBoard.GetCellStatus(2) == TicTacToeBoard.BOARD_CELL_PLAYERO)
            return GAMEOVER_O_WINS;


//    printf("Turns Left: %d\n", TurnsLeft);
//    printf("Game Status: %d\n", GameStatus);

    // If theres no more turns, and no winner... then its a TIE!!
    if(TurnsLeft == 0) return GAMEOVER_TIE;

    // If nothing else, the game isn't over!
    return GAME_NOT_OVER;
}

void GameApp::RenderFrame(void)
{
    //glClear(GL_COLOR_BUFFER_BIT);
    //context.DrawBackground();
    //context.DrawTexture(context.GetBackground(),0 , 0);

    if(GameStatus == GAME_NOT_OVER)
    {
        context.DrawBackground();
        //context.DrawTexture(context.GetBackground(),0 , 0);
        for(int x = 0; x < 9; x++)
        {
            if(TicTacToeBoard.GetCellStatus(x) == TicTacToeBoard.BOARD_CELL_PLAYERX) context.DrawTexture(context.GetPlayerXTexture(), (x % 3 * 200), (x / 3 * 200));
            else
            if(TicTacToeBoard.GetCellStatus(x) == TicTacToeBoard.BOARD_CELL_PLAYERO) context.DrawTexture(context.GetPlayerOTexture(), (x % 3 * 200), (x / 3 * 200));
        }
    }
    if(GameStatus == GAMEOVER_X_WINS)
    {
        context.DrawTexture(context.GetXWinsScreen(), 0, 0);
        SDL_GL_SwapBuffers();
        SDL_Delay(3000);
        ResetGame();
    }
    if(GameStatus == GAMEOVER_O_WINS)
    {
        context.DrawTexture(context.GetOWinsScreen(), 0, 0);
        SDL_GL_SwapBuffers();
        SDL_Delay(3000);
        ResetGame();
    }
    if(GameStatus == GAMEOVER_TIE)
    {
        context.DrawTexture(context.GetTieScreen(), 0, 0);
        SDL_GL_SwapBuffers();
        SDL_Delay(3000);
        ResetGame();
    }


    SDL_GL_SwapBuffers();
//    ILenum Error;
//    while ((Error = ilGetError()) != IL_NO_ERROR) {
//        printf("%d: %s/n", Error, iluErrorString(Error));
//    }
//
//    GLenum glError;
//    while ((glError = glGetError()) != GL_NO_ERROR) {
//        printf("%d: %s\n", glError, gluErrorString(glError));
//    }

}
