#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=avr-gcc
CCC=avr-g++
CXX=avr-g++
FC=gfortran
AS=avr-as

# Macros
CND_PLATFORM=Arduino-Linux
CND_DLIB_EXT=so
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/60c060cd/ModuleMvt.o \
	${OBJECTDIR}/_ext/60c060cd/ModuleRf433.o \
	${OBJECTDIR}/Comm.o \
	${OBJECTDIR}/CommIcon.o \
	${OBJECTDIR}/Lifo.o \
	${OBJECTDIR}/Message.o \
	${OBJECTDIR}/Module.o \
	${OBJECTDIR}/ModuleClock.o \
	${OBJECTDIR}/ModuleDht.o \
	${OBJECTDIR}/ModuleEeprom.o \
	${OBJECTDIR}/ModuleId.o \
	${OBJECTDIR}/ModuleProg.o \
	${OBJECTDIR}/ModuleRelay.o \
	${OBJECTDIR}/ModuleScreenArid.o \
	${OBJECTDIR}/ModuleUsb.o \
	${OBJECTDIR}/main.o \
	${OBJECTDIR}/thermo.o \
	${OBJECTDIR}/utils.o


# C Compiler Flags
CFLAGS=${FLAGS_GCC}

# CC Compiler Flags
CCFLAGS=${FLAGS_GPP}
CXXFLAGS=${FLAGS_GPP}

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/domosat

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/domosat: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	avr-gcc -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/domosat ${OBJECTFILES} ${LDLIBSOPTIONS} ${FLAGS_LINKER}

${OBJECTDIR}/_ext/60c060cd/ModuleMvt.o: /home/hsaturn/Projets/Arduino/Domosat/ModuleMvt.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/60c060cd
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/60c060cd/ModuleMvt.o /home/hsaturn/Projets/Arduino/Domosat/ModuleMvt.cpp

${OBJECTDIR}/_ext/60c060cd/ModuleRf433.o: /home/hsaturn/Projets/Arduino/Domosat/ModuleRf433.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/60c060cd
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/60c060cd/ModuleRf433.o /home/hsaturn/Projets/Arduino/Domosat/ModuleRf433.cpp

${OBJECTDIR}/_ext/60c060cd/ModuleRf433.hpp.gch: /home/hsaturn/Projets/Arduino/Domosat/ModuleRf433.hpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/60c060cd
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o "$@" /home/hsaturn/Projets/Arduino/Domosat/ModuleRf433.hpp

${OBJECTDIR}/_ext/60c060cd/config.h.gch: /home/hsaturn/Projets/Arduino/Domosat/config.h 
	${MKDIR} -p ${OBJECTDIR}/_ext/60c060cd
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o "$@" /home/hsaturn/Projets/Arduino/Domosat/config.h

${OBJECTDIR}/Comm.o: Comm.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/Comm.o Comm.cpp

${OBJECTDIR}/CommIcon.o: CommIcon.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/CommIcon.o CommIcon.cpp

${OBJECTDIR}/Lifo.o: Lifo.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/Lifo.o Lifo.cpp

${OBJECTDIR}/Lifo.h.gch: Lifo.h 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o "$@" Lifo.h

${OBJECTDIR}/Message.o: Message.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/Message.o Message.cpp

${OBJECTDIR}/Message.h.gch: Message.h 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o "$@" Message.h

${OBJECTDIR}/Module.o: Module.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/Module.o Module.cpp

${OBJECTDIR}/ModuleClock.o: ModuleClock.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/ModuleClock.o ModuleClock.cpp

${OBJECTDIR}/ModuleDht.o: ModuleDht.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/ModuleDht.o ModuleDht.cpp

${OBJECTDIR}/ModuleEeprom.o: ModuleEeprom.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/ModuleEeprom.o ModuleEeprom.cpp

${OBJECTDIR}/ModuleId.o: ModuleId.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/ModuleId.o ModuleId.cpp

${OBJECTDIR}/ModuleProg.o: ModuleProg.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/ModuleProg.o ModuleProg.cpp

${OBJECTDIR}/ModuleRelay.o: ModuleRelay.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/ModuleRelay.o ModuleRelay.cpp

${OBJECTDIR}/ModuleScreenArid.o: ModuleScreenArid.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/ModuleScreenArid.o ModuleScreenArid.cpp

${OBJECTDIR}/ModuleUsb.o: ModuleUsb.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/ModuleUsb.o ModuleUsb.cpp

${OBJECTDIR}/main.o: main.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/main.o main.cpp

${OBJECTDIR}/thermo.o: thermo.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -D__cplusplus -DHAVE_MODULE_DHT=1 -I${INCLUDE} -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/avr/include -I../../../arduino/arduino-1.0.6/hardware/arduino/variants/standard -I../../../arduino/arduino-1.0.6/hardware/tools/avr/lib/gcc/avr/4.3.2/include -I../../../arduino/arduino-1.0.6/libraries/Mirf -I../../../arduino/arduino-1.0.6/libraries/hsaturn -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/robot -I../../../arduino/arduino-1.0.6/libraries/EEPROM -I../../../arduino/arduino-1.0.6/libraries/DHT -I../../../arduino/arduino-1.0.6/libraries/SPI -I../../../arduino/arduino-1.0.6/libraries -I../../../arduino/arduino-1.0.6/hardware/arduino/cores/arduino -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/thermo.o thermo.cpp

${OBJECTDIR}/utils.o: utils.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.c) -g -I${INCLUDE} -I/home/hsaturn/arduino/arduino-1.0.6/libraries/Streaming -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/utils.o utils.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/domosat

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
