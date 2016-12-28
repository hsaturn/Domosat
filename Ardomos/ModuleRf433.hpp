/* 
 * File:   ModuleRf433.h
 * Author: hsaturn
 *
 * Created on 21 december 2014
 */

#ifndef MODULE_RF433_H
#define	MODULE_RF433_H

#include <Arduino.h>
#include "Module.h"

#define DATA_RF 2

class ModuleRf433 : public Module
{
	public:
		ModuleRf433();
		virtual int8_t msgHandler(uint8_t iMsgType, Message* p);
	
	private:
		static void rf433();
		static uint32_t receive;	// Last 32 bits code received or 0
};

#endif	/* MODULE_RF433_H */

