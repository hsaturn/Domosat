#ifndef _MODULE_SCREEN_ARID
#define _MODULE_SCREEN_ARID

#if HAVE_DISPLAY

#include "Module.h"
#include "ModuleScreen.h"

/**
 * Screen Module, Arid implementation
 *
 * The ModuleScreen should be an abstract base class, allowing to implement different
 * way to display information.
 */
class ModuleScreenArid : public Module
{
public:
	ModuleScreenArid();

	virtual int8_t msgHandler(uint8_t iMsgType, Message* p);

private:
	void interpretData(Message* p);
	void displayInt(Message* p);
	void drawIcon(Message* p);
};

#endif
#endif
