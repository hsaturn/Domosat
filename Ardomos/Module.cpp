#include <Arduino.h>
#include "Module.h"
#include "ModuleId.h"
#include "Message.h"
#include <Timer.h>
#include "ModuleEeprom.h"
#include "Comm.h"
#include <Streaming.h>
#include <MemoryFree.h>

extern Comm* pComm;

Module* Module::mpModules[MAX_MODULES];
uint8_t Module::miCount = 0;

// FIXME pins used par defaut selon l'avr (SPI etc...)
// #ifdef __AVR_ATmega2560__
uint32_t Module::siPins = 0;

Module::Module(uint8_t cName, uint8_t iId)
:
mcName(cName) {
	registerModule(this);
}

void
Module::begin() {
	if (ModuleEeprom::read16(ST_MOD_INIT_1) != (int) 0xCAFE) {
		debugln(F("COLD RST"));
		dispatchAllMods(MSG_TYPE_RESET);
	}
	dispatchAllMods(MSG_TYPE_BEGIN);
}

void
Module::registerModule(Module* pNew) {
	if (miCount < MAX_MODULES)
		mpModules[miCount++] = pNew;
	else
		delete pNew; // Destructor should take care of manage the error message ?
}

int8_t
Module::msgHandler(uint8_t iMsgType, Message* p) {

	switch (iMsgType) {
		case MSG_TYPE_LOOP:
			return MSG_RET_OK;

		case MSG_TYPE_SECOND:
			//Serial << F("MEM ") << freeMemory() << endl;
#if 0
	static uint16_t iCounter;
	static uint16_t iMinLps = 9999;
	static uint16_t iMaxLps = 0;
			iCounter = 0;
			debug(F("LPS:"));
			if (iMinLps > iCounter) iMinLps = iCounter;
			if (iMaxLps < iCounter) iMaxLps = iCounter;
			debug(iMinLps);
			debug('-');
			debug(iCounter);
			debug('-');
			debugln(iMaxLps);
#endif

			return MSG_RET_OK;
			
		case MSG_TYPE_LISTM:
			Serial.print(F("Domosat "));
			for (uint8_t i = 0; i < miCount; i++) {
				Serial.print((char) mpModules[i]->mcName);
			}
			Serial.println();
			return MSG_RET_STOP_PROPAGATION;

		case MSG_TYPE_PING:
			// FIXME todo, check if we have to add the route
			return MSG_RET_OK;
	}
	/*
	Serial.print(F("?msg "));
	Serial.print((char) iMsgType);
	Serial.print(F(" for "));
	Serial.println((char) mcName);*/
	return MSG_RET_UNKNOWN_MSG;
}

void
Module::loop() {
	static Timer oSecond(1000);
	static uint8_t iMin = 0;

	if (oSecond.nextEvent()) {
		dispatchAllMods(MSG_TYPE_SECOND);
		if (++iMin >= 60) {
			dispatchAllMods(MSG_TYPE_MINUTE);
			iMin = 0;
		}
	} else {
		dispatchAllMods(MSG_TYPE_LOOP);
	}

}

// Dispatch vers TOUS les modules internes
int8_t
Module::dispatchAllMods(uint8_t iMsgType) {
	for (uint8_t i = 0; i < miCount; i++) {
		mpModules[i]->msgHandler(iMsgType, 0); // FIXME return code ?
	}
	return MSG_RET_OK; // FIXME
}

int8_t
Module::dispatch(Message* p) {
	int8_t iRet = MSG_RET_NODEST;
	if (p && (p->getHeaderDest() == MSG_DEST_BROADCAST || p->getHeaderDest() == ModuleId::getId() || p->getHeaderDest() == MSG_DEST_SELF)) {
		uint8_t cDestName = p->getHeaderMod();

		for (uint8_t i = 0; i < miCount; i++) {
			if (cDestName == MODULE_ALL || cDestName == mpModules[i]->mcName) {
				p->rewind();
				iRet = mpModules[i]->msgHandler(p->getHeaderMsg(), p); // FIXME return code ?
				if (iRet == MSG_RET_STOP_PROPAGATION) break;
				// Renvoyer une réponse en lower, mais ne pas renvoyer si c'était déja un msg lower.
				if (iRet == MSG_RET_OK && ((p->getHeaderMsg() & 32)==0))
				{
					p->lowerMsg();
					p->swapFromDest();
					pComm->send(p);
					p->swapFromDest();
					p->upperMsg();
				}
			}
		}
	}
	return iRet;
}

void
Module::holdPin(uint8_t iPin) {
	debug(F("HOLD #"));
	debug(iPin);
	debug('-');
	debugln((char) mcName);
	uint32_t iBinary = 1 << iPin;
	if (siPins & iBinary) {
		debug(F("PIN CONFLICT #"));
		debug(iPin);
		debug('-');
		debugln((char) mcName);
	}
	siPins |= iBinary;
}

void
Module::withHoldPin(uint8_t iPin) {
	uint32_t iBinary = 1 << iPin;
	debug(F("UNHOLD #"));
	debug(iPin);
	debug('-');
	debugln((char) mcName);
	siPins &= ~iBinary;
}
