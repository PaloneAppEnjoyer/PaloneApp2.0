package com.palone.paloneapp.models.timetableLessonsResponseData

data class Table(
    val cdefs: List<Cdef>,
    val data_columns: List<String>,
    val data_rows: List<DataRow>,
    val def: Def,
    val id: String
)