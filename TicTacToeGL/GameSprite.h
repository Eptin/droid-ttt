#define GAME_SPRITE

class GameSprite
{
    protected:
        float worldX;
        float worldY;

        // Direction the sprite is facing.
        float orientation;  // in degrees

    public:
        // Constructor and destructor
        GameSprite();
        virtual ~GameSprite(void);

        // Accessor functions
        float GetWorldX(void);
        void SetWorldX(float x);

        float GetWorldY(void);
        void SetWorldY(float y);

        float GetOrientation(void);
        void SetOrientation(float orientationValue);



        // Movement related functions. I will remove them
        // when I implement physics.
        void MoveLeft(float distance);
        void MoveRight(float distance);
        void MoveUp(float distance);
        void MoveDown(float distance);

};

typedef GameSprite* GameSpritePtr;
typedef GameSprite** GameSpriteHandle;
