#include "Message.h"
#include "Module.h"
#include "ModuleId.h"
#include "utils.h"
#include <Streaming.h>

Message::Message(byte* p)
	: mbDeleteBytes(false),
	mpMsg(p),
	miDataOffset(MSG_DATA_OFFSET)
{
}

Message::Message(uint8_t iMsg, uint8_t iDest, uint8_t iDestMod)
{
	static int nr(0);
	mpMsg = new byte[MSG_LENGTH];
	mbDeleteBytes = true;
	byte* p(mpMsg);	// Eiter l'utilisation de mpMsg (couteur en mem prog)
	
	p[MSG_HEADER_DEST] = iDest;
	p[MSG_HEADER_FROM] = ModuleId::getId();
	p[MSG_HEADER_MOD ] = iDestMod;
	p[MSG_HEADER_FTL ] = MSG_DEFAULT_TTL;
	p[MSG_HEADER_NR  ] = nr++;	// No conflict with Comm, (internal messages)
	p[MSG_HEADER_MSG ] = iMsg;
	p[MSG_HEADER_SIZE] = 0;

	miDataOffset=MSG_DATA_OFFSET;
}

void Message::swapFromDest()
{
	byte* p(mpMsg);	// Eiter l'utilisation de mpMsg (couteur en mem prog)

	byte b=p[MSG_HEADER_FROM];
	p[MSG_HEADER_FROM] = p[MSG_HEADER_DEST];
	p[MSG_HEADER_DEST] = b;
}

uint8_t Message::decreaseTtl() const
{
	mpMsg[MSG_HEADER_SRCE] = ModuleId::getId();
	if (mpMsg[MSG_HEADER_FTL] & 0xF)
	{
		return (mpMsg[MSG_HEADER_FTL]--) & 0xF;
	}
	Serial.print("TTL OUT ");
	Serial.println(mpMsg[MSG_HEADER_FTL]);
	return 0;
}

void Message::dump() const
{
	const byte* p=getBytes(); 
	Serial << '[';
	for(uint8_t i=0; i<mpMsg[MSG_HEADER_SIZE]+MSG_DATA_OFFSET; i++)
	{
		outHexa2(p[i]);
	}

	Serial << ']';
}

void Message::advance(uint8_t iOffset)
{
	if (miDataOffset+iOffset<MSG_LENGTH)
	{
		miDataOffset+=iOffset;
		if (mpMsg[MSG_HEADER_SIZE] < miDataOffset-MSG_HEADER_SIZE-1)
			mpMsg[MSG_HEADER_SIZE] = miDataOffset-MSG_HEADER_SIZE-1;
	}
	else
		Serial.println(F("ERR MSG59"));
}

byte Message::takeByte()
{
	byte iByte=mpMsg[miDataOffset];
	advance(1);
	return iByte;
}

uint16_t Message::takeWord()
{
	int16_t* p=(int16_t*)(mpMsg+miDataOffset);
	advance(2);
	return *p;
}

// Relative int8 write
void Message::writeInt8(int8_t iInt)
{
	if (miDataOffset<MSG_LENGTH)
	{
		mpMsg[miDataOffset]=iInt;
		advance(1);
	}
}

// Relative int16 write
void Message::writeInt16(int16_t iInt)
{
	//if (miDataOffset<MSG_LENGTH-1)
	{
		writeInt8(iInt & 0xFF);
		writeInt8(iInt >> 8);
		/*
		byte* p=mpMsg+miDataOffset;
		*p++=iInt>>8;
		*p=iInt&0xFF;
		advance(2);
		*/
		/*
		int16_t* p=(int16_t*)(mpMsg+miDataOffset);
		advance(2);
		*p=iInt;*/
	}
}

int16_t Message::read16(uint8_t iModule, uint8_t iDev, uint8_t iOffset)
{
	Message* p=new Message(MSG_TYPE_READ16, MSG_DEST_SELF, iModule);
	p->writeInt8(iDev);
	return dispatch(p, iOffset);
}

int16_t Message::write16(uint8_t iModule, uint8_t iDev, uint16_t iValue)
{
	Message* p=new Message(MSG_TYPE_WRITE16, MSG_DEST_SELF, iModule);
	p->writeInt8(iDev);
	p->writeInt16(iValue);
	return dispatch(p);
}

int16_t Message::write3b(uint8_t iModule, uint8_t iDev, uint8_t i1, uint8_t i2, uint8_t i3)
{
	Message* p=new Message(MSG_TYPE_WRITE3B, MSG_DEST_SELF, iModule);
	p->writeInt8(iDev);
	p->writeInt8(i1);
	p->writeInt8(i2);
	p->writeInt8(i3);
	return dispatch(p);
}


int16_t Message::write3w(uint8_t iModule, uint8_t iDev, uint16_t i1, uint16_t i2, uint16_t i3)
{
	Message* p=new Message(MSG_TYPE_WRITE3W, MSG_DEST_SELF, iModule);
	p->writeInt8(iDev);
	p->writeInt16(i1);
	p->writeInt16(i2);
	p->writeInt16(i3);
	return dispatch(p);
}

int16_t Message::dispatch(Message* p, uint8_t iOffset)
{
	p->writeInt8(0);	// 22 octets... TODO why
	Module::dispatch(p);
	int16_t iRet=p->getDataInt16(1+iOffset);
	delete p;
	return iRet;
}

