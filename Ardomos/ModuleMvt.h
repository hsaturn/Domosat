/* 
 * File:   ModuleIr.h
 * Author: hsaturn
 *
 * Created on 23 novembre 2014, 05:34
 */

#ifndef MODULEMVT_H
#define	MODULEMVT_H

#include <Arduino.h>
#include "Module.h"

#define PIN_IR 7

class ModuleMvt : public Module {
public:
	ModuleMvt();
	virtual int8_t msgHandler(uint8_t iMsgType, Message* p);

};

#endif	/* MODULEMVT_H */

