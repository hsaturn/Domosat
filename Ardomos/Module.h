#ifndef _MODULE_H_
#define _MODULE_H_

#include "config.h"
#include "Message.h"
#include "Vector.h"

#define MODULE_ALL         '*'
#define MODULE_MODULE	   '@'
//#define MODULE_ARRAY     'A'
#define MODULE_BUZZER      'B'
#define MODULE_CLOCK       'C'
#define MODULE_ECHO        'E'
#define MODULE_DETECT_MVT  'D'
#define MODULE_ETHERNET    'E'
#define MODULE_HYGRO       'H'
#define MODULE_INFRARED    'I'
#define MODULE_UNKNOWN     'K'
#define MODULE_LIGHT       'L'
#define MODULE_MEASURE     'M'
#define MODULE_NRF24       'N'
#define MODULE_EEPROM      'O'
#define MODULE_PROG        'P'
#define MODULE_RELAY       'R'
#define MODULE_SCREEN      'S'
#define MODULE_TEMPERATURE 'T'
#define MODULE_USB         'U'
#define MODULE_RF_433	   'X'
#define MODULE_ID          'Z'

#define MAX_MODULES         10

class Message;

/**
  * Base class for all modules
  **/
class Module
{
    private:
        static Module* mpModules[MAX_MODULES];
        static uint8_t miCount;

        uint8_t mcName;	// Name of the module one upper letter

        // Use to do a pseudo pinout allocation
        // Each module should reserve the pin they use, except SPI ones
        // see holdPin() / withHoldPin()
        static uint32_t		siPins;

    public:
        Module(uint8_t cName, uint8_t iId=0);

        /**
          * This method must be called foreach arduino loop()
          * A LOOP message will be sent to every registered module.
          */
        static void loop();

        static void registerModule(Module* pNew);

        //static void dumpRegistered();
		
        /**
          * A module must handle messages.
          * The virtual method will be called for every incomming message.
          * If the handler cannot handle the message, it must then call the parent handler.
          *
          * that is relevant for the module :
          * MSG_HEADER_MOD = '*' or mcName
          *
          * @input uint8_t iMsg   The message type (MSG_TYPE_xxx)
          * @input Message* p, the input / output values message.
          *                    CARE ! p can be set to 0 (no message)
          * @return int8_t MSG_RET_XXX or positives values for module custom return values
          */
        virtual int8_t msgHandler(uint8_t iMsgType, Message* p);

        /**
          * Dispatches a simple message to all modules (cannot go outside).
          *
          * @input uint8_t iMsgType  
          * @return int8_t MSG_RET_XXX (see msgHandler)
          **/
        static int8_t dispatchAllMods(uint8_t iMsgType);

        /** Dispatch a message to all relevant modules (cannot go outside).
          * Only broadcast message or message adresse to this arduino are managed.
          *
          * Relevant is :
          *
          * if msgType='*' : all modules
          * else msgDest is used
          * @input Message* p The message to dispatch
          * @return int8_t MSG_RET_XXX (see msgHandler)
          **/
        static int8_t dispatch(Message* p);

        /** Begin all modules by sending them the BEGIN msg
          */
        static void begin();

        void holdPin(uint8_t iPin);
        void withHoldPin(uint8_t iPin);

};

#endif
