#include "GameContext.h"

// Constructor and destructor
GameContext::GameContext(void)
{

}

GameContext::~GameContext(void)
{

}


// Accessors
GameTexture GameContext::GetBackground(void)
{
    return background;
}

void GameContext::SetBackground(GameTexture theBackground)
{
    background = theBackground;
}

GameTexture GameContext::GetPlayerXTexture(void)
{
    return PlayerXTexture;
}

void GameContext::SetPlayerXTexture(GameTexture theStorage)
{
    PlayerXTexture = theStorage;
}

GameTexture GameContext::GetPlayerOTexture(void)
{
    return PlayerOTexture;
}

void GameContext::SetPlayerOTexture(GameTexture theStorage)
{
    PlayerOTexture = theStorage;
}

GameTexture GameContext::GetXWinsScreen(void)
{
    return XWinsScreen;
}

void GameContext::SetXWinsScreen(GameTexture theStorage)
{
    XWinsScreen = theStorage;
}

GameTexture GameContext::GetOWinsScreen(void)
{
    return OWinsScreen;
}

void GameContext::SetOWinsScreen(GameTexture theStorage)
{
    OWinsScreen = theStorage;
}

GameTexture GameContext::GetTieScreen(void)
{
    return TieScreen;
}

void GameContext::SetTieScreen(GameTexture theStorage)
{
    TieScreen = theStorage;
}


Uint16 GameContext::GetWidth(void)
{
    return width;
}

void GameContext::SetWidth(Uint16 theWidth)
{
    width = theWidth;
}

Uint16 GameContext::GetHeight(void)
{
    return height;
}

void GameContext::SetHeight(Uint16 theHeight)
{
    height = theHeight;
}


// Drawing functions
void GameContext::DrawBackground(void)
{
    // Draw the whole background texture.
    GLfloat textureLeft = 0.0f;
    GLfloat textureRight = 1.0f;
    GLfloat textureTop = 1.0f;
    GLfloat textureBottom = 0.0f;

    // Have the background cover the entire viewport.
    GLfloat left = 0.0f;
//    GLfloat right = width;
    GLfloat right = background.GetWidth();
    GLfloat bottom = 0.0f;
    //GLfloat top = height;
    GLfloat top = background.GetHeight();

    // Set up the drawing of the tile
    glEnable(GL_TEXTURE_2D);
    glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
    glBindTexture(GL_TEXTURE_2D, background.GetTextureName());

    // Draw the tile
    glBegin(GL_QUADS);

        // Lower left corner
        glTexCoord2f(textureLeft, textureBottom);
        glVertex3f(left, bottom, 0.0);

        // Lower right corner
        glTexCoord2f(textureRight, textureBottom);
        glVertex3f(right, bottom, 0.0);

        // Upper right corner
        glTexCoord2f(textureRight, textureTop);
        glVertex3f(right, top, 0.0);

        // Upper left corner
        glTexCoord2f(textureLeft, textureTop);
        glVertex3f(left, top, 0.0);

    glEnd();

}

void GameContext::DrawTexture(GameTexture theTexture, int x, int y)
{
    glPushMatrix();
    GLfloat textureLeft = 0.0f;
    GLfloat textureRight = 1.0f;
    GLfloat textureTop = 1.0f;
    GLfloat textureBottom = 0.0f;

    GLfloat left = x;
    GLfloat right = left + theTexture.GetWidth();
    GLfloat bottom = y;
    GLfloat top = bottom + theTexture.GetHeight();


    //GLfloat textureColor[] = {1.0, 1.0, 1.0, 1.0};

    // Set up the drawing of the player.
    glEnable(GL_TEXTURE_2D);

    // Tell OpenGL not to draw transparent pixels
    glAlphaFunc(GL_GREATER, 0);
    //glAlphaFunc(GL_LESS, 1);
    glEnable(GL_ALPHA_TEST);

    //glTexEnvfv(GL_TEXTURE_ENV, GL_TEXTURE_ENV_COLOR, textureColor);
    //glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_BLEND);
    glBindTexture(GL_TEXTURE_2D, theTexture.GetTextureName());

    // Set up the rotation
    //glMatrixMode(GL_TEXTURE);
    glMatrixMode(GL_TEXTURE);
    glPushMatrix();
    glLoadIdentity();

    // Tell OpenGL to rotate around the center of the texture.
//    glTranslatef(0.5, 0.5, 0.5);
//    glRotatef(player.GetOrientation(), 0.0, 0.0, 1.0);
//    glTranslatef(-0.5, -0.5, -0.5);

    // Draw the sprite
    glBegin(GL_QUADS);

//    // Lower left corner
//    glTexCoord2f(textureLeft, textureBottom);
//    glVertex3f(left, bottom, 0.0);
//
//    // Lower right corner
//    glTexCoord2f(textureRight, textureBottom);
//    glVertex3f(right, bottom, 0.0);
//
//    // Upper right corner
//    glTexCoord2f(textureRight, textureTop);
//    glVertex3f(right, top, 0.0);
//
//    // Upper left corner
//    glTexCoord2f(textureLeft, textureTop);
//    glVertex3f(left, top, 0.0);

// Lower left corner
    glTexCoord2f(textureLeft, textureBottom);
    glVertex3f(left, bottom, 0.0);

    // Lower right corner
    glTexCoord2f(textureRight, textureBottom);
    glVertex3f(right, bottom, 0.0);

    // Upper right corner
    glTexCoord2f(textureRight, textureTop);
    glVertex3f(right, top, 0.0);

    // Upper left corner
    glTexCoord2f(textureLeft, textureTop);
    glVertex3f(left, top, 0.0);

    glEnd();

    glPopMatrix();

}
