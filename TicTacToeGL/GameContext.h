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
        GameTexture PlayerXTexture;
        GameTexture PlayerOTexture;

        GameTexture XWinsScreen;
        GameTexture OWinsScreen;
        GameTexture TieScreen;

        Uint16 width;
        Uint16 height;

    public:
        // Constructor and destructor
        GameContext(void);
        virtual ~GameContext(void);

        // Accessors
        GameTexture GetBackground(void);
        void SetBackground(GameTexture theBackground);

        GameTexture GetPlayerXTexture(void);
        void SetPlayerXTexture(GameTexture theStorage);

        GameTexture GetPlayerOTexture(void);
        void SetPlayerOTexture(GameTexture theStorage);

        GameTexture GetXWinsScreen(void);
        void SetXWinsScreen(GameTexture theStorage);

        GameTexture GetOWinsScreen(void);
        void SetOWinsScreen(GameTexture theStorage);

        GameTexture GetTieScreen(void);
        void SetTieScreen(GameTexture theStorage);

        Uint16 GetWidth(void);
        void SetWidth(Uint16 theWidth);

        Uint16 GetHeight(void);
        void SetHeight(Uint16 theHeight);

        // Drawing functions
        void DrawBackground(void);
        void DrawTexture(GameTexture theTexture, int x, int y);
};

typedef GameContext* GameContextPtr;
typedef GameContext** GameContextHandle;
