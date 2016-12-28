#ifndef COMM_H_
#define COMM_H_

#include <Arduino.h>
#include <Mirf.h>

#include "Module.h"

#define MIRF_CSN 6
#define MIRF_CE 5	// Faudra mettre 5

class Message;

/**
  * Classe de gestion du module nRF24L01
  */
class Comm : public Module
{
    private:
        uint8_t miErrors;
        Message* mpMessage;

    private:
        static uint8_t begin(int iCsnPin, int iCePin);

    public:

        Comm();

        virtual int8_t msgHandler(uint8_t iMsgType, Message* p);

        bool send(Message* pMsg);

        static void serialInit();


        /** resend a message, so ttl is decreased
          * if ttl=0 the message is not sent, and false is returned.
          * @return bool true if the message is sent
          */
        bool resend(const Message* pMsg);

        Message* receive();
};

#endif
