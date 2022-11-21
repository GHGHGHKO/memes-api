package me.synology.memesapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemesApiApplication

fun main(args: Array<String>) {
	runApplication<MemesApiApplication>(*args)
}
