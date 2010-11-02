#include "GameSprite.h"

// Constructor
GameSprite::GameSprite(void)
{

}

// Destructor
GameSprite::~GameSprite(void)
{

}

// Accessor functions
float GameSprite::GetWorldX(void)
{
    return worldX;
}

void GameSprite::SetWorldX(float x)
{
    worldX = x;
}

float GameSprite::GetWorldY(void)
{
    return worldY;
}

void GameSprite::SetWorldY(float y)
{
    worldY = y;
}

float GameSprite::GetOrientation(void)
{
    return orientation;
}

void GameSprite::SetOrientation(float orientationValue)
{
    orientation = orientationValue;
}

// Movement related functions. I will remove them
// when I implement physics.
void GameSprite::MoveLeft(float distance)
{
    worldX = worldX - distance;
}

void GameSprite::MoveRight(float distance)
{
    worldX = worldX + distance;
}

void GameSprite::MoveUp(float distance)
{
    worldY = worldY + distance;
}

void GameSprite::MoveDown(float distance)
{
    worldY = worldY - distance;
}
