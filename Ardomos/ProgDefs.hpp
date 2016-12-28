/* 
 * File:   ProgDefs.hpp
 * Author: hsaturn
 *
 * Created on 4 novembre 2014, 02:34
 */

#ifndef PROGDEFS_HPP
#define	PROGDEFS_HPP

// Constante permettant de construire un octet / mot correspondant
// à une micro instruction stockée en EEPROM (voir tableau de codage des micros instructions dans le manuel
#define PROG_UTYPE_NUM6(i)	(i & 0x3F)
#define PROG_UTYPE_NUM14_1(i)	(0x40 | ((i>>8)&0x3F))
#define PROG_UTYPE_NUM14_2(i)	(i & 0xFF)
#define PROG_UTYPE_NAME(c)	(0x80 | ((c-'*') & 0x3F))
#define PROG_UTYPE_OP(i)	(0xC0 | (i & 0x3F))

// Constantes à combiner avec PROG_OP pour obtenir un 
// byte à inscrire dans l'EEPROM
// ex: pour CMD_LT, PROG_UTYPE_OP(CMD_LT) donne la valeur correcte

// 5 values C0-C4	10xx-xxx
#define CMD_RD16	0 /* C0 */
#define CMD_RD8		1 /* C1 */
#define CMD_OUTRD16	2 /* C2 */
#define CMD_MSG		3 /* C3 */

// ternary op C8-CF
#define CMD_INSIDE	8 /* C8 */
#define CMD_WR16	9 /* C9 wr16(mod,dev,val)*/

// binary op D0-DF
#define CMD_EQ		16 /* D0 */
#define CMD_LT      17 /* D1 */
#define CMD_GT      18 /* D2 */
#define CMD_LTE     19 /* D3 */
#define CMD_GTE     20 /* D4 */
#define CMD_AND     21 /* D5 */
#define CMD_OR		22 /* D6 */
#define CMD_ADD		23 /* D7 */
#define CMD_SUB		24 /* D8 */
#define CMD_IFGOTO	25 /* D9 ifgoto(row, cond) */
#define CMD_SWAP	26 /* DA swap(a,b) */
#define CMD_MUL		27 /* DB */
#define CMD_DIV		28 /* DC */
// #define CMD_UNUSED 29 /* DD */
#define CMD_WR1600	30 /* (28 avant) DE ATTENTION DOIT ETRE PAIR */
#define CMD_WR1601	31 /* (29 avant) DF ATTENTION DOIT ETRE IMPAIR */

// unary op E0-EF
#define CMD_NOT		32 /* E0 */
#define CMD_DUP		33 /* E1 */
#define CMD_PICK	34 /* E2 */
#define CMD_DROP	35 /* E3 */
#define CMD_PAUSE	36 /* E4 secondes */
#define CMD_GOTO	37 /* E5 */
#define CMD_RD1600	38 /* E6 ATTENTION DOIT ETRE PAIR */ 
#define CMD_RD1601  39 /* E7 ATTENTION DOIT ETRE IMPAIR */
#define CMD_RD801   42 /* EA ATTENTION DOIT ETRE PAIR --------- unused ? */
#define CMD_RD800	43 /* EB ATTENTION DOIT ETRE IMPAIR ----- unused ? */


//
// zero stack & msg op F0-FF
#define CMD_SEC		48 /* F0 */
#define CMD_MIN		49 /* F1 */
#define CMD_STACK	50 /* F2 */
#define CMD_CLEAR	51 /* F3 */

#define CMD_WR3B	58 /* FA */
#define CMD_WR3W	59 /* FB */
#define CMD_OUTWR16	61 /* FD */

#define CMD_RESET	62 /* FE warm reset arduino*/	
#define CMD_END     63 /* FF */
#define CMD_CONT	CMD_END	/* FF */



#endif	/* PROGDEFS_HPP */

