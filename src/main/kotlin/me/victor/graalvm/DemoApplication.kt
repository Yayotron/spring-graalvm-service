package me.victor.graalvm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(proxyBeanMethods = false)
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}