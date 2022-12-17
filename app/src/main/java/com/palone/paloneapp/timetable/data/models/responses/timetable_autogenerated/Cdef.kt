package com.palone.paloneapp.models.timetableLessonsResponseData

data class Cdef(
    val id: String,
    val name: String,
    val subcolumns: List<Subcolumn>,
    val table: String,
    val type: String
)