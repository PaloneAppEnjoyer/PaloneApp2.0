package com.palone.paloneapp.utils

fun Int.between(from: Int, to: Int): Boolean {
    if (this in from..to)
        return true
    return false
}

