package com.palone.paloneapp.feature_screen_substitutions.domain.substitutionsDataManager

import android.util.Log
import com.palone.paloneapp.domain.htmlParser.HtmlParserInterface
import com.palone.paloneapp.feature_screen_substitutions.data.SubstitutionsDataProvider
import com.palone.paloneapp.feature_screen_substitutions.data.models.SubstitutionData
import kotlinx.datetime.LocalDate

class SubstitutionsDataManagerImpl : SubstitutionsDataManagerInterface {
    override fun getFilteredSubstitutionDataByQuery(
        data: List<SubstitutionData>,
        query: String
    ): List<SubstitutionData> {
        val sortedData: MutableList<SubstitutionData> = mutableListOf()
        data.forEach {
            Log.i("", "$it")
            if ((it.className.contains(query)))
                sortedData.add(it)
        }
        return sortedData
    }

    override suspend fun getSubstitutionsDataWithLocalDate(
        htmlParser: HtmlParserInterface,
        localDate: LocalDate
    ): List<SubstitutionData> {
        return SubstitutionsDataProvider().getRemoteData(htmlParser, localDate)
    }
}