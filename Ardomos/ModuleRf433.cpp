/* 
 * File:   ModuleRf433.cpp
 * Author: hsaturn
 * 
 * Created on 21 december 2014
 */
#include "ModuleRf433.hpp"
#if HAVE_RF433
#include "Message.h"
#include "Module.h"

#define DATA_BIT (PIND & (1<<DATA_RF))

uint32_t ModuleRf433::receive;

ModuleRf433::ModuleRf433() : Module(MODULE_RF_433) {
	receive = 0;
}

int8_t ModuleRf433::msgHandler(uint8_t iMsgType, Message* p) {
	switch (iMsgType) {
		case MSG_TYPE_BEGIN:
			withHoldPin(DATA_RF);
			pinMode(DATA_RF, INPUT);
			attachInterrupt(0, rf433, CHANGE);
			return MSG_RET_OK;

		case MSG_TYPE_READ16:
			return MSG_RET_OK;

		case MSG_TYPE_LOOP:
			if (receive)
			{
				static uint8_t toggle=0;
				toggle=1-toggle;
				digitalWrite(LED_RED, toggle);
				Message* msg=new Message( MSG_TYPE_RF433, MSG_DEST_BROADCAST, MODULE_ALL);
				uint16_t* p=(uint16_t *)(&receive);
				receive=0;
				msg->writeInt16(*p++);
				msg->writeInt16(*p++);
				Module::dispatch(msg);
				delete msg;
			}
			return MSG_RET_OK;

	}
	return MSG_RET_ERROR;
}

void ModuleRf433::rf433()
{
	static uint32_t sum=0;
	static uint32_t count=0;
	static uint32_t min=9999;
	static uint32_t max=0;

	static uint8_t prevBit = 0;
	static uint8_t prevState = 0;	// Attente état bas
	static uint32_t prev_start=0;		// micros état précédent
	static uint32_t bitnr=255;			// Demi bit reçu (manchester) ex: 64=bit 32
	static uint32_t msg;
	
	uint8_t bit;
	uint8_t state = DATA_BIT;
	

	if (prevState == state)	return;		// Pas de changement d'état : return
	uint32_t mic=micros();
	
	uint32_t t = mic-prev_start;
	if (state)	// front montant
	{
		if (t>5000)	// zero trame
		{
			bitnr = 0;
			//Serial << endl << 'S' << t << ' ';
		}
		else if (bitnr==0 && t>2600 && t<2750)	// Msg start
		{
			//Serial << 'T' << t << " : ";
			msg=0;
			count++;
			sum+=t;
			if (t<min) min=t;
			if (t>max) max=t;
			// Serial << "avg=" << sum/count << " count=" << count << " min-max=" << min << '-' << max << endl;
		}
		else if (bitnr<255)
		{
			//Serial << t << ' ';
			if (t>220 && t<350)
			{
				bit = 0;
			}
			else if (t>1260 && t<1480)
			{
				bit = 1;
			}
			// else if (t ?) détecter la fin (oscillo))
			else
			{
				// fin ou parasite
				//Serial << "? " << t << endl;
				bitnr=254;
			}
			if (bitnr&1)
			{
				if (prevBit^bit)
				{
					msg <<=1;
					if (prevBit)
						msg |=1;
				}
				else
				{
					//Serial << "P " << t << " bitnr " << bitnr << " sig=" << prevBit << '.' << bit << '=' << (prevBit^bit) << endl;
					bitnr=254;
				}
			}
			bitnr++;
			if (bitnr==64)
			{
				receive=msg;
				bitnr=255;
			}
		}
		prevBit = bit;
	}

	prev_start = mic;
	prevState = state;
}
#endif