#!/bin/bash

mvn clean package -f pom.xml
docker build . -t graalvm-service-jar -f Dockerfile_jar