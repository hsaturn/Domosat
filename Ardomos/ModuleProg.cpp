/* 
 * File:   ModuleProg.cpp
 * Author: hsaturn
 * 
 * Created on 29 octobre 2014, 21:11
 */

#include "ModuleProg.hpp"
#include "ModuleEeprom.h"
#include "utils.h"
#include <MemoryFree.h>
#include <Timer.h>
#include <Streaming.h>
#include <avr/wdt.h>

Lifo<int16_t, 3>* mLifo;
Message* mp = 0; // Message vers extérieure en attente de réponse (out_read))

#define pdebug(a)  /*Serial << a*/
//static ModuleProg* oProg = new ModuleProg;
// Nombre maxi de micro programmes
#define PROG_MAX_UP 12

void
ModuleProg::next() {
	if (++mUp >= PROG_MAX_UP) {
		mUp = 0;
	}
	mPc = ST_PROG_UP(mUp);
}

void
ModuleProg::showStack(Message* p) {
	/**
	 * Ancienne méthode par usb, mais ça limite la possibilité de débuggage via message
	 */
#if 0

	Serial << " p:" << mPc; // 76 octets
	Serial << 'M' << freeMemory();
	pdebug(" FLG:" << mFlags);
	Serial << F(" STK ");
	pdebug(mLifo->queuePtr() << '-' << mLifo->headPtr() << ':');
	Serial << '[';

	for (int8_t i = mLifo->size(); i > 0;) {
		Serial << mLifo->get(--i);
		if (i > 0) Serial << ' ';
	}
	Serial << ']' << endl;
#else
	uint8_t sz=mLifo->size();
	p->writeInt8(PROG_MSG_STACK);
	p->writeInt8(sz);
	for (int8_t i = sz; i > 0;) {
		p->writeInt16(mLifo->get(--i));
	}
#endif
}

void
ModuleProg::step(Message* p) {
	uint8_t u(ModuleEeprom::read(mPc++));

	switch (u & 0xC0) {
		case 0x40: // num14
		{
			// recrééer un int16 signé à partir des 14 bits
			uint16_t i = ((u & 0x3f) << 8) + ModuleEeprom::read(mPc++);
			// bit de signe positionné : étendre
			if (i & 0x2000) i |= 0xC000;
			int16_t * p((int16_t*) & i);
			mLifo->push(*p);
		}
			break;

		case 0x80:
		{
			mLifo->push((u & 0x3F) + '*');
		}
			break;

		case 0x00: // num
		{
			uint8_t i(u & 0x3f);
			if (i & 0x20) i |= 0xC0;
			int8_t * p = (int8_t*) & i;
			mLifo->push((int16_t) * p);
		}
			break;

		default:
		{
			int16_t result = 32767;
			int16_t e;
			int16_t d;
			int16_t c;
			int16_t b;
			int16_t a;

			if (u < 0xC8) {
				e = mLifo->pop(); // ????
				d = mLifo->pop();
			}
			if (u < 0xD0) c = mLifo->pop();
			if (u < 0xE0) b = mLifo->pop();
			if (u < 0xF0) a = mLifo->pop();

			switch (u) // Type de micro instruction
			{
				case PROG_UTYPE_OP(CMD_RESET):
				{
#if 1
					asm volatile ("	jmp 0");
#else
					wdt_enable(WDTO_15MS);

					while (1) {

					}
#endif
				}
				case PROG_UTYPE_OP(CMD_END):
					next();
					break;


				case PROG_UTYPE_OP(CMD_EQ):
					result = (a == b);
					break;

				case PROG_UTYPE_OP(CMD_LT):
					result = (a < b);
					break;

				case PROG_UTYPE_OP(CMD_GT):
					result = (a > b);
					break;

				case PROG_UTYPE_OP(CMD_LTE):
					result = (a <= b);
					break;

				case PROG_UTYPE_OP(CMD_INSIDE): // 12 bytes, mais à vérifier et doit être juste avant CMD_GTE -a>=b
					//result = (a >= b && b >= c);
					//break;
					if (b<c) b=0x7FFF;
					// DO NOT SEPARATE INSIDE AND GTE
				case PROG_UTYPE_OP(CMD_GTE):
					result = (a >= b);
					break;

				case PROG_UTYPE_OP(CMD_AND):
					result = (a && b);
					break;

				case PROG_UTYPE_OP(CMD_OR):
					result = (a || b);
					break;

				case PROG_UTYPE_OP(CMD_ADD):
					result = (b + a);
					break;

				case PROG_UTYPE_OP(CMD_SUB):
					result = (a - b);
					break;
					

				case PROG_UTYPE_OP(CMD_MUL):
					result = a*b;
					break;

				case PROG_UTYPE_OP(CMD_DIV):
					result = a/b;
					break;

				case PROG_UTYPE_OP(CMD_NOT):
					result = (a == 0);
					break;

					/*	case PROG_UTYPE_OP(CMD_RD800):
						case PROG_UTYPE_OP(CMD_RD801):
							b=0;
							c=u & 1;
						case PROG_UTYPE_OP(CMD_RD8):
							result = (Message::read16(a, b, c));
							break;*/

				case PROG_UTYPE_OP(CMD_WR1600):
				case PROG_UTYPE_OP(CMD_WR1601):
					// a=module b=value (c=device))
					c = u & 1;
					// result = (Message::write16(a, c, b));
					Message::write16(a, c, b);
					break;

				case PROG_UTYPE_OP(CMD_RD1600):
				case PROG_UTYPE_OP(CMD_RD1601):
					b = 0;
					c = u & 1;
				case PROG_UTYPE_OP(CMD_RD16):
					// a=module b=device c=offsetread
					result = (Message::read16(a, b, c << 1));
					break;

				case PROG_UTYPE_OP(CMD_MSG):
				{
					// e=dest
					// d=mod
					// c=msg
					// b=dev
					// a=16 bits to send
					Message* p = new Message(c, e, d);
					p->writeInt8(b);
					p->writeInt16(a);
					p->dump();
					Module::dispatch(p);
					delete p;
					break;
				}

				case PROG_UTYPE_OP(CMD_WR3B):
				case PROG_UTYPE_OP(CMD_WR3W):
					break;
				case PROG_UTYPE_OP(CMD_OUTRD16):
					{
						// e=dest
						// d=mod
						// c=msg
						// b=dev
						// a=offset
						if (mp) delete mp;	// TODO This really should *NEVER* happen
						Message* mp = new Message(c, e, d);
						mp->writeInt8(b);
						miPOffset = a;
						Module::dispatch(mp);
						
						// The program is now stopped, and is waiting for mp to be deleted (by ModuleProg::msgHandler)
					}
					break;

				case PROG_UTYPE_OP(CMD_IFGOTO):
					if (a == 0) {
						break;
					}
					a = b;
					// DO NOT SEPARATE CMD_IFGOTO and CMD_GOTO CASES
				case PROG_UTYPE_OP(CMD_GOTO):
					mUp = a - 1;
					next();
					break;

				case PROG_UTYPE_OP(CMD_DUP):
					mLifo->push(a);
					result = a;
					break;

				case PROG_UTYPE_OP(CMD_SWAP):
					mLifo->push(b);
					result = a;
					break;

				case PROG_UTYPE_OP(CMD_WR16):
					Message::write16(a, b, c);
					break;

				case PROG_UTYPE_OP(CMD_PICK):
					result = mLifo->get((uint8_t) a - 1);
					break;

				case PROG_UTYPE_OP(CMD_SEC):
				case PROG_UTYPE_OP(CMD_MIN):
					break;

				case PROG_UTYPE_OP(CMD_STACK):
					showStack(p);
					break;

				case PROG_UTYPE_OP(CMD_CLEAR):
					mLifo->clear();
					break;

				case PROG_UTYPE_OP(CMD_DROP):
					break;

				// FIXME NYI
				case PROG_UTYPE_OP(CMD_PAUSE):

					break;

				default:
					p->writeInt8(PROG_MSG_ERR);
					break;
			}
			if (result != 32767) {
				mLifo->push(result);
			}
		}
	}
}

int8_t
ModuleProg::msgHandler(uint8_t iMsgType, Message * p) {
	if (mp) {
		// out_read16 a envoyé un message
		// Si on en reçoit la réponse, on push le résultat
		if (
			mp->getHeaderFrom() == p->getHeaderDest() &&
			mp->getHeaderMod() == p->getHeaderMod() &&
			mp->getHeaderMsg() == (p->getHeaderMsg() | 32)) {
			// push the result
			mLifo->push(p->getDataInt16(miPOffset));

			// delete the msg so the execution can now continue
			delete mp;
			mp = 0;
		}
	}
	switch (iMsgType) {
		case MSG_TYPE_BEGIN:
			holdPin(LED_GREEN);
			holdPin(LED_RED);
			pinMode(LED_GREEN, OUTPUT);
			pinMode(LED_RED, OUTPUT);
			mLifo = new Lifo<int16_t, 3>;
			if (ModuleEeprom::read16(ST_PROG) != 0xFADA) {
				debugln("INIT PROG AREA");
				for (uint8_t i = 0; i < PROG_MAX_UP; i++) {
					ModuleEeprom::write(ST_PROG_UP(i), (byte) (PROG_UTYPE_OP(CMD_END)));
				}
				ModuleEeprom::write(ST_PROG, (int) 0xFADA);
			}
			return MSG_RET_OK;

		case MSG_TYPE_RESET:
			debugln(F("RST T"));
			//config(DHT_DEFAULT_CONFIG, true);
			return MSG_RET_OK;

		case MSG_TYPE_WRITE:
		{
			uint8_t op = p->takeByte();
			uint8_t up = p->takeByte();
			switch (op) {
				case 0: // PAUSE
					mFlags |= PROG_PAUSED;
					break;

				case 1: // RESUME (Macro F4))
					mFlags &= ~PROG_PAUSED;
					break;

				case 2: // RESET TO ROW (Macro F5 (run 0))
					mUp = up - 1;
					mLifo->clear();
					next();
					break;

				case 3: // STEP (Macro F6))
					step(p);
					break;
					
				case 4: // showStack()... lol
					break;

				case 5:
					mFlags |= PROG_DEBUG;
					break;

				case 6:
					mFlags &= ~PROG_DEBUG;
					break;
			}
			p->writeInt8(PROG_MSG_STAT);
			p->writeInt16(freeMemory());
			p->writeInt16(mPc);
			showStack(p);

			return MSG_RET_OK;
		}


		case MSG_TYPE_LOOP:
			// Exécution de PC
			//case MSG_TYPE_SECOND:
		{
			if ((mp != 0) | (mFlags & PROG_PAUSED))
				return MSG_RET_OK; // PAUSE or WAIT MSG
			step(p);
		}
			break;

		default:
			return Module::msgHandler(iMsgType, p);
	}
	return MSG_RET_UNKNOWN_MSG;
}
