#include "GameApp.h"

// Constructor
GameApp::GameApp(void)
{
    done = false;
}

// Destructor
GameApp::~GameApp(void)
{

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

GameSprite GameApp::GetPlayerX(void)
{
    return playerX;
}

void GameApp::SetPlayerX(GameSprite thePlayer)
{
    playerX = thePlayer;
}


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

int GameApp::GetGameBoardCellStatus(int CellNumber)
{
    return GameBoard[CellNumber];
}

void GameApp::SetGameBoardCellStatus(int CellNumber, int CellStatus)
{

    GameBoard[CellNumber] = CellStatus;

}

// GameBoard Functions
void GameApp::ResetGame()
{
    for(int x = 0; x < 9; x++) SetGameBoardCellStatus(x, BOARD_CELL_EMPTY);
    //SetGameBoardCellStatus(0, BOARD_CELL_PLAYERX);
    //SetGameBoardCellStatus(2, BOARD_CELL_PLAYERX);
    //SetGameBoardCellStatus(7, BOARD_CELL_PLAYERY);
}

// Initialization functions
void GameApp::InitApp(void)
{

    ResetGame();
    ResW = 600;
    ResH = 600;
    InitializeSDL();
    InitializeDrawContext(ResW, ResH);
    InstallTimer();

    //player1 = new GameSprite;
    playerX.SetWorldX(0);
    playerX.SetWorldY(0);
    playerX.SetOrientation(0.0);

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
    GameTexture playerXTextureMap;// = new GameTexture;

    playerXTextureMap.SetInternalFormat(GL_RGBA8);

    playerXTextureMap.SetFormat(GL_RGBA);

    // The texture was created on a big endian computer so reverse
    // the byte order on a little endian computer.
    #if __BIG_ENDIAN__
        playerXTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8);
    #else
        playerXTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8_REV);
    #endif

    // Look in the application bundle for the player ship texture file on Mac OS X.
    // Look in the directory where the executable file resides on other operating systems.
    #if __MACOSX__
        playerXTextureMap.Load("SDLAnimation.app/Contents/Resources/PlayerShip.png");
    #else
        playerXTextureMap.Load("gfx/x.png");
    #endif

    context.SetPlayerXSpriteStorage(playerXTextureMap);

    // Create the sprite storage texture map.
    GameTexture playerYTextureMap;// = new GameTexture;

    playerYTextureMap.SetInternalFormat(GL_RGBA8);

    playerYTextureMap.SetFormat(GL_RGBA);

    // The texture was created on a big endian computer so reverse
    // the byte order on a little endian computer.
    #if __BIG_ENDIAN__
        playerYTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8);
    #else
        playerYTextureMap.SetType(GL_UNSIGNED_INT_8_8_8_8_REV);
    #endif

    // Look in the application bundle for the player ship texture file on Mac OS X.
    // Look in the directory where the executable file resides on other operating systems.
    #if __MACOSX__
        playerYTextureMap.Load("SDLAnimation.app/Contents/Resources/PlayerShip.png");
    #else
        playerYTextureMap.Load("gfx/y.png");
    #endif

    context.SetPlayerYSpriteStorage(playerYTextureMap);

    // Set the size of the context.
    context.SetWidth(width);
    context.SetHeight(height);

//    gluOrtho2D(0.0, width, 0.0, height);

    glViewport(0, 0, width, height);

    glMatrixMode(GL_PROJECTION);

    glLoadIdentity();

    glOrtho(0, width, 0, height, 1, -1);

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
            playerX.MoveLeft(MOVEMENT_DISTANCE);
            playerX.SetOrientation(90.0f);
            break;

        case SDLK_RIGHT:
            playerX.MoveRight(MOVEMENT_DISTANCE);
            playerX.SetOrientation(270.0f);
            break;

        case SDLK_UP:
            playerX.MoveUp(MOVEMENT_DISTANCE);
            playerX.SetOrientation(0.0f);
            break;

        case SDLK_DOWN:
            playerX.MoveDown(MOVEMENT_DISTANCE);
            playerX.SetOrientation(180.0f);
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
        newOrientation = playerX.GetOrientation() + ROTATION_AMOUNT;
        if (newOrientation >= 360.0)
            newOrientation = 0.0f;
        playerX.SetOrientation(newOrientation);
    }

    // Move right
    if (xMovement > 0) {
        newOrientation = playerX.GetOrientation() - ROTATION_AMOUNT;
        if (newOrientation <= 0.0)
            newOrientation = 360.0f;
        playerX.SetOrientation(newOrientation);
    }
}

void GameApp::HandleMouseLeftClick(int x, int y)
{
    int TranslatedX = (x / 200) * 200;
    int TranslatedY = 400 - (y / 200) * 200;

    int GameBoardLocation = (x / 200) + (6 - (y / 200 * 3));
//    printf("Board Location: %d\n", GameBoardLocation);
//    printf("x: %d\n", (x / 200));
//    printf("y: %d\n", 6 - (y / 200 * 3));


    if(GetGameBoardCellStatus(GameBoardLocation) == BOARD_CELL_EMPTY) SetGameBoardCellStatus(GameBoardLocation, BOARD_CELL_PLAYERX);
    //playerX.SetWorldX(TranslatedX);
    //playerX.SetWorldY(TranslatedY);
    //playerX.SetWorldY(39);
}

// Game related functions
void GameApp::GameLoop(void)
{
    RenderFrame();
}

void GameApp::RenderFrame(void)
{
    //glClear(GL_COLOR_BUFFER_BIT);
    context.DrawBackground();
//    playerX.SetWorldX(0);
//    playerX.SetWorldY(0);
//    context.DrawPlayer(playerX);
//    playerX.SetWorldX(200);
//    playerX.SetWorldY(200);

    //context.DrawPlayer(context.GetPlayerXSpriteStorage(), playerX, 0, 0);
    //context.DrawPlayer(context.GetPlayerYSpriteStorage(), playerY, 200, 0);

    for(int x = 0; x < 9; x++) {
        if(GetGameBoardCellStatus(x) == BOARD_CELL_PLAYERX) context.DrawPlayer(context.GetPlayerXSpriteStorage(), playerX, (x % 3 * 200), (x / 3 * 200));
        else
        if(GetGameBoardCellStatus(x) == BOARD_CELL_PLAYERY) context.DrawPlayer(context.GetPlayerYSpriteStorage(), playerY, (x % 3 * 200), (x / 3 * 200));

    }

    SDL_GL_SwapBuffers();
}
