package me.victor.graalvm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.annotation.SynthesizedAnnotation
import org.springframework.nativex.hint.JdkProxyHint
import javax.inject.Qualifier

@SpringBootApplication(proxyBeanMethods = false)
@JdkProxyHint(types = [Qualifier::class, SynthesizedAnnotation::class])
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}