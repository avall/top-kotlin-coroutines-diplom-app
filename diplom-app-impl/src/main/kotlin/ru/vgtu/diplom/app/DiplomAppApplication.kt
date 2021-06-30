package ru.vgtu.diplom.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DiplomAppApplication

fun main(args: Array<String>) {
    runApplication<DiplomAppApplication>(*args)
}
