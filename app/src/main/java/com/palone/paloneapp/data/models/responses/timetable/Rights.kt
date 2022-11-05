package com.palone.paloneapp.models.timetableLessonsResponseData

data class Rights(
    val classes: Boolean,
    val classes_summary: Boolean,
    val classrooms: Boolean,
    val classrooms_summary: Boolean,
    val classroomsupervision: Boolean,
    val igroups: Boolean,
    val igroups_summary: Boolean,
    val students: Boolean,
    val subjects: Boolean,
    val teachers: Boolean,
    val teachers_summary: Boolean
)