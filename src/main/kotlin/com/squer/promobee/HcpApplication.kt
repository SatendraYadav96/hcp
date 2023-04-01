package com.squer.promobee

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class HcpApplication

fun main(args: Array<String>) {
	runApplication<HcpApplication>(*args)
}
