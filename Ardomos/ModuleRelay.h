#ifndef _MODULE_RELAY_H
#define _MODULE_RELAY_H

#include <Arduino.h>
#include "Module.h"

// SPI Mode, Chip Select
#define REL_CS 3

// Relay Module
//
// Write Msg : configure / change relay(s) state(s)
// Read Msg : read setup and relay state

// Flags definition for MSG_TYPE_WRITE (1st byte)
// Flags are change by setting the SETUP bit flag
#define RFLG_SET_STATE	(0x00)

// Bit 6 if 1 for SPI mode, 0 for PIN mode.
// When in PIN mode, bits 0..3 are the PIN used (cannot be D0)

#define RFLG_SETUP		(0x80)	// Other bits are ignore when this bit is not set
#define RFLG_SPI		(0x40)
#define RFLG_RESTORE	(0x20)	// Restore relay state during startup

// These bits are reserved for PIN mode, PIN can never be 0
#define RFGL_PINS		(0x0F)
#define RFGL_PIN3		(0x08)
#define RFGL_PIN2		(0x04)
#define RFGL_PIN1		(0x02)
#define RFGL_PIN0		(0x01)

// Default config to pin mode, with relay connected to REL_CS
#define RFGL_DEFAULT_CONFIG	REL_CS

class ModuleRelay : public Module
{
	private:
		uint8_t miFlags;  	// See MSG_TYPE_WRITE
		uint8_t miState;	// Known state of relays

	private:

		void writeRelay(uint8_t);
		void config(uint8_t);

	public:
		ModuleRelay();

		/**
		  * MSG_TYPE_WRITE (int16_t)
		  *    1st byte : see RFLG_XXX bits definition

		  *    2nd byte : data, in SPI mode one bit per relay, in Direct mode : 1 (on) or 0 (off)
		  *       b0 = relay #0
		  *       b1 = relay #1
		  *       b2 = ...
		  */
		virtual int8_t msgHandler(uint8_t iMsgType, Message* p);
};
#endif
