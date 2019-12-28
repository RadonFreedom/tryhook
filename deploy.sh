#!/usr/bin/env bash
cd tryhook
git pull
mvn clean package
ps -ef | grep java
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar tryhook-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &

# local
# java -jar tryhook-0.0.1-SNAPSHOT.jar --spring.profiles.active=local