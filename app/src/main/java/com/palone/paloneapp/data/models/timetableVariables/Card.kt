package com.palone.paloneapp.data.models.timetableVariables

data class Card(
    val id: String,
    val lesson: Lesson,
    val period: Int,
    val day: DaysDef,
    val classRooms: List<ClassRoom>,
)
