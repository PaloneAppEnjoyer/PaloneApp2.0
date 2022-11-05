package com.palone.paloneapp.data.models.responses.ttviewer

data class Timetable(
    val datefrom: String,
    val hidden: Boolean,
    val text: String,
    val tt_num: String,
    val year: Int
)