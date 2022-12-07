package com.palone.paloneapp.screen_timetable.data.models.timetableVariables

data class Lesson(
    val id: String,
    val subject: Subject,
    val teacher: List<Teacher>,
    val group: List<Group>,
    val schoolClass: List<SchoolClass>,
    val classRoom: List<ClassRoom>,
    val durationPeriod: Int,
)
