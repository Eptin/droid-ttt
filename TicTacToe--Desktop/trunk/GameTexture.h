#ifndef GAMETEXTURE_H
#define GAMETEXTURE_H

#include "SDL.h"
#include "GL/gl.h"
#include "GL/glext.h"
#include "GL/glu.h"
#include "CBPNGLoader.h"


class GameTexture
{
    protected:
        int width;
        int height;

        // These members are parameters to the OpenGL
        // function glTexImage2D()
        GLint internalFormat;
        GLenum format;
        GLenum type;

        GLuint textureName;
        char * textureBytes;



    public:
        // Constructor
        GameTexture(void);

        // Destructor
        virtual ~GameTexture(void);

        // Accessors
        int GetWidth(void);
        void SetWidth(int theWidth);

        int GetHeight(void);
        void SetHeight(int theHeight);

        GLint GetInternalFormat(void);
        void SetInternalFormat(GLint theFormat);

        GLenum GetFormat(void);
        void SetFormat(GLenum theFormat);

        GLenum GetType(void);
        void SetType(GLenum theType);

        GLuint GetTextureName(void);

        // Functions for loading a texture from a file
        void Load(char* filename);
        void ReadFromFile(char* filename);
        void Create(void);
        void Delete(void);

};

typedef GameTexture* GameTexturePtr;
typedef GameTexture** GameTextureHandle;

const int kAutoCloseFile = 1;

#endif
