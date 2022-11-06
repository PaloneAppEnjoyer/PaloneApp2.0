package com.palone.paloneapp.data.models

data class TimetableData(val className: String, val entries: List<TimetableDataEntry>)
data class TimetableDataEntry(
    val subjectShortName: String,
    val classroomsName: String,
    val teacherShortName: String,
    val schoolClassNames: List<String>,
    val duration: Int,
    var dayName: String,
    var lessonFrom: Int,
    var lessonTo: Int,
    val groupName: String,
    var dayInt: Int
)