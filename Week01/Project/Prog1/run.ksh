#!/bin/bash
DEBUG=""
WRAPPER=false
VAR=1
COUNT=0
C=0
MAX=5
TIME=5
while true; do
  case "$1" in
    -d | --debug ) DEBUG="-debug"; shift ;;
    -w | --wrap )    WRAPPER=true; shift ;;
     * ) break ;;
  esac
done

count=1
while [ $VAR -ne 0 ] && [ $COUNT -lt $MAX ]
do
    echo run.ksh: starting java'('${COUNT}')'...
    javac -cp "lib/log4j-1.2.17.jar" -sourcepath "./src/main/java:./src/main/resources" -d build $(find . -name "*.java")
    java -cp "./build:./lib/log4j-1.2.17.jar" -Dlog4j.configuration=file:./src/main/resources/log4j.properties com/revature/control/BankingDriver ${DEBUG}
    VAR="${?}"
    echo exit: $VAR
    if [ ! $WRAPPER ] || [ $VAR -eq 0 ] || [ $COUNT -ge $MAX ]
    	then
    	break;
    fi
    TIME=5
    while [ $C -lt $TIME ] 
    do
        echo -ne "run.ksh: restarting in "$TIME" second(s)"\\r
    	sleep 1
    	(( TIME-- ))
	echo -ne "run.ksh: restarting in "$TIME" second(s)"\\r
    done
    echo -ne \\n
    (( COUNT++ ))
done
