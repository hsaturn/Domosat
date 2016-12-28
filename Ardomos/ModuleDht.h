#ifndef _MODULE_DHT_
#define _MODULE_DHT_

#include <Arduino.h>
#include "Module.h"
#include <dht.h>

#define DHT_11 0x00
#define DHT_22 0x80

// Default configuration = DHT22 on A0
#define DHT_DEFAULT_CONFIG DHT_22

/** Réponse aux messages
  * 
  * MSG_TYPE_WRITE : Module configuration
  *    octet 0 : iDev
  *    	bit 7 : 0 si DHT11 1 si DTH22
  *    	bit 0..4 : Entrée analogique à utiliser
  *
  * MSG_TYPE_READ : Temperature & Hygro read
  *   iDev = 0 : read T° and Hygro (default device)
  *
  *   iDev !=0 , read any DHT device (NYI)
  *          b7    : 0 read DHT11, 1 DHT22 (NYI)
  *   		 b5    : 1 (Fixed)
  *   		 b4    : if b5, 0 DHT11, 1 DHT22			FIXME NYI
  *          b3-b0 : if b5, device (a0-a15)				FIXME NYI
  *   Return value, four bytes
  *     	0..1 :  10xT° (999 in case of error)
  *   		2..3 : hygro (or number of error if T°==999)
  */
class ModuleDht : public Module
{
	private:
		uint8_t	miDev;			// Same as MSG_TYPE_WRITE iDev
		uint8_t	miPin;			// Number of Analog pin
		dht	moDht;
		uint8_t miLastRead;		// Last dht.read result

		/**
		 * Configure the module.
		 *
		 * @param uint8_t iDev samme as MSG_TYPE_WRITE
		 * @param bool bReconfigure if true, then withHold previous pin and store config
		 *            (thus configuration is now permanent)
		 */
		void config(uint8_t iDev, bool bReconfigure);
		// TODO, yet only one DHT is managed.

	public:
		ModuleDht();

		virtual int8_t msgHandler(uint8_t iMsgType, Message* p);

		// Return filtered temperature x10
		int16_t read();

};

#endif
