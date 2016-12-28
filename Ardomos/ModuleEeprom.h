// Affectation des registres EEPROM

#ifndef STORAGE_H_
#define STORAGE_H_
#include <Arduino.h>
#include <EEPROM.h>

#define ST_MAGIC 0

// ID module storage
#define ST_ID_LAST 4
#define ST_ID_TEMP 5

// Relay module storage
#define	 ST_REL_FLAGS	6
#define ST_REL_STATE	7

// Module module
#define ST_MOD_INIT_1 8
#define ST_MOD_INIT_2 9

// Module DHT
#define ST_DHT_DEV	8

// Module PROG start at 0x62
#define ST_PROG 98

#include "Module.h"

class ModuleEeprom : public Module
{
	public:
		static void setup();

		static byte read(int16_t iOffset) { return EEPROM.read(iOffset); }
		static int  read16(int16_t iOffset)
		{
			uint16_t i;
			_read(iOffset, (byte*)&i, 2);
			return i;
		}
		static long readLong(int iOffset)
		{
			long l;
			_read(iOffset,(byte*)&l, 4);
			return l;
		}

		static void write(int16_t iOffset, byte b)	{ _write(iOffset, &b, 1); }
		static void write(int16_t iOffset, int i) { _write(iOffset,(byte*)&i, 2); }
		static void write(int16_t iOffset, long l) { _write(iOffset, (byte*)&l, 4); }

	public:
		ModuleEeprom() : Module(MODULE_EEPROM){}
		virtual int8_t msgHandler(uint8_t iMsgType, Message* p);
 

	private:
		static void _write(int16_t iOffset, byte*, int iSize);
		static void _read(int16_t iOffset, byte*, int iSize);
		
};

#endif
