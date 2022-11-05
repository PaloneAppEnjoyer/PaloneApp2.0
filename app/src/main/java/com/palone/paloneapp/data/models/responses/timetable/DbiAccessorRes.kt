package com.palone.paloneapp.models.timetableLessonsResponseData

data class DbiAccessorRes(
    val changeEvents: ChangeEventsX,
    val dbid: String,
    val tables: List<Table>,
    val type: String
)