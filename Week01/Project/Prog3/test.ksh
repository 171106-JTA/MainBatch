#!/bin/bash
    javac -cp "lib/log4j-1.2.17.jar" -sourcepath "./src/main/java:./src/main/resources" -d build $(find . -name "*.java" | grep -v "Test")
    java -cp "./build:./lib/log4j-1.2.17.jar" -Dlog4j.configuration=file:./src/main/resources/log4j.properties com/revature/control/BankingDriver ${DEBUG}
