// TODO (corey#1#): Need to work on error handling
#ifndef CBPNGLOADER_H
#define CBPNGLOADER_H

#include <iostream>
#include <fstream>
#include <png.h>

#define PNGSIGSIZE 8


typedef int             pInt;
typedef unsigned int    pUInt;

enum ImageProperty {
            IMAGE_PROPERTY_WIDTH,
            IMAGE_PROPERTY_HEIGHT,
            IMAGE_PROPERTY_BITDEPTH,
            IMAGE_PROPERTY_CHANNELS,
            IMAGE_PROPERTY_COLOR_TYPE
        };

class CBPNGLoader
{
    protected:
        std::ifstream   m_Source;
        png_bytep *     m_RowPtrs;
        png_structp     m_PngPtr;
        png_infop       m_InfoPtr ;
        char*           m_ImageData;

        unsigned int    m_ImageWidth;
        unsigned int    m_ImageHeight;
        unsigned int    m_ImageBitdepth;
        unsigned int    m_ImageChannels;
        unsigned int    m_ImageColorType;

    protected:
        bool checkForValidPngSig(std::istream& source);
        bool readPngInfo(void);
        bool readPngImageData(void);

    public:
        CBPNGLoader(void);
        ~CBPNGLoader(void);

        bool Init(void);
        bool Load(char * fileName);
        pUInt GetProperty(ImageProperty ImageProperty);
        char * GetImageData(void);

        bool Close(void);
};

typedef CBPNGLoader* CBPNGLoaderPtr;
typedef CBPNGLoader** CBPNGLoaderHandle;

#endif
