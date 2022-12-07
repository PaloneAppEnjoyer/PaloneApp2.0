package com.palone.paloneapp.models.timetableLessonsResponseData

data class Subcolumn(
    val id: String,
    val name: String,
    val subcolumns: List<SubcolumnX>,
    val type: String
)