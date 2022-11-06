package com.palone.paloneapp.data

sealed class ScreensProperties(val route: String, val name: String, val selected: Boolean = false) {
    object SubstitutionsScreen : ScreensProperties("substitutions_screen", "Zastępstwa", true)
    object TimetableScreen : ScreensProperties("timetable_screen", "Plan Lekcji")
}
