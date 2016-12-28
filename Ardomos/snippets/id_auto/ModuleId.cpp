#include <Arduino.h>
#include <Arid.h>
#include "ModuleId.h"
#include "Message.h"
#include "Timer.h"
#include "Comm.h"
#include "Storage.h"
#include "Debug.h"
#include "ModuleScreen.h"
#include "version.h"

//static ModuleId* oId=new ModuleId();

extern Comm* pComm;
//extern Arid tft;
//extern CommIcon* pCommIcons;

byte ModuleId::miTemp;			// Temporary affect
byte ModuleId::miActual=1;		// Temporaray id

BitArray* ModuleId::mpKnown;		// known id bitfield

QuadArray* ModuleId::mpLastForwardNr;	//

Timer ModuleId::tmrResetBurn(5000);
Timer ModuleId::tmrAssign(20000);

ModuleId::ModuleId()
	:
	Module(MODULE_ID)
{
}

void ModuleId::begin()
{
	debug(F("ID begin"));
	miTemp=Storage::read(ST_ID_TEMP);
	tmrResetBurn.reset();
	tmrAssign.reset();
	mpKnown=new BitArray(MAX_ID);
	mpLastForwardNr=new QuadArray(MAX_ID);
	if (miTemp==0)	// On a deja eu un id par le passé
	{
		miActual=Storage::read(ST_ID_LAST);
		debug(F("ID Last was "));
		debugln(miActual);
	}
	miTemp=analogRead(A0) & 255;	// Reconsiderer l'id comme temporaire (au cas ou (et au cas ou ST_ID_TEMP est faux))
}

void ModuleId::fillFrom(byte* p)
{
	p[MSG_HEADER_ID0 ] = miTemp;
	p[MSG_HEADER_FROM] = miActual;
}

int8_t ModuleId::msgHandler(uint8_t iMsgType, Message* p)
{
	static uint16_t iCounter;
	static uint16_t iMinLps=9999;
	static uint16_t iMaxLps=0;

	if (p)
	{
		update(p);
	}
	switch(iMsgType)
	{
		case MSG_TYPE_BEGIN:
			ModuleId::begin();
			return MSG_RET_OK;

		case MSG_TYPE_LOOP:
			iCounter++;
			return MSG_RET_OK;

		case MSG_TYPE_SECOND:
#if 0
			debug(F("LPS:"));
			if (iMinLps>iCounter) iMinLps=iCounter;
			if (iMaxLps<iCounter) iMaxLps=iCounter;
			debug(iMinLps);
			debug('-');
			debug(iCounter);
			debug('-');
			debugln(iMaxLps);
#endif
			iCounter=0;
			// Mets à jour la liste d'icones
			{
				static uint8_t last_id=-1;
				uint16_t iCol=GREEN;

				// auto assignation apres pTmrAssign
				if (miTemp && tmrAssign.nextEvent())
				{
					debug(F("*** AUTO ASSIGNATION : "));
					debugln(miActual);
					miTemp=0;
					Storage::write(ST_ID_LAST, miActual);
					Storage::write(ST_ID_TEMP, miTemp);
				}

				if (last_id != miActual)
				{
					// tft.draw(1,1,last_id,BLACK);
					Message::write3w(MODULE_SCREEN, 0, 0x0101, last_id, BLACK);	// print at 1,1; last_id, black
				}

				if (miTemp)
				{
					iCol=RED;
					// pCommIcons->setConnected(0);	// FIXME 
				}
				else
				{
					Message::write3b(MODULE_SCREEN, 0, SCREEN_ICON, SCREEN_ICON_CONNECTED, 1);
					// pCommIcons->setConnected(1);
				}

				last_id=miActual;
				Message::write3w(MODULE_SCREEN, 0, 0x0101, last_id, iCol);	// print at 1,1; last_id, iCol
				//tft.draw(1,1,last_id,iCol);

			}
			if (miTemp)
			{
				alive();
			}

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
	if (tmrResetBurn.nextEvent())
	{
		mpLastForwardNr->reset();
	}

	byte iMsgId0  = pMsg->getHeaderId0();
	byte iMsgFrom = pMsg->getHeaderId1();
	byte iMsgNr   = pMsg->getHeaderNr();

	if (iMsgId0 == 0)	// 1st byte ==0, iMsgFrom is already reserved.
	{
		mpKnown->set(iMsgFrom);	// Invalidate that id
	}
	else if (iMsgId0 == miTemp)		
	{
		// We probably receive an echo of our packet
		// so do nothing
		// Fixme, we could check the number of message ?
	}
	else if (iMsgFrom == miActual)	// Un arduino demande notre id : invalider sa demande !
	{
		debug(F(" INVALIDATE DEMAND f"));
		debugln((int)iMsgFrom);
		alive();
	}
	else // L'emetteur iMsgFrom n'était pas nous (iMsgId0 != miTemp) => Invalider l'id
	{
		mpKnown->set(iMsgFrom);
	}

	// Now search for a temporay id if miActual is taken
	while (miTemp && (mpKnown->get(miActual)))
	{
		debug(F("NEXT ID "));
		miActual++;
		tmrAssign.reset();
		if (miActual>=MAX_ID)
		{
			miActual=1;
			debugln(F("ALL KNOWN ?"));
			mpKnown->reset();
			break;
		}
	}
	if (iMsgFrom!=miActual)	// Ne pas forwarder les self message 
	{
		uint8_t iLastMsgNr=mpLastForwardNr->get(iMsgFrom);
		if (iMsgNr<iLastMsgNr)
		{
			iMsgNr+=mpLastForwardNr->dim();
		}
		if (iMsgNr-iLastMsgNr <= 2 )	// Don't resend old messages	// FIXME should be mpLastForwardNr->dim()-1 ?
		{
			debug(F("FORWARD "));
			debug((int)iMsgFrom);
			debug(' ');
			debug(F("ME "));
			debug(' ');
			debug((int)miActual);
			// debugln(pMsg->getString());
			mpLastForwardNr->set(iMsgFrom,iMsgNr);
			pComm->resend(pMsg);
		}
	}
}

void ModuleId::alive()
{
	debugln(F("ALIVE"));
	Message* p=new Message(MSG_TYPE_HELLO,MSG_DEST_BROADCAST,MODULE_ID);	// Renvoyer un broadcast pour s'identifier
	pComm->send(p);	// FIXME only one comm device
	delete p;
}
