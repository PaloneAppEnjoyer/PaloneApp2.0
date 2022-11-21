package com.palone.paloneapp.domain

fun Int.isBetween(from: Int, to: Int): Boolean {
    if (this in from..to)
        return true
    return false
}

