package com.palone.paloneapp.models.timetableLessonsResponseData

data class Settings(
    val draft_options: DraftOptions,
    val m_DozoryKriteria: MDozoryKriteria,
    val m_bAllowZlavnenie: Boolean,
    val m_bGenerDraft: Boolean,
    val m_bPrintDayAsNumber: Boolean,
    val m_bPrintDoublesAsSingles: Boolean,
    val m_bPrintDozory: Boolean,
    val m_bPrintDozoryColor: Boolean,
    val m_bPrintDozoryVSuhrnnych: Boolean,
    val m_bPrintSinglesSpolu: Boolean,
    val m_nCoGenerovat: Int,
    val m_nGapsCounting: Int,
    val m_nPrvyDen: Int,
    val m_nSchoolType: Int,
    val m_nSirkaCiaryDen: Int,
    val m_nSirkaCiaryLesson: Int,
    val m_nSirkaCiaryOkraj: Int,
    val m_nTimeFormat: Int,
    val m_nZlozitostGener: Int,
    val m_strDateBellowTimeTable: String,
    val m_strPrintHeaderText: String,
    val name_format: String
)