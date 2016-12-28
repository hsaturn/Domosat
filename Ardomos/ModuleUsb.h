#ifndef _MODULE_USB_
#define _MODULE_USB_

#include <Arduino.h>
#include "Module.h"

class ModuleUsb : public Module
{
	private:
		String msBuffer;

	public:
		ModuleUsb();

		virtual int8_t msgHandler(uint8_t iMsgType, Message* p);
		
		private:
			void sendMessage(String& s);
			
			void dumpEeprom(String& s);
			void writeEeprom(String &s);
};

#endif
