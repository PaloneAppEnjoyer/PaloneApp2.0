package com.palone.paloneapp.feature_screen_substitutions.domain.substitutionsDataManager

import com.palone.paloneapp.domain.htmlParser.HtmlParserInterface
import com.palone.paloneapp.feature_screen_substitutions.data.models.SubstitutionData
import kotlinx.datetime.LocalDate

interface SubstitutionsDataManagerInterface {
    fun getFilteredSubstitutionDataByQuery(
        data: List<SubstitutionData>,
        query: String
    ): List<SubstitutionData>

    suspend fun getSubstitutionsDataWithLocalDate(
        htmlParser: HtmlParserInterface,
        localDate: LocalDate
    ): List<SubstitutionData>
}