package com.palone.paloneapp.substitutions.domain.substitutionsDataManager

import com.palone.paloneapp.substitutions.data.models.SubstitutionData
import com.palone.paloneapp.utils.htmlParser.HtmlParserInterface
import kotlinx.datetime.LocalDate

interface SubstitutionsDataManager {
    fun getFilteredSubstitutionDataByQuery(
        data: List<SubstitutionData>,
        query: String
    ): List<SubstitutionData>

    suspend fun getSubstitutionsDataWithLocalDate(
        htmlParser: HtmlParserInterface,
        localDate: LocalDate
    ): List<SubstitutionData>
}