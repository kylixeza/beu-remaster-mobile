package com.kylix.history.util

import java.text.SimpleDateFormat
import java.util.TimeZone

fun String.toGMT8Format(): String {
    val sdf = SimpleDateFormat("d MMM yyyy, HH:mm")
    sdf.timeZone = TimeZone.getTimeZone("GMT")
    val date = sdf.parse(this)

    val sdfOutput = SimpleDateFormat("d MMM yyyy, HH:mm")
    sdfOutput.timeZone = TimeZone.getTimeZone("GMT+8")

    return sdfOutput.format(date)
}