#include "GameTexture.h"


// Constructor
GameTexture::GameTexture(void)
{
    width = 0;
    height = 0;
}

// Destructor
GameTexture::~GameTexture(void)
{

}

// Accessors
int GameTexture::GetWidth(void)
{
    return width;
}

void GameTexture::SetWidth(int theWidth)
{
    width = theWidth;
}

int GameTexture::GetHeight(void)
{
    return height;
}

void GameTexture::SetHeight(int theHeight)
{
    height = theHeight;
}

GLint GameTexture::GetInternalFormat(void)
{
    return internalFormat;
}

void GameTexture::SetInternalFormat(GLint theFormat)
{
    internalFormat = theFormat;
}

GLenum GameTexture::GetFormat(void)
{
    return format;
}

void GameTexture::SetFormat(GLenum theFormat)
{
    format = theFormat;
}

GLenum GameTexture::GetType(void)
{
    return type;
}

void GameTexture::SetType(GLenum theType)
{
    type = theType;
}

GLuint GameTexture::GetTextureName(void)
{
    return textureName;
}


// Texture loading functions
void GameTexture::Load(char* filename)
{
    // You can pass the entire name of the file as the filename argument or you can
    // separate the entire name and its file extension into the
    // filename and fileExtension arguments. If you pass the entire name as the
    // filename argument, pass NULL as the fileExtension argument.

    // You can normally pass NULL as the subdirectory. If you place all your
    // texture files in a special directory in your application, you can pass
    // the directory name as the subdirectory argument to make searching faster.

    ReadFromFile(filename);
    Create();
}

void GameTexture::ReadFromFile(char* filename)
{
    CBPNGLoader pngLoader;
    if(pngLoader.Load(filename))
    {
        textureBytes = pngLoader.GetImageData();
        SetWidth(pngLoader.GetProperty(IMAGE_PROPERTY_WIDTH));
        SetHeight(pngLoader.GetProperty(IMAGE_PROPERTY_HEIGHT));
    }

}


void GameTexture::Create(void)
{
    // Creates the texture from the file we loaded earlier.

    //glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
    glGenTextures(1, &textureName);
    glBindTexture(GL_TEXTURE_2D, textureName);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
//    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
//    glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    GLenum error;
    glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format,
                 type, textureBytes);
//    glTexImage2D(GL_TEXTURE_2D, 0, 4, width, height, 0, GL_RGBA,
//                 GL_UNSIGNED_BYTE, textureBytes);
    error = glGetError();

}

void GameTexture::Delete(void)
{
    glDeleteTextures(1, &textureName);
}

