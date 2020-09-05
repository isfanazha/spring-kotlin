package com.katalis.branchapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BranchApiApplication

fun main(args: Array<String>) {
	runApplication<BranchApiApplication>(*args)
}
