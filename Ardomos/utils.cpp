/* 
 * File:   utils.cpp
 * Author: hsaturn
 *
 * Created on 3 novembre 2014, 00:29
 */

#include "utils.h"
#include <Streaming.h>

void outHexa(uint8_t i)
{
    if (i>=10) i= 'A'+i-10-'0';
    Serial << (char)('0'+i);
}

void outHexa2(uint8_t i)
{
    outHexa(i>>4);
    outHexa(i&0xF);
    Serial << ' ';
}

