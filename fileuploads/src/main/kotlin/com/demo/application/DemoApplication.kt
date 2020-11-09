package com.demo.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.demo")
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}