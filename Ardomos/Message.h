#ifndef MESSAGE_H_
#define MESSAGE_H_

#include <Arduino.h>
#include "Debug.h"

#define MSG_LENGTH 32 

#define MSG_INFO	'='

#define MSG_HEADER_FROM 0   // Arduino transmitter
#define MSG_HEADER_SRCE 1	// Arduino receiver
#define MSG_HEADER_DEST 2   // Arduino recipient (MSG_DEST_XXX or number)
#define MSG_HEADER_MOD  3   // Module recipent (MODULE_XXX))
#define MSG_HEADER_FTL  4   // Flags & Ttl
#define MSG_HEADER_NR   5   // Number of message
#define MSG_HEADER_MSG  6   // Message to send
#define MSG_HEADER_SIZE 7	// Total length of message

#define MSG_DATA_OFFSET 8

#define MSG_DEFAULT_FLAG 0
#define MSG_DEFAULT_TTL  (15 + MSG_DEFAULT_FLAG)

#define MSG_FLAG_RECEIVED 0x80

// * Message dispatchable to ALL modules at once (Module::dispatch))
// X Specific message (should be handled by a specific module))
#define MSG_TYPE_BEGIN		'@'		// *  @
#define MSG_TYPE_RESET		'A'		// *  WAS 0 Cold reset to default params (*)
#define MSG_TYPE_RF433		'B'		// 433Mhz data incoming
#define MSG_TYPE_FLAGS		'F'		//    ?? CONFIGURE ??
#define MSG_TYPE_HELLO		'H'		//    For ModuleId assignation
// #define MSG_TYPE_LIST		'L'		//    List devices
#define MSG_TYPE_LISTM		'L'		// X  WAS ? List all modules
#define MSG_TYPE_LOOP		'O'		// *  Same as arduino loop
#define MSG_TYPE_MINUTE		'M'		// *  Once every minute
#define MSG_TYPE_SECOND		'N'		// *  Once every second
#define MSG_TYPE_PING		'P'		// ? 
#define MSG_TYPE_READ16		'Q'		//
#define MSG_TYPE_READ		'R'		//    Either 1 byte or many bytes
#define MSG_TYPE_WRITE3W	'T'		//    WAS 6 Write 3 uint_16
#define MSG_TYPE_WRITE		'W'		//    WAS 1 Write byte
#define MSG_TYPE_WRITE16	'X'		//    WAS 2 Write 2 bytes
#define MSG_TYPE_WRITE3B	'Y'		//    WAS 3 Write 3 bytes
#define MSG_TYPE_SHUTDOWN	'Z'		//    Power is going down.

// (*) TODO When a RESET occurs, the module must release used pins
// in order to avoid bad pin conflict behaviour.

#define MSG_RET_STOP_PROPAGATION -10
#define MSG_RET_OK    0
// Generic error while performing message
#define MSG_RET_ERROR -1
// Accessed device unknown
#define MSG_RET_BADDEV -2
// Module exists but has not overriden the msgHandler method
#define MSG_RET_VIRTUAL -3
// The module does not handle MSG_HEADER_MSG
#define MSG_RET_UNKNOWN_MSG -4
// None of the registered module 
// matches the message
// The message could not be adressed
#define MSG_RET_NODEST -5
#define MSG_RET_NOMOD -6

#define MSG_DEST_BROADCAST	0xFF	// Arduinos recipent : all
#define MSG_DEST_MASTER		0xFE	// Master PC (Any USB connected device (not arduino side))
#define MSG_DEST_SELF		0


class Message
{
    private:
        bool mbDeleteBytes;		// True if mpMsg has been allocated (and have to be deleted))
        byte* mpMsg;			// allocated memory (client side or *this side)
		
        uint8_t miDataOffset;	// offset in DATA for read/write operations

        /**
          * Advance the data offset to iOffset bytes
          */
        void advance(uint8_t iOffset);

    public:
        /** Create a message from this arduino to any dest.
          *
          * @input uint8_t iMsg   MSG_TYPE_XXX constant
          * @input uint8_t iDest  Id of the dest arduino or MSG_DEST_XXXX
          * @input uint8_t iMod   Name of the destination module 
          **/
        Message(uint8_t iMsg, uint8_t iDest, uint8_t iDestModule);
        
        /** Create a message from byte ptr. Allowing to save MSG_LENGTH bytes
         *  byte* p must exists while *this exists.
         *	Take care of p that is not deleted when the message is deleted.
         */
        Message(byte* p);
        
        ~Message() { if (mbDeleteBytes) delete mpMsg; }

        uint8_t length() const { return MSG_LENGTH; }

        /**
          * @return byte* pointer of the whole message (header + data)
          */
        const byte*   getBytes() const { return mpMsg; }

        /** Rewind the data pointer to 0 */
        void rewind() { miDataOffset=MSG_DATA_OFFSET; }

        /** Write an int16, do not advance data offset
          * @input int8_t 
          **/
        void writeInt8(int8_t iInt);

        /** Read a byte and advance data offset
          * @return byte
          */
        byte takeByte();

        /**
         * Read a uint16_t and advance data offset
         * @return uint16_t
         */
        uint16_t takeWord();

        /** Write an int16, do not advance data offset
          * @input int16_t 
          **/
        void writeInt16(int16_t);

        // Header getters
        // --------------
        // Id0  0 for permanent from, rand for temporary id (see id.h)
        // Id1  Id of the emitter
        // From alias for Id1
        // Dest Id of the receiver arduino
        // Mod  Name of the receiver module
        // Ttl  Time to live
        // Nr   Nr of message (incremental per arduino) 
        // Msg  MSG_TYPE_XXX constant

        uint8_t getHeaderFrom () const { return mpMsg[ MSG_HEADER_FROM ]; }
        uint8_t getHeaderDest () const { return mpMsg[ MSG_HEADER_DEST ]; }
        uint8_t getHeaderMod  () const { return mpMsg[ MSG_HEADER_MOD  ]; }
        uint8_t getHeaderTtl  () const { return mpMsg[ MSG_HEADER_FTL  ]&0xF; }
        uint8_t getHeaderFlags() const { return mpMsg[ MSG_HEADER_FTL  ]; }
        uint8_t getHeaderNr   () const { return mpMsg[ MSG_HEADER_NR   ]; }
        uint8_t getHeaderMsg  () const { return mpMsg[ MSG_HEADER_MSG  ]; }
        
        uint8_t getCurrentOffset() const { return miDataOffset; }

        // FIXME const has nothing to do there
        void setHeaderNr(byte iMsgNr) { mpMsg[ MSG_HEADER_NR ] = iMsgNr; }

		/**
		 * Use ONLY MSG_FLAG_XXX
         * @param iFlag
         */
        void setFlag(uint8_t iFlag) { mpMsg[MSG_HEADER_FTL] |= iFlag; }

        bool hasFlag(uint8_t iFlag) const { return mpMsg[MSG_HEADER_FTL] & iFlag; }

        /**
          * Getters : Get data from absolute position
          */
        int16_t getDataInt16(uint8_t iPos) const { return *((int16_t*)(mpMsg+MSG_DATA_OFFSET+iPos)); }
        int8_t  getDataInt8 (uint8_t iPos) const { return  mpMsg[MSG_DATA_OFFSET+iPos]; }

        /**
          * Decrease the TTL and returns it.
          * @return 0 if no more ttl
          */
        uint8_t decreaseTtl() const;
		
		void resetTtl() { mpMsg[MSG_HEADER_FTL] |= 0x0F; }

        void dump() const;
		
		void swapFromDest();
		void lowerMsg() { mpMsg[MSG_HEADER_MSG] |= 32; }
		void upperMsg() { mpMsg[MSG_HEADER_MSG] &= ~32; }

    public:	// Static methods

        /** Read16 a Module for a given device/module (internal no outgoing request) */
        static int16_t read16(uint8_t iModule, uint8_t iDev, uint8_t iOffset=0);

        /** Sends a 16 bits data message to itself (no outgoing)
         * The data section will receive an additionnal byte 0 after iValue
         *
         * @input uint8_t  iModule (internal module)
         * @input uint8_t  iDev	(device number)
         * @input uint16_t iValue	(16 bit msg data)
         * @input uint16_t Return value from the module or iValue	// FIXME should be MSG_RET_XXX
         */
        static int16_t write16(uint8_t iModule, uint8_t iDev, uint16_t iValue);

        /**
         * Sends 3 bytes data to itself
         * The data section will receive an additionnal byte 0 after i3
         *
         * @input uint8_t  iModule (internal module)
         * @input uint8_t  iDev	(device number)
         * @input uint8_t  i1		byte 1
         * @input uint8_t  i2		byte 2
         * @input uint8_t  i3		byte 3
         */
        static int16_t write3b(uint8_t iModule, uint8_t iDev, uint8_t i1, uint8_t i2, uint8_t i3=0);

        /**
         * Sends 3 words data to itself
         * @input uint16_t  iModule (internal module)
         * The data section will receive an additionnal byte 0 after i3
         *
         * @input uint8_t  iDev	(device number)
         * @input uint16_t  i1		byte 1
         * @input uint16_t  i2		byte 2
         * @input uint16_t  i3		byte 3
         */
        static int16_t write3w(uint8_t iModule, uint8_t iDev, uint16_t i1, uint16_t i2, uint16_t i3=0);

    private:
        /**
         * Dispatch the message in order to retried response.
         *
         * WARNING : Message is deleted at the end
         */
        static int16_t dispatch(Message* p, uint8_t iOffset=0);

    private:
        Message();
};

#endif
