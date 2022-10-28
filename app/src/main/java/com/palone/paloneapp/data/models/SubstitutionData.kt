package com.palone.paloneapp.data.models

data class SubstitutionData(
    val className: String? = null,
    val entries: MutableList<SubstitutionDataEntries>? = null
)

data class SubstitutionDataEntries(
    val lessons: String?,
    val subject: String?,
    val teacherReplacement: String?,
    val roomChange: String?
)
