package com.katalis.branchapi.utils

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun getTimeNow(): String {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
}