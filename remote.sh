#!/usr/bin/env bash

mvn clean package
sshfs root@101.132.157.78:/root ~/cloud
cp target/tryhook-0.0.1-SNAPSHOT.jar ~/cloud
ssh root@101.132.157.78
ps -ef | grep java
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar tryhook-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &