#!/bin/bash

mvn clean package -f pom.xml
docker build . -t graalvm-jit-service -f ./graalvm-jit/Dockerfile_jit