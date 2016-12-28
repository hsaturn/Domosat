#ifndef _VERSION_H_
#define _VERSION_H_

// Major = 0..5
#define VERSION_MAJOR 1

// Minor = 0..9
#define VERSION_MINOR 1

// Build = 0..999
#define VERSION_BUILD 7

// Version is an int
// XXYY where XX is the Major and YY the minor

#define VERSION ( VERSION_MAJOR*10000 + VERSION_MINOR*1000 + VERSION_BUILD )

#endif
