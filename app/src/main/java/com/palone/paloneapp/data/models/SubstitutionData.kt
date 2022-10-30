package com.palone.paloneapp.data.models

data class SubstitutionData(
    val className: String? = null,
    val entries: List<SubstitutionDataEntry>? = null
)

data class SubstitutionDataEntry(
    val lessons: String? = null,
    val subject: String? = null,
    val teacherReplacement: String? = null,
    val roomChange: String? = null
)
