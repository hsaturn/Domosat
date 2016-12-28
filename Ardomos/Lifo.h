/* 
 * File:   Lifo.h
 * Author: hsaturn
 *
 * Created on 30 octobre 2014, 00:47
 */

#ifndef FIFO_H
#define	FIFO_H

#include <Arduino.h>


// /!\ *** Attention, la taille maxi d'une lifo est aussi fonction de la taille maxi d'un message arduino
// /!\ *** et de tout ce qui est transmit avec le module uProg (Err, Stack, Pc ..)
#define FIFO_SIZE (1<<FIFO_BSIZE)

// Define a Lifo of 2^FIFO_BSIZE elements

template <class T, int FIFO_BSIZE>
class Lifo
{
    public:
        Lifo() : miHead(0), miQueue(0) {};
        
        void push(const T t) { mLifo[miHead] = t; inc(miHead); }
        T pop() {  dec(miHead); T t(mLifo[miHead]); return t; }
        uint8_t size() { return (miHead-miQueue)&(FIFO_SIZE-1); }

        int8_t headPtr() const { return miHead; }
        int8_t queuePtr() const { return miQueue; }
		
		T get(uint8_t iPos) {
			iPos = (miHead-1-iPos)&(FIFO_SIZE-1);
			return mLifo[iPos];
		}
		
		void clear() { miHead = miQueue; }
				
    private:
        int8_t miHead;
        int8_t miQueue;
        T mLifo[FIFO_SIZE];
        
    private:
        void inc(int8_t &t) { t++; t &= (FIFO_SIZE-1); }
        void dec(int8_t &t) { t--; t &= (FIFO_SIZE-1); }
};

#endif	/* FIFO_H */

