package com.palone.paloneapp.domain

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.palone.paloneapp.data.SubstitutionsDataProvider
import com.palone.paloneapp.data.TimetableDataProvider
import com.palone.paloneapp.data.models.SubstitutionData
import com.palone.paloneapp.data.models.TimetableData
import com.palone.paloneapp.data.models.responses.ttviewer.TtViewerRemoteDataResponse
import kotlinx.datetime.LocalDate
import java.io.File
import java.io.FileReader

class UseCases {

    suspend fun getSubstitutionsDataWithLocalDate(localDate: LocalDate): List<SubstitutionData> {
        return SubstitutionsDataProvider().getRemoteData(localDate)
    }

    suspend fun getTtViewerData(): TtViewerRemoteDataResponse {
        return TimetableDataProvider().getRemoteTtViewerData()
    }

    suspend fun getTimetableData(timetableVersion: Int): List<TimetableData> {
        return TimetableDataProvider().getRemoteTimetableData(timetableVersion)
    }

    fun saveTimetableDataToLocalJsonFile(
        timetableData: List<TimetableData>,
        filePath: String,
        fileName: String,
        onFinish: () -> Unit = {}
    ) {
        val file = File(filePath + fileName)
        file.writeText(Gson().toJson(timetableData))
        onFinish()
    }

    fun getTimetableDataFromLocalJsonFile(
        filePath: String,
        fileName: String,
    ): List<TimetableData> {
        val listType = object : TypeToken<List<TimetableData>>() {}.type
        return Gson().fromJson(FileReader(filePath + fileName), listType)
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