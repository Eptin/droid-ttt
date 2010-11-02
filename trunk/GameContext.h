#include "SDL.h"
#include "SDL_opengl.h"

#ifndef GAME_TEXTURE
    #include "GameTexture.h"
#endif

#ifndef GAME_SPRITE
    #include "GameSprite.h"
#endif

#define GAME_CONTEXT

class GameContext
{
    protected:
        GameTexture background;
        GameTexture playerXSpriteStorage;
        GameTexture playerYSpriteStorage;

        Uint16 width;
        Uint16 height;

    public:
        // Constructor and destructor
        GameContext(void);
        virtual ~GameContext(void);

        // Accessors
        GameTexture GetBackground(void);
        void SetBackground(GameTexture theBackground);

        GameTexture GetPlayerXSpriteStorage(void);
        void SetPlayerXSpriteStorage(GameTexture theStorage);

        GameTexture GetPlayerYSpriteStorage(void);
        void SetPlayerYSpriteStorage(GameTexture theStorage);

        Uint16 GetWidth(void);
        void SetWidth(Uint16 theWidth);

        Uint16 GetHeight(void);
        void SetHeight(Uint16 theHeight);

        // Drawing functions
        void DrawBackground(void);
        void DrawPlayer(GameSprite player);
        void DrawPlayer(GameTexture playerTexture, GameSprite playerSprite, int x, int y);
};

typedef GameContext* GameContextPtr;
typedef GameContext** GameContextHandle;
