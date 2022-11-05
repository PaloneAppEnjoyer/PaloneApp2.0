package com.palone.paloneapp.domain

import android.util.Log
import com.palone.paloneapp.data.SubstitutionsDataProvider
import com.palone.paloneapp.data.models.SubstitutionData
import kotlinx.datetime.LocalDate

class UseCases {

    suspend fun getSubstitutionsDataWithLocalDate(localDate: LocalDate): List<SubstitutionData> {
        return SubstitutionsDataProvider().getRemoteData(localDate)
    }

    fun getFilteredSubstitutionDataByQuery(
        data: List<SubstitutionData>,
        query: String
    ): List<SubstitutionData> {
        val sortedData: MutableList<SubstitutionData> = mutableListOf()
        data.forEach {
            Log.i("", "$it")
            if ((it.className.contains(query) == true))
                sortedData.add(it)
        }
        return sortedData
    }


}