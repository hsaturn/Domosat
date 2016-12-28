#include "ModuleEeprom.h"
#include <EEPROM.h>
#include "Debug.h"
#include "utils.h"
#include <Streaming.h>

// 150 bytes redondant avec le module usb

int8_t
ModuleEeprom::msgHandler(uint8_t iMsgType, Message* p) {
	if (p) {
		int16_t iOffset(p->takeWord());
		int16_t cnt(p->takeByte());
		
		Serial << "OFF=" << iOffset << " cnt=" << cnt << ' ';

		for (int16_t i = 0; i < cnt; i++)
			switch (iMsgType) {
				case MSG_TYPE_WRITE:
				{
					uint8_t o = p->takeByte();
					EEPROM.write(iOffset+i,o);
					break;
				}
				case MSG_TYPE_READ:
					p->writeInt8(EEPROM.read(iOffset + i));
					break;
			}
	}
	return MSG_RET_OK;
}

void
ModuleEeprom::setup() {
#define MAGIC_DATA 0xA01FEAB9

	if (readLong(ST_MAGIC) != (long) MAGIC_DATA) {
		Serial.println(F("INIT DATA"));
		Serial.println(readLong(ST_MAGIC));
		ModuleEeprom::write(ST_MAGIC, (long) MAGIC_DATA);
		ModuleEeprom::write(ST_ID_TEMP, 1);
	}
}

void
ModuleEeprom::_write(int16_t iOffset, byte* p, int iSize) {
	debug(F("EE "));
	debug(iOffset);
	debug(' ');
	debugln(iSize);
	while (iSize--) {
		EEPROM.write(iOffset++, *p++);
	}
}

void
ModuleEeprom::_read(int16_t iOffset, byte* p, int iSize) {
	while (iSize--) {
		*p++ = EEPROM.read(iOffset++);
	}
}
