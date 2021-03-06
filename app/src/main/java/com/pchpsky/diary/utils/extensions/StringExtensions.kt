package com.pchpsky.diary.utils.extensions

fun String.toValidDouble(): Double? {
    return toDoubleOrNull() ?:
    when(true) {
        matches(Regex("^\\.$")) -> null
        matches(Regex("^\\d\\.$")) -> (this + "0").toDouble()
        matches(Regex("^\\.\\d+")) -> ("0$this").toDouble()
        else -> null
    }
}