package com.palone.paloneapp.domain

import android.util.Log
import com.palone.paloneapp.data.SubstitutionsDataProvider
import com.palone.paloneapp.data.TimetableDataProvider
import com.palone.paloneapp.data.models.SubstitutionData
import com.palone.paloneapp.data.models.TimetableData
import com.palone.paloneapp.data.models.responses.ttviewer.TtViewerRemoteDataResponse
import kotlinx.datetime.LocalDate

class UseCases {

    suspend fun getSubstitutionsDataWithLocalDate(localDate: LocalDate): List<SubstitutionData> {
        return SubstitutionsDataProvider().getRemoteData(localDate)
    }

    suspend fun getTtViewerData(): TtViewerRemoteDataResponse {
        return TimetableDataProvider().getRemoteTtViewerData()
    }

    suspend fun getTimetableData(): List<TimetableData> {
        return TimetableDataProvider().getRemoteTimetableData(109)
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