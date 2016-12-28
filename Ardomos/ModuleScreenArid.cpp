#if HAVE_MODULE_DISPLAY
#include "ModuleScreenArid.h"
#include "CommIcon.h"
#include "Message.h"
#include <Arid.h>

// static ModuleScreenArid* oModuleScreen=new ModuleScreenArid;
extern CommIcon* pCommIcons;
extern Arid tft;	// FIXME ??? Il devrait être instancié et stocké ici !
ModuleScreenArid::ModuleScreenArid()
	:
	Module(MODULE_SCREEN)
{
};

int8_t ModuleScreenArid::msgHandler(uint8_t iMsgType, Message* p)
{
	switch(iMsgType)
	{
		case MSG_TYPE_BEGIN:
			// FIXME holdPin...
			return MSG_RET_OK;

		// case MSG_TYPE_READ16:
		// p->writeInt16(9999);


		//case MSG_TYPE_LOOP:
		//	return MSG_RET_OK;

		case MSG_TYPE_WRITE3W:
			p->takeByte();	// Ignore device
			displayInt(p);
			break;

		case MSG_TYPE_WRITE3B:
		case MSG_TYPE_DATA:
		{
			interpretData(p);
			break;
		}

		default:
			return Module::msgHandler(iMsgType, p);
	}
        return MSG_RET_OK;
}

void ModuleScreenArid::interpretData(Message* p)
{
	byte iCommand;
	/*byte iDev=*/p->takeByte();	// Ignoré (device de sortie)

	while((iCommand = p->takeByte()))
	{
		switch(iCommand)
		{
		case SCREEN_ICON:
			drawIcon(p);
			break;

		case SCREEN_PRINT_INT:
			displayInt(p);
			break;

		default:
			return;

		}
	}
}

/**
 * Display an int on the screen
 * p.data is pointing on the byte
 * right after the SCREEN_PRINT_INT command.
 */
void ModuleScreenArid::displayInt(Message* p)
{
	/*Serial.print("INT ");
	Serial.print((uint16_t)8);
	Serial.print((uint16_t)p->takeByte());
	Serial.print("x");
	Serial.print((uint16_t)p->takeByte());
	Serial.print(" ");
	Serial.print("v=");
	Serial.print((uint16_t)(p->takeWord()));
	Serial.print(" c=");
	Serial.print((uint16_t)p->takeWord()); */
	tft.draw(p->takeByte(),p->takeByte(), p->takeWord(),p->takeWord());
}

void ModuleScreenArid::drawIcon(Message* p)
{
	byte iIcon = p->takeByte();
	byte iValue = p->takeByte();

	if (pCommIcons)
	{
		switch(iIcon)
		{
			case SCREEN_ICON_CONNECTED:
				if (iValue == SCREEN_ICON_OFF)
				{
					pCommIcons->setConnected(0);
				}
				else if (iValue == SCREEN_ICON_ON)
				{
					pCommIcons->setConnected(1);
				}
				break;

			case SCREEN_ICON_IN:
				pCommIcons->setIn(iValue);
				break;

			case SCREEN_ICON_OUT:
				pCommIcons->setOut(iValue);
				break;
		}
	}
}
#endif