package com.palone.paloneapp.feature_screen_timetable.data.models

data class TimetableData(val className: String, val day: List<TimetableDay>)
data class TimetableDay(val dayNameShorted: String, val lessons: List<TimetableLessons>)
data class TimetableLessons(val lessonNumber: Int, val entries: List<TimetableDataEntry>)
data class TimetableDataEntry(
    val subjectShortName: String,
    val subjectName: String,
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