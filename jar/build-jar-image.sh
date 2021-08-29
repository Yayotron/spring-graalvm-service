#!/bin/bash

mvn clean package -f pom.xml
docker build . -t openjdk-service -f ./jar/Dockerfile_jar