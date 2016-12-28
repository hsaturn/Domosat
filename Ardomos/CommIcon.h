#ifndef _COMM_ICON_H_
#define _COMM_ICON_H_

#include <Arduino.h>

// Gère les images in0.ard in1.ard out0.ard out1.ard et comm.ard comm1.ard
//
// in  1/0 = data incoming off/on
// out 1/0 = data outgoing on/off
// comm    = carre noir de base contenant l'ensemble
// comm1   = connecté
// comm0   = non connecté

const uint8_t COMM_CONN=4;	// Flag connecté
const uint8_t COMM_OUT=1;	// Flag state out
const uint8_t COMM_IN=2;		// Flag state in


class Timer;

class CommIcon
{
	private:
		uint8_t cState;
		uint8_t mx;
		uint8_t my;
		Timer* ptmrOffIn;	// Timer before extinguish in icon
		Timer* ptmrOffOut;	// Timer before extinguish out icon

	public:
		CommIcon(uint8_t x,uint8_t y);

		// Set the time before the icon goes off 
		void setOffInterval(long lMs);

		/** Check timers and switch off in/out icons if timeout
		 */
		void update();

		void draw(bool bFull=false);

		void setFlags(uint8_t flags);

		void draw(const uint8_t* icon, uint8_t icon_bytes, uint8_t BIT, uint8_t iFlag);

		/**
		 * Set outgoing flag and redraw
		 * @input iOut 0 or 1 (no other value !)
		 */
		void setOut(uint8_t iOut);

		void setIn(bool iIn);

		void setConnected(uint8_t iConnected);
};

#endif
