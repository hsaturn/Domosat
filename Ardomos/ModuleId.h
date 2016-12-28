#ifndef _MODULE_ID_H_
#define _MODULE_ID_H_

#include <Arduino.h>
#include <BitArray.h>
#include <QuadArray.h>

#include "Module.h"

class Message;
class Timer;

#define MAX_ID 64

/**
  * Automatic unique id assignation class within many devices.
  * Id 0 is always reserved (to a master for example)
  * 
  * The module responds to MSG_READ16 with the version of the software
  *
  * Call update() for each received message. This class will take care of
  * - Manage the id assignation of this device
  * - Forward messages that are not for us
  * - Blacklist messages already forwarded (avoid loops)
  * - Store the last known ID to EEPROM (ST_ID_LAST and ST_ID_TEMP address)
  */

class ModuleId : public Module
{
	private:
		static byte miActual;			// Id assigné
		static QuadArray* mpLastForwardNr;

	public: // From Module
		virtual int8_t msgHandler(uint8_t iMsgType, Message* p);

	public:

		ModuleId();

		static byte getId() { return miActual; }

		/**
		  * This method should be called for every incomming message in order
		  * to update / reenforce the allocated id
		  */
		static void update(const Message* pMsg);

		static int getFromLength()
		{
			return 2;
		}

		static char isValidFrom(char cFirst)
		{
			return (cFirst|32)=='f';
		}

		static void begin();

		// Broadcast a msg HELLO
		static void alive();
};

#endif
