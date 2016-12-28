/* 
 * File:   ModuleProg.hpp
 * Author: hsaturn
 *
 * Created on 29 octobre 2014, 21:11
 */

#ifndef MODULEPROG_HPP
#define	MODULEPROG_HPP

#include <Arduino.h>
#include "Module.h"
#include "Lifo.h"
#include "ModuleEeprom.h"

// Adresse en EEPROM du programme 0
#define ST_PROG_UP0 (ST_PROG+2)

// Adresse en EEPROM du uprogramme up
#define ST_PROG_UP(up) (ST_PROG_UP0+(up<<5))

#include "ProgDefs.hpp"

// Flags
#define PROG_PAUSED		0x01
#define PROG_DEBUG		0x02

// Constantes de message de retour
#define PROG_MSG_STAT	0xFC	// FC MM MM PC, MMMM = free mem, PC=mPc
#define PROG_MSG_STACK	0xFA
#define PROG_MSG_ERR	0xEE	// uProg byte not recognized

class ModuleProg : public Module
{
	public:
		ModuleProg() : Module(MODULE_PROG), mUp(-1),mFlags(PROG_DEBUG|PROG_PAUSED) { next(); };

		virtual int8_t msgHandler(uint8_t iMsgType, Message* p);

    private:
        uint16_t mPc;    // Program counter (dans l'EEPROM)
		
	public:
		int8_t				mUp;	// Numéro de uprogramme en cours d'exécution
		uint8_t				mFlags;	
		uint8_t				miPOffset;	// offset de lecture pour out_rd16
		// Passe au microprogramme suivant
		void next();
		void step(Message* p);
		void showStack(Message* p);
};

#endif	/* MODULEPROG_HPP */

