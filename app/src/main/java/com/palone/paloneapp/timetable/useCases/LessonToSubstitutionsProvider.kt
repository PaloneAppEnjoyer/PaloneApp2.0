package com.palone.paloneapp.timetable.useCases

import com.palone.paloneapp.substitutions.data.models.SubstitutionData
import com.palone.paloneapp.substitutions.data.models.SubstitutionDataEntry

fun interface LessonToSubstitutionsProvider {
    fun getSubstitutionsFromLessonNumber(
        lessonNumber: Int,
        substitutionsList: List<SubstitutionData>,
        selectedSchoolClass: String
    ): MutableList<SubstitutionDataEntry>
}