/* 
 * File:   ModuleClock.cpp
 * Author: hsaturn
 * 
 * Created on 2 novembre 2014, 22:06
 */

#include "ModuleClock.h"
#include <Streaming.h>

int8_t ModuleClock::msgHandler(uint8_t iMsgType, Message * p)
{
	// TODO tester la différence si on fait une variable locale
	// en prendant p->takeByte() pour éviter de l'appeler par la suite)
	switch (iMsgType)
	{
		case MSG_TYPE_BEGIN:
			// TODO get clock from broadcasting ?
			break;
			
		case MSG_TYPE_READ16:
			if (p->takeByte()==0)	// heure
			{
				uint16_t i(miHour<<6);
				i+=miHour<<5;
				i+=miHour<<2;	// h*100
				i+=miMin;
				p->writeInt16(i);
			}
			p->writeInt16(miDay);
			return MSG_RET_OK;
			
		case MSG_TYPE_WRITE16:
			if (p->takeByte()==0)	// heure
			{
				uint16_t i(p->takeWord());
				miHour=0;
				while(i>=100)
				{
					miHour++; i-=100;
				}
				miMin=i;
			}
			else
			{
				miDay = p->takeWord();
			}
			return MSG_RET_OK;
			
		case MSG_TYPE_MINUTE:
			if (++miMin>59)
			{
				miMin=0;
				miHour++;
				if (miHour>23)
				{
					miHour=0;
					miDay++;
					if (miDay>6)
						miDay=0;
				}
			}
			return MSG_RET_OK;
	}
	return MSG_RET_ERROR;
}