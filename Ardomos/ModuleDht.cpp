#include "ModuleDht.h"
#include "Message.h"
#include "ModuleEeprom.h"
#include "Comm.h"

#if HAVE_DHT
// static ModuleDht* oDht=new ModuleDht;

ModuleDht::ModuleDht()
	:
	Module(MODULE_TEMPERATURE),
	miPin(255)
{
};

#include <dht.h>

int8_t ModuleDht::msgHandler(uint8_t iMsgType, Message* p)
{
	switch(iMsgType)
	{
		case MSG_TYPE_BEGIN:
			debugln(F("BEGIN T"));
			config(ModuleEeprom::read(ST_DHT_DEV), false);
			return MSG_RET_OK;

		case MSG_TYPE_RESET:
			debugln(F("RST T"));
			config(DHT_DEFAULT_CONFIG, true);
			return MSG_RET_OK;

		case MSG_TYPE_SECOND:
			static bool bRead(false);
			bRead = !bRead;
			if (bRead)
			{
				if (miDev && DHT_22)
				{
					miLastRead=moDht.read22(miPin);
				}
				else
				{
					miLastRead=moDht.read11(miPin);
				}
			}
			return MSG_RET_OK;

		case MSG_TYPE_WRITE:
			{
				uint8_t iDev = p->takeByte();
				config(iDev, true);
			}
			return MSG_RET_OK;

		case MSG_TYPE_READ:
		case MSG_TYPE_READ16:
			{
				byte iDev=p->takeByte();
				if (iDev)
				{
					// NYI : Device is not the default DHT device
				}
				if (miLastRead==0)
				{
					int16_t t(moDht.temperature);
					int16_t h(moDht.humidity);
#if DHT_MUL10
#else
					t=t<<3+t<<2;
					h=h<<3+h<<2;
#endif
					
					p->writeInt16(t);
					p->writeInt16(h);
					return MSG_RET_OK;
				}
				else
				{
					debugln(F("ERR"));
					p->writeInt16(9999);
					p->writeInt16(miLastRead);
					return MSG_RET_ERROR;
				}
			}
			break;

		default:
			return Module::msgHandler(iMsgType, p);
	}
}

void ModuleDht::config(uint8_t iDev, bool bReconfigure)
{
	miDev=iDev;
	if (miPin != 255)
	{
		withHoldPin(miPin);
	}
	// FIXME DISTINCTION HT11 / HT22
	#ifdef __AVR_ATmega2560__
		miPin=(iDev & 0xF) + 54;
	#else
		miPin=(iDev & 0xF) + 14;
	#endif
	debug(F("DHT pin #A"));
	debugln(iDev & 0xF);
	holdPin(miPin);

	if (bReconfigure)
	{
		ModuleEeprom::write(ST_DHT_DEV, iDev);
	}
}

#endif