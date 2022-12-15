package com.palone.paloneapp.substitutions.domain.substitutionsDataManager

import android.util.Log
import com.palone.paloneapp.substitutions.data.SubstitutionsDataProvider
import com.palone.paloneapp.substitutions.data.models.SubstitutionData
import com.palone.paloneapp.utils.htmlParser.HtmlParserInterface
import kotlinx.datetime.LocalDate

class SubstitutionsDataManagerImpl : SubstitutionsDataManager {
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