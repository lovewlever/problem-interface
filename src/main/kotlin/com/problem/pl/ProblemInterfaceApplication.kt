package com.problem.pl

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@MapperScan("com.problem.pl.model.dao")
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ProblemInterfaceApplication

fun main(args: Array<String>) {
    runApplication<ProblemInterfaceApplication>(*args)
}
