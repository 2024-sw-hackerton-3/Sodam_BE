package org.example.sodam

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SodamBeApplication

fun main(args: Array<String>) {
    runApplication<SodamBeApplication>(*args)
}
