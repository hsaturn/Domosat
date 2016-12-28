#ifndef _MODULE_SCREEN
#define _MODULE_SCREEN

// This file is not a module, but declares
// the constants of the module screen messages
// The implementation depends of what kind of screen
// is connected to the arduino.
// Either none (no screen support, messages are ignored)
// or Arid screen, lcd etc.
#define SCREEN_DATA_16( a, b) (a<<8 + b)

#define SCREEN_PRINT_INT		'n'
#define SCREEN_ICON				'i'

// Available icons (a)
#define SCREEN_ICON_IN			'i'
#define SCREEN_ICON_OUT			'o'
#define SCREEN_ICON_CONNECTED	'c'

// Icons states (b)
#define SCREEN_ICON_OFF			'0'
#define SCREEN_ICON_ON			'1'

#endif
