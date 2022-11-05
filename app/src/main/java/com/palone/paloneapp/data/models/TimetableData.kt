package com.palone.paloneapp.data.models

data class TimetableData(
    val id: String,
    val subjectid: String,
    val teacherids: List<String>,
    val classids: List<String>,
    val durationperiods: Int,
    var subjectName: String? = null,
    val teachersShorted: MutableList<String?> = mutableListOf(),
    var className: MutableList<String?> = mutableListOf(),
    var day: String? = null,
    var lessonFrom: Int? = null,
    var lessonTo: Int? = null,
    var classroomsName: String? = null,
    val groupids: List<String>,
    val groupNames: MutableList<String?> = mutableListOf(),
    var dayInt: Int? = null
)