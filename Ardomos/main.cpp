/* Domosat
vim: syntax=arduino
23412 octets 20130912
 */
#include <Arduino.h>
#include <EEPROM.h>
#include <Mirf.h>
#include <SPI.h>

#if HAVE_DISPLAY
#include <ST7735.h>
#include <ST_AA.h>
#include <Arid.h>
#endif

#include <Timer.h>

#include "Lifo.h"
#include "Module.h"
#include "ModuleId.h"
#include "thermo.cpp"
#include "CommIcon.h"
#include "Message.h"
#include "Comm.h"
#include <Streaming.h>

// For Arid FIXME => in module screen ?
#define cs 10   // for MEGAs you probably want this to be pin 53
#define dc 9
#define rst 8  // you can also connect this to the Arduino reset

#if HAVE_DISPLAY
Arid tft = Arid(cs, dc, rst);
#endif

#define HOUR_COLOR YELLOW

#if HAVE_DISPLAY
CommIcon* pCommIcons = 0;
#endif

#include "ModuleDht.h"
#include "ModuleProg.hpp"
#include "ModuleId.h"
#include "ModuleUsb.h"
#include "ModuleRelay.h"
#include "ModuleClock.h"
#include "ModuleEeprom.h"
#include "ModuleMvt.h"
#include "config.h"
#include "ModuleRf433.hpp"

void setup()
{
    Comm::serialInit();

	new ModuleId;
	/**/
	new ModuleEeprom;
	new ModuleProg;
	
#if HAVE_RELAY
	new ModuleRelay;
#endif
	
	new ModuleUsb;

#if HAVE_DHT
	new ModuleDht;
#endif

	new ModuleClock;
#if HAVE_MVT
	new ModuleMvt;
#endif
#if HAVE_RF433
	new ModuleRf433;
#endif

	Module::dispatchAllMods(MSG_TYPE_LISTM);
    Module::begin();

#if HAVE_DISPLAY
    tft.initR(); // initialize a ST7735R chip
    tft.writecommand(ST7735_DISPON);
    tft.setAntialiasing(false);
    tft.fillScreen(0);
    tft.drawLine(0, 12, 128, 12, 0xFFFF);
    // pCommIcons=new CommIcon(tft.width-20,0);
    pCommIcons = new CommIcon(108, 0); // FIXME size dyn
#endif
}

#if HAVE_DISPLAY
void drawChar(char c, uint16_t cCol, uint8_t iSize)
{
    static char ac[20];

    if (ac[iC] != c)
    {
        tft.drawChar(gx + giFontWidth * iC*iSize, gy, ac[iC], BLACK, iSize);
    }
    ac[iC] = c;
    tft.drawChar(gx + giFontWidth * iC*iSize, gy, c, cCol, iSize);
    iC++;
    if (iC >= 20) iC = 19;
}
#endif

#if HAVE_SCREEN
void drawCounter(uint8_t i, uint16_t cCol, char cSep = 0, uint8_t iSize = 1)
{
    drawChar(i / 10 + '0', cCol, iSize);
    drawChar(i % 10 + '0', cCol, iSize);
    if (cSep)
    {
        drawChar(cSep, cCol, iSize);
    }
}
#endif

long readVcc()
{
    long result;

    // Read 1.1V reference against AVcc
    ADMUX = _BV(REFS0) | _BV(MUX3) | _BV(MUX2) | _BV(MUX1);
    delay(2); // Wait for Vref to settle
    ADCSRA |= _BV(ADSC); // Convert
    while (bit_is_set(ADCSRA, ADSC));
    result = ADCL;
    result |= ADCH << 8;
    result = 1125300L / result; // Back-calculate AVcc in mV
    return result;
}

void loop()
{
	Module::loop();
    //static Timer oSec(1000);
	return;
    
	
#if HAVE_DISPLAY
    if (pCommIcons) pCommIcons->update();

    while (oSec.nextEvent())
    {
		//Serial.print(F("MEM "));
		//Serial.println(freeMemory());

        int t = 19;
        int h = 35;
        int t = Message::read16(MODULE_TEMPERATURE, 0x0);
        int h = Message::read16(MODULE_TEMPERATURE, 0x0, 2);
        Serial.print("T:");
        Serial.print(t);
        Serial.print(" H:");
        Serial.println(h);

        // pCommIcons->setFlags(random(0,7));
        if (++sec >= 60)
        {
            sec = 0;
            if (++min >= 60)
            {
                min = 0;
                if (++hour >= 24)
                {
                    hour = 0;
                }
            }
        }

        drawCounter(hour, HOUR_COLOR, ':', 2);
        drawCounter(min, HOUR_COLOR, ':', 2);
        drawCounter(sec, HOUR_COLOR, 0, 2);

        uint16_t iCol;
        String s;

        if (t != 999)
        {
            s = "";
            s += (uint16_t) (t / 10);
            s += '.';
            s += (uint16_t) (t % 10);
            iCol = GREEN;
        }
        else
        {
            s = sLastTemp;
            iCol = GRAY;
        }
        if (sLastTemp != s || iCol == GRAY)
        {
            tft.drawString(10, 60, sLastTemp.c_str(), BLACK, 3);
            sLastTemp = s;
            tft.drawString(10, 60, sLastTemp.c_str(), iCol, 3);
        }

        if (t != 999)
        {
            s = "";
            s += (uint16_t) (h / 10);
            s += '.';
            s += (uint16_t) (h % 10);
            s += '%';
            iCol = CYAN;
        }
        else
        {
            s = sLastHygro;
            iCol = GRAY;
        }
        if (sLastHygro != s || iCol == GRAY)
        {
            tft.drawString(10, 110, sLastHygro.c_str(), BLACK, 3);
            sLastHygro = s;
            tft.drawString(10, 110, sLastHygro.c_str(), iCol, 3);
        }
    }
#endif

    // tft.drawArid(thermo, 45,10,0);
}


