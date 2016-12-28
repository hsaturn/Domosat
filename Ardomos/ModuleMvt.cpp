/* 
 * File:   ModuleIr.cpp
 * Author: hsaturn
 * 
 * Created on 23 novembre 2014, 05:34
 */
#include <Arduino.h>
#include "ModuleMvt.h"
#include "Message.h"

#if HAVE_MVT

ModuleMvt::ModuleMvt() : Module(MODULE_DETECT_MVT) {
}

int8_t ModuleMvt::msgHandler(uint8_t iMsgType, Message* p) {
	switch (iMsgType) {
		case MSG_TYPE_BEGIN:
			return MSG_RET_OK;

		case MSG_TYPE_READ16:
			p->writeInt8(0);
			p->writeInt16(digitalRead(PIN_IR));
			return MSG_RET_OK;

		case MSG_TYPE_LOOP:
			return MSG_RET_OK;

	}
	return MSG_RET_ERROR;
}

#endif