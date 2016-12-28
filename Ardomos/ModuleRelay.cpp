
#include "ModuleRelay.h"
#if HAVE_RELAY

#include <SPI.h>
#include "Message.h"
#include "ModuleEeprom.h"
#include <Streaming.h>

//ModuleRelay* poRelay=new ModuleRelay;

ModuleRelay::ModuleRelay()
: Module(MODULE_RELAY) {
}

int8_t ModuleRelay::msgHandler(uint8_t iMsgType, Message* p) {
	switch (iMsgType) {
		case MSG_TYPE_BEGIN:

			// FIXME, this may conflict with Nrf24L01 module
			SPI.begin();
			SPI.setDataMode(SPI_MODE0);
			SPI.setClockDivider(SPI_2XCLOCK_MASK);

			config(ModuleEeprom::read(ST_REL_FLAGS));
			ModuleEeprom::write(ST_REL_FLAGS, (byte) miFlags);

			if (miFlags && RFLG_RESTORE) {
				miState = ModuleEeprom::read(ST_REL_STATE);
			} else {
				miState = 0;
			}
			writeRelay(miState);

			return MSG_RET_OK;

		case MSG_TYPE_WRITE16:
		{
			uint8_t iFlags = p->takeByte();
			uint8_t iState = p->takeByte();

			if (iFlags && RFLG_SETUP) {
				config(iFlags);
				if (iFlags != miFlags) {
					ModuleEeprom::write(ST_REL_FLAGS, (byte) miFlags);
				}
				if ((miFlags && RFLG_RESTORE) && (miState != iState)) {
					ModuleEeprom::write(ST_REL_STATE, (byte) iState);
				}
			}

			writeRelay(iState);

			return MSG_RET_OK;
		}
		case MSG_TYPE_READ:
		case MSG_TYPE_READ16:
			p->takeByte();
			p->writeInt16((int) miFlags);
			p->writeInt16((int) miState);
			return MSG_RET_OK;
	}
	return MSG_RET_ERROR;

}

void ModuleRelay::config(uint8_t iFlags) {
	debug(F("RELAY SETUP "));
	debugln((int) iFlags);

	if (iFlags & RFLG_SPI) {
		holdPin(REL_CS);
		pinMode(REL_CS, OUTPUT);
	} else {
		holdPin(iFlags & RFGL_PINS);
		pinMode(iFlags & RFGL_PINS, OUTPUT);
	}

	miFlags = iFlags;
}

void ModuleRelay::writeRelay(uint8_t iState) {
	debug(F("R="));
	debug(iState);
	if (miFlags & RFLG_SPI) {
		digitalWrite(REL_CS, LOW);
		SPI.transfer(iState);
		digitalWrite(REL_CS, HIGH);
	} else {
		digitalWrite(miFlags & RFGL_PINS, iState);
	}
	miState = iState;
}
#endif