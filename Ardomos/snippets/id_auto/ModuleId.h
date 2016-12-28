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
		static byte miTemp;				// 0 si Id permanent
		static byte miActual;			// Id temporaire ou assigné (selon miTemp)
		static Timer tmrResetBurn;		// Timer pour reset des forward de messages.
		static Timer tmrAssign;			// Timer pour s'auto assigner un id
		static QuadArray* mpLastForwardNr;
		static BitArray* mpKnown;

	public: // From Module
		virtual int8_t msgHandler(uint8_t iMsgType, Message* p);

	public:

		ModuleId();

		/** Return a string containing a short msg to send in order
		  * to identify us with the yet known ID.
		  *
		  * @return string (fxx or Fx)
		  */
		static void fillFrom(byte*);

		/** Return the temporary or permanent id
		  * @see isTemporary()
		  */
		static byte getId() { return miActual; }

		/**
		  * Returns true if the getId is a temporary id
		  **/
		static bool isTemporary() { return miTemp!=0; }


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
