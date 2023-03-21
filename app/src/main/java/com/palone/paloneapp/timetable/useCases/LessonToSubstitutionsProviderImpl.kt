package com.palone.paloneapp.timetable.useCases

import com.palone.paloneapp.substitutions.data.models.SubstitutionData
import com.palone.paloneapp.substitutions.data.models.SubstitutionDataEntry
import com.palone.paloneapp.utils.between

class LessonToSubstitutionsProviderImpl : LessonToSubstitutionsProvider {
    //todo refactor pls bo oczy bolą
    override fun getSubstitutionsFromLessonNumber(
        lessonNumber: Int,
        substitutionsList: List<SubstitutionData>,
        selectedSchoolClass: String
    ): MutableList<SubstitutionDataEntry> {
        val substitutionsForLesson = mutableListOf<SubstitutionDataEntry>()
        substitutionsList.forEach {
            if (it.className == selectedSchoolClass)
                it.entries.forEach { it2 ->
                    if (when (it2.lessons.replace(" ", "").replace("(", "")
                            .replace(")", "").replace("\n", "").length) {
                            3 -> lessonNumber.between(
                                it2.lessons.replace(" ", "").replace("(", "")
                                    .replace(")", "").replace("\n", "")[0].toString().toInt(),
                                it2.lessons.replace(" ", "").replace("(", "")
                                    .replace(")", "").replace("\n", "")[2].toString().toInt()
                            )
                            1 -> it2.lessons.replace(" ", "").replace("(", "")
                                .replace(")", "").replace("\n", "")[0].toString()
                                .toInt() == lessonNumber
                            else -> it2.lessons.replace(" ", "").replace("(", "")
                                .replace(")", "").replace("\n", "") == "Cały dzień"
                        }
                    ) substitutionsForLesson.add(it2)
                }
        }
        return substitutionsForLesson

    }
}