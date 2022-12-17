package com.palone.paloneapp.substitutions.data.models

data class SubstitutionData(
    val className: String = "",
    val entries: List<SubstitutionDataEntry>
)

data class SubstitutionDataEntry(
    val lessons: String = "",
    val subject: String = "",
    val teacherReplacement: String = "",
    val roomChange: String = ""
)
