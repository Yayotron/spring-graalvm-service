# spring-graalvm-service

Pet project to test Spring GraalVM - this project has features that we'll commonly find in a production repository: rest
endpoints, database, logging, flyway.

This repository contains the required configuration to build and run the same project in a standard jdk17 container and
using a GraalVM native image built to run inside oraclelinux:8-slim.

# Pre-requisites

* Maven (tested with version 3.9.1)
* JDK 17 (tested with Eclipse Temurin distribution)
* Docker
* Create a database called demo in PostgreSQL (can be started running `docker-compose up database`).

# Note

Apparently Flyway still doesn't support GraalVM ([link to the issue](https://github.com/flyway/flyway/issues/2927)).

Therefore it's disabled in `docker-compose` configuration for GraalVM service, it's necessary to run jar version
beforehand in order to create the database structure.

# How to run tests

Ain't nobody got time to write unit tests for a pet project.
You can use postman collection `spring-graalvm-service.postman_collection.json` to test the API manually.
You could also run performance test using jmeter. Note: these tests fully clean-up DB before starting.

`jmeter -n -t performance_test.jmx -l {result-file-name}.jtl`

# How to build

## GraalVM Native Service

`./native-image/build-native-image.sh`

This generates a new docker image using `oraclelinux:8-slim` as base which is running a native image generated by
GraalVM with the help of `native-image-maven-plugin` and `spring-aot-maven-plugin`.

Can be run using `docker-compose up graalvm-native-service`

## Jar Service

`./jar/build-jar-image.sh`

This generates a new docker image using `eclipse-temurin:17` as base which is running a `.jar` with this service.

Can be run using `docker-compose up temurin-service`

## GraalVM JIT Service

`./graalvm-jit/build-jit-image.sh`

This generates a new docker image using `oraclelinux:8-slim` as base which is running a `.jar` using 
GraalVM JIT compilation mode.

Can be run using `docker-compose up graalvm-jit-service`

# Throughput test results

## Initial Test
* Sending requests upon VM startup
* 2 test cases:
  * Query 15 demos
  * Register a new demo
* Using 25 concurrent users for 2 minutes.

### Result
![initial-test-result](response-time-graph-initial.png)
* ~267 r/s for GraalVM JIT
* ~263 r/s for GraalVM native
* ~245 r/s for Temurin

On this test, either GraalVM performed ~10% faster than Temurin.

Temurin and GraalVM as they're using JIT they start much slower, however once the JIT optimizations
kick-in we can see their numbers improving. 



## Warmed Up Test
* Warm-up VM by sending requests for 5 minutes then run the tests.
* 2 test cases:
    * Query 15 demos
    * Register a new demo
* Using 25 concurrent users for 2 minutes.

### Result
![warmed-test-result](response-time-graph-warmed.png)
* ~284 r/s for GraalVM JIT
* ~228 r/s for Temurin
* ~220 r/s for GraalVM native


As both Temurin and GraalVM JIT are already warmed up they maintain their throughput don't
fluctuate as much as for the previous case. Also we can see that GraalVM JIT performs exceptionally
better.

Probably the most surprising fact on these tests is the small difference between Temurin and GraalVM 
native.