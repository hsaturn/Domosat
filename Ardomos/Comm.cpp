// vim: syntax=arduino
#include "Comm.h"
#include <MirfHardwareSpiDriver.h>
#include "Timer.h"
#include "Message.h"
#include "ModuleScreen.h"
#include "ModuleId.h"
#include "Debug.h"
#include <Streaming.h>

Comm* pComm = new Comm();

Comm::Comm()
: Module(MODULE_NRF24),
mpMessage(0) { }

void Comm::serialInit()
{
	static bool bInit(true);

	if (bInit)
	{
		bInit = false;
		Serial.begin(57600);
		while (!Serial);
	}
}

uint8_t Comm::begin(int iCsnPin, int iCePin)
{
	debugln(F("nRF24L01"));
	Mirf.csnPin = iCsnPin;
	Mirf.cePin = iCePin;
	debug(F("Mirf cs : "));
	debugln((int) iCsnPin);
	debug(F("Mirf ce : "));
	debugln((int) iCePin);

	Mirf.spi = &MirfHardwareSpi;
	Mirf.init();
	Mirf.setTADDR((byte *) "12345");
	Mirf.setRADDR((byte*) "12345");
	Mirf.payload = MSG_LENGTH;
	Mirf.config();
	return MSG_RET_OK;
}

bool Comm::send(Message* pMsg)
{
	static byte iMsgCounter = 0;
	//Serial.println("SENDING MSG");

	iMsgCounter = (iMsgCounter + 1)&0xF; // FIXME, this should be Id::mpLastForwardNr->dim()
	pMsg->setHeaderNr(iMsgCounter);
	pMsg->resetTtl();

	return resend(pMsg);
}

bool Comm::resend(const Message* pMsg)
{
	if (pMsg->decreaseTtl())
	{
		pMsg->dump();
		Message::write3b(MODULE_SCREEN, 0, SCREEN_ICON, SCREEN_ICON_OUT, SCREEN_ICON_ON);
		Mirf.payload = MSG_LENGTH;

		//debug("SEND : ");
		//pMsg->dump();

		Mirf.send(pMsg->getBytes());

		Timer oTimeOut(10);
		while (Mirf.isSending())
		{
			if (oTimeOut.nextEvent())
			{
				Serial.println(F("TIMEOUT"));
			}
		}

		return true; // FIXME
	}
	else
	{
		return false;
	}
}

Message* Comm::receive()
{
	static byte msg[MSG_LENGTH];
	
	if (mpMessage)
	{
		delete mpMessage;
		mpMessage = 0;
	}
	if (!Mirf.rxFifoEmpty()) // FIXME, ca ne fonctionne pas
	{
		Mirf.payload = MSG_LENGTH;
		Mirf.getData((byte*) & msg[0]);

		if (msg[MSG_HEADER_MSG])
		{
			// Serial.println("RX");
			Message::write3b(MODULE_SCREEN, 0, SCREEN_ICON, SCREEN_ICON_IN, SCREEN_ICON_ON);
			mpMessage = new Message(msg);

			mpMessage->setFlag(MSG_FLAG_RECEIVED);

			Serial.print("CIN ");
			mpMessage->dump();
			Serial.println();

		}
	}
	return mpMessage;
}

int8_t Comm::msgHandler(uint8_t iMsgType, Message* p)
{
	switch (iMsgType)
	{
		case MSG_TYPE_BEGIN:
			holdPin(MIRF_CSN);
			holdPin(MIRF_CE);
			return begin(MIRF_CSN, MIRF_CE);

		case MSG_TYPE_LOOP:
		{
			Message* p = receive();
			if (p)
			{
				ModuleId::update(p); // FIXME
				Module::dispatch(p);
			}
		}
	}
	return MSG_RET_OK;
}

