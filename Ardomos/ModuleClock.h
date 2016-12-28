/* 
 * File:   ModuleClock.h
 * Author: hsaturn
 *
 * Created on 2 novembre 2014, 22:06
 */

#ifndef MODULECLOCK_H
#define	MODULECLOCK_H

#include <Arduino.h>
#include "Module.h"

class ModuleClock : public Module {
public:
	ModuleClock():Module(MODULE_CLOCK), miMin(0), miHour(0),miDay(0){}
	virtual int8_t msgHandler(uint8_t iMsgType, Message* p);
	
private:
	uint8_t miMin;
	uint8_t miHour;
	uint8_t miDay;

};

#endif	/* MODULECLOCK_H */

