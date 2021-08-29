#!/bin/bash

# Workaround for Docker for Windows in Git Bash.
docker()
{
        (export MSYS_NO_PATHCONV=1; "docker.exe" "$@")
}

docker build -t graalvm-builder -f native-image/Dockerfile_builder .

container_id=$(docker run -d --volume "$HOME"/.m2:/root/.m2 graalvm-builder)

docker exec "$container_id" bash -c "mvn clean -Pnative package -Dmaven.test.skip=true"
docker cp "$container_id":/home/user/spring-graalvm-service/target/spring-graalvm-service-native ./native-image/spring-graalvm-service-native
docker container stop "$container_id"

docker build . -f native-image/Dockerfile_native -t graalvm-native-service