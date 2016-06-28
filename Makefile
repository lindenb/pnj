SHELL=/bin/bash
CC ?= gcc
JAVA ?= java
JAVAC ?= javac
JAVAH ?= javah
CFLAGS=-O3 -Wall  -D_FILE_OFFSET_BITS=64 -D_LARGEFILE_SOURCE
PNJPACKAGE=com.github.lindenb.pnj.jni
JAVASRCDIR=./src/main/java
JAVACLASSSRC=./src/main/java/com/github/lindenb/pnj/jni/PngPtr.java
JAVAQUALNAME=com.github.lindenb.pnj.jni.PngPtr
JAR=pnj.jar
NATIVETARFILE=pnj-native.tar
TESTDIR=test



ifeq (${JAVA_HOME},)
$(error $${JAVA_HOME} is not defined)
endif

## find path where to find include files
JDK_JNI_INCLUDES?=$(addprefix -I,$(sort $(dir $(shell find ${JAVA_HOME}/include -type f -name "*.h"))))


ifeq (${JDK_JNI_INCLUDES},)
$(error Cannot find C header files under $${JAVA_HOME})
endif


# my C source code path
native.dir=src/main/native

CC?=gcc
.PHONY:all compile jar  clean

all:  jar 


#compile the JNI bindings
${native.dir}/pnj.o: ${native.dir}/pnj.c ${native.dir}/pnj.h 
	$(CC) -c $(CFLAGS) -o $@ $(CFLAGS) -fPIC  ${JDK_JNI_INCLUDES} $<


#create JNI header
${native.dir}/pnj.h : compile
	$(JAVAH) -o $@ -jni -classpath ${JAVASRCDIR} $(JAVAQUALNAME)
	
#compile java classes
compile: $(JAVACLASSSRC)
	$(JAVAC) -sourcepath ${JAVASRCDIR} -d ${JAVASRCDIR} $^

#create a JAR
jar: ${JAVASRCDIR}
	jar cvf ${JAR} -C ${JAVASRCDIR} .

