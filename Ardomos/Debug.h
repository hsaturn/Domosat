#ifndef DEBUG_H_
#define DEBUG_H_

#if DEBUG
	#define debugln(t) { Serial.println(t); }
	#define debug(t)   { Serial.print(t); }
#else
	#define debugln(t)
	#define debug(t)
#endif

#endif
