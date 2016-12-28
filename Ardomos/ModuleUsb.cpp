#include <Arduino.h>
#include "ModuleUsb.h"
#include "Message.h"
#include "ModuleId.h"
#include "Comm.h"
#include "ModuleEeprom.h"
#include <Streaming.h>
#include "utils.h"

extern Comm* pComm;
//static ModuleUsb* oUsb = new ModuleUsb;

ModuleUsb::ModuleUsb()
:
Module(MODULE_USB) {
};

uint8_t getHexa1(String &s) {
    uint8_t c = s.charAt(0);
	if (c)
	{
		s.remove(0, 1);
		if (c < 0x40)
			return c - '0';
		else
			return (c & 0xDF) - 55;
	}
}

uint8_t getHexa2(String &s) {
	// Plus court que s.trim()
    while (s.charAt(0) == ' ')
        s.remove(0, 1);
    return (getHexa1(s) << 4)+getHexa1(s);
}

uint16_t getHexa4(String &s) {
    return (getHexa2(s) << 8)+getHexa2(s);
}

// 292 octets


// 174 octets


/* 280 octets */
void ModuleUsb::sendMessage(String &sCmd) {
    uint8_t iMsg = sCmd.charAt(2);
    uint8_t iMod = sCmd.charAt(3);

    Message* p = new Message(iMsg, getHexa2(sCmd), iMod);
    getHexa2(sCmd); // Remove MSG / MOD dest to get DATA section
    while (sCmd.length()) {
        p->writeInt8(getHexa2(sCmd));
    }
    Serial << '=' << Module::dispatch(p) << ' ';

    // Afficher le message modifié ou non lors du dispatch.
    p->dump();

    // If module is not (only) for us, sends through radio
    if (p->getHeaderDest() == MSG_DEST_BROADCAST || (p->getHeaderDest() != ModuleId::getId() && p->getHeaderDest() != MSG_DEST_SELF)) {
        // FIXME, Module::dispatch responsability
        // Serial << F(" OUT");
        pComm->send(p);
    }
    delete p;
}

int8_t ModuleUsb::msgHandler(uint8_t iMsgType, Message* p) {
	
	// Dump incoming messages
	if (p)
	{
		Serial.print(F("IN "));
		p->dump();
		Serial << endl;
	}
	
    switch (iMsgType) {
        case MSG_TYPE_LOOP:
            if (Serial.available()) {

                char c(Serial.read());

                if (c != '\r' && c != '\n') {
                    msBuffer += c;
                } else {
                    Serial << F("USB [") << msBuffer << ']';

                    char cType = msBuffer.charAt(0);
                    msBuffer.remove(0, 1);

                    switch (cType & 0xDF) {

                        case 'M':
                            sendMessage(msBuffer);
                            break;

                        default:
                            Serial << F("ERR") << cType;
							msBuffer.remove(0,9999);
                    }
                    Serial << endl;
                }
            }
    }
    return MSG_RET_OK;
}
