package com.problem.pl.commons

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

object UniversalCommon {

    fun generateDBId(): String = UUID.randomUUID().toString().replace("-","")

    fun generateTimestamp() : Long = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()


}