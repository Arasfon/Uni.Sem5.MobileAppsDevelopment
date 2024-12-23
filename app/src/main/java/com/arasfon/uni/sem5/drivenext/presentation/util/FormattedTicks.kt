package com.arasfon.uni.sem5.drivenext.presentation.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formattedTicks(ticks: Long): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm, dd MMMM yyyy")
        .withZone(ZoneId.systemDefault())

    val instant = Instant.ofEpochMilli(ticks)
    return formatter.format(instant)
}

fun formattedTicksDate(ticks: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        .withZone(ZoneId.systemDefault())

    val instant = Instant.ofEpochMilli(ticks)
    return formatter.format(instant)
}
