#include <Arduino.h>
#include <Arid.h>
#include "ModuleId.h"
#include "Message.h"
#include "Timer.h"
#include "Comm.h"
#include "ModuleEeprom.h"
#include "Debug.h"
#include "ModuleScreen.h"
#include "version.h"
#include <Streaming.h>

//static ModuleId* oId=new ModuleId();

extern Comm* pComm;
//extern Arid tft;
//extern CommIcon* pCommIcons;

byte ModuleId::miActual=1;		// Temporaray id

QuadArray* ModuleId::mpLastForwardNr;	//

ModuleId::ModuleId()
	:
	Module(MODULE_ID)
{
}

void ModuleId::begin()
{
	mpLastForwardNr=new QuadArray(MAX_ID);
	miActual=ModuleEeprom::read(ST_ID_LAST);
	Serial.print(F("ID="));
	Serial.println(ModuleId::getId());
}

int8_t ModuleId::msgHandler(uint8_t iMsgType, Message* p)
{
	if (p)
	{
		update(p);
	}
	switch(iMsgType)
	{
		case MSG_TYPE_BEGIN:
			ModuleId::begin();
                        break;
                        
        case MSG_TYPE_RESET:    // TODO
			return MSG_RET_OK;

		case MSG_TYPE_LOOP:
			return MSG_RET_OK;

		case MSG_TYPE_MINUTE:
			alive();
			return MSG_RET_OK;

		case MSG_TYPE_READ16:
			p->writeInt16((int16_t) VERSION);
			return MSG_RET_OK;
	}
	return Module::msgHandler(iMsgType, p);
}


void ModuleId::update(const Message* pMsg)
{
	byte iMsgFrom = pMsg->getHeaderFrom();
	byte iMsgNr   = pMsg->getHeaderNr();

	if (iMsgFrom!=miActual && pMsg->getHeaderDest()!=miActual)	// Ne pas forwarder les self message 
	{
		uint8_t iLastMsgNr=mpLastForwardNr->get(iMsgFrom);
		if (iMsgNr<iLastMsgNr)
		{
			iMsgNr+=mpLastForwardNr->dim();
		}
		if ((iMsgNr!=iLastMsgNr) && (iMsgNr-iLastMsgNr <= 2))	// Don't resend old messages	// FIXME should be mpLastForwardNr->dim()-1 ?
		{
			// debugln(pMsg->getString());
		//	Serial << "FWD " << iMsgFrom << '#' << iMsgNr;
			mpLastForwardNr->set(iMsgFrom,iMsgNr);
			pComm->resend(pMsg);
		}
		//else
		//	Serial << "SKIP " << iMsgFrom << '#' << iMsgNr << ' ' << iLastMsgNr << '/' << mpLastForwardNr->get(iMsgFrom) << endl;
	}
}

void ModuleId::alive()
{
	Message* p=new Message(MSG_TYPE_HELLO,MSG_DEST_BROADCAST,MODULE_ID);	// Renvoyer un broadcast pour s'identifier
	pComm->send(p);	// FIXME only one comm device ?
	Serial << " alive " << endl;
	delete p;
}
