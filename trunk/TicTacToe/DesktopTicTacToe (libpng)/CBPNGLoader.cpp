// TODO (corey#1#): Need to work on error handling
#include "CBPNGLoader.h"

using namespace std;

// This is needed to tell libpng to read via streams...
void userReadData(png_structp m_PngPtr, png_bytep data, png_size_t length)
{

    //Here we get our IO pointer back from the read struct.
    //This is the parameter we passed to the png_set_read_fn() function.
    //Our std::istream pointer.
    png_voidp a = png_get_io_ptr(m_PngPtr);
    //Cast the pointer to std::istream* and read 'length' bytes into 'data'
    ((std::istream*)a)->read((char*)data, length);
}

CBPNGLoader::CBPNGLoader(void)
{

}

CBPNGLoader::~CBPNGLoader(void)
{

}

bool CBPNGLoader::checkForValidPngSig(std::istream& source)
{
    //Allocate a buffer of 8 bytes, where we can put the file signature.
    png_byte pngsig[PNGSIGSIZE];
    pInt is_png = 0;

    //Read the 8 bytes from the stream into the sig buffer.
    source.read((char*)pngsig, PNGSIGSIZE);

    //Check if the read worked...
    if (!source.good()) return false;

    //Let LibPNG check the sig. If this function returns 0, everything is OK.
    is_png = png_sig_cmp(pngsig, 0, PNGSIGSIZE);
    return (is_png == 0);
}

bool CBPNGLoader::Init(void)
{
    //Here we create the png read struct. The 3 NULL's at the end can be used
    //for your own custom error handling functions, but we'll just use the default.
    //if the function fails, NULL is returned. Always check the return values!
    m_PngPtr = png_create_read_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);
    if (!m_PngPtr) {
        std::cerr << "ERROR: Couldn't Init png read struct" << std::endl;
        return 0; //Do your own error recovery/handling here
    }

    //Here we create the png info struct.
    //Note that this time, if this function fails, we have to clean up the read struct!
    m_InfoPtr = png_create_info_struct(m_PngPtr);
    if (!m_InfoPtr) {
        std::cerr << "ERROR: Couldn't Init png info struct" << std::endl;
        png_destroy_read_struct(&m_PngPtr, (png_infopp)0, (png_infopp)0);
        return 0; //Do your own error recovery/handling here
    }

    png_set_read_fn(m_PngPtr,(voidp)&m_Source, userReadData);
    return 1;
}

bool CBPNGLoader::readPngInfo(void)
{
    //Set the amount signature bytes we've already read:
    //We've defined PNGSIGSIZE as 8;
    png_set_sig_bytes(m_PngPtr, PNGSIGSIZE);

    //Now call png_read_info with our m_PngPtr as image handle, and infoPtr to receive the file info.
    png_read_info(m_PngPtr, m_InfoPtr);


    m_ImageWidth =  png_get_image_width(m_PngPtr, m_InfoPtr);
    m_ImageHeight = png_get_image_height(m_PngPtr, m_InfoPtr);

    //bits per CHANNEL! note: not per pixel!
    m_ImageBitdepth   = png_get_bit_depth(m_PngPtr, m_InfoPtr);
    //Number of channels
    m_ImageChannels   = png_get_channels(m_PngPtr, m_InfoPtr);
    //Color type. (RGB, RGBA, Luminance, luminance alpha... palette... etc)
    m_ImageColorType = png_get_color_type(m_PngPtr, m_InfoPtr);
}

bool CBPNGLoader::readPngImageData(void)
{
    //Here's one of the pointers we've defined in the error handler section:
    //Array of row pointers. One for every row.
    m_RowPtrs = new png_bytep[m_ImageHeight];

    //Alocate a buffer with enough space.
    //(Don't use the stack, these blocks get big easilly)
    //This pointer was also defined in the error handling section, so we can clean it up on error.
    m_ImageData = new char[m_ImageWidth * m_ImageHeight * m_ImageBitdepth * m_ImageChannels / 8];
    //This is the length in bytes, of one row.
    const unsigned int stride = m_ImageWidth * m_ImageBitdepth * m_ImageChannels / 8;

    //A little for-loop here to set all the row pointers to the starting
    //Adresses for every row in the buffer

    for (size_t i = 0; i < m_ImageHeight; i++) {
        //Set the pointer to the data pointer + i times the row stride.
        //Notice that the row order is reversed with q.
        //This is how at least OpenGL expects it,
        //and how many other image loaders present the data.
        pUInt q = (m_ImageHeight- i - 1) * stride;
        m_RowPtrs[i] = (png_bytep)m_ImageData + q;
    }

    //And here it is! The actuall reading of the image!
    //Read the imagedata and write it to the adresses pointed to
    //by rowptrs (in other words: our image databuffer)
    png_read_image(m_PngPtr, m_RowPtrs);
}

bool CBPNGLoader::Load(char * fileName)
{
    Init();
    m_Source.open (fileName, ios::binary );
    //First, we validate our stream with the validate function I just mentioned
    if (!checkForValidPngSig(m_Source)) {
        std::cerr << "ERROR: Data is not valid PNG-data" << std::endl;
        return 0; //Do your own error recovery/handling here
    }

    readPngInfo();
    readPngImageData();
    Close();
    return 1;
}

pUInt CBPNGLoader::GetProperty(ImageProperty ImageProperty)
{
    switch (ImageProperty)
    {
        case IMAGE_PROPERTY_WIDTH:
            return m_ImageWidth;
            break;
        case IMAGE_PROPERTY_HEIGHT:
            return m_ImageHeight;
            break;
        case IMAGE_PROPERTY_BITDEPTH:
            return m_ImageBitdepth;
            break;
        case IMAGE_PROPERTY_CHANNELS:
            return m_ImageChannels;
            break;
        case IMAGE_PROPERTY_COLOR_TYPE:
            return m_ImageColorType;
            break;
        default:
            break;
    }
}

char * CBPNGLoader::GetImageData(void) { return m_ImageData; };

bool CBPNGLoader::Close(void)
{
    //Delete the row pointers array....
    delete[] (png_bytep)m_RowPtrs;
    //And don't forget to clean up the read and info structs !
    png_destroy_read_struct(&m_PngPtr, &m_InfoPtr,(png_infopp)0);
    m_Source.close();
}



