package com.palone.paloneapp.domain

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.palone.paloneapp.data.SubstitutionsDataProvider
import com.palone.paloneapp.data.TimetableDataProvider
import com.palone.paloneapp.data.models.SubstitutionData
import com.palone.paloneapp.data.models.TimetableData
import kotlinx.datetime.LocalDate
import java.io.File
import java.io.FileReader

class UseCases {

    suspend fun getSubstitutionsDataWithLocalDate(localDate: LocalDate): List<SubstitutionData> {
        return SubstitutionsDataProvider().getRemoteData(localDate)
    }

    suspend fun getTimetableData(filePath: String): List<TimetableData> {
        return TimetableDataProvider().getTimetableData(filePath)
    }

    fun between(value: Int, from: Int?, to: Int?): Boolean {
        if (from == null) {
            return false
        }
        if (to == null) {
            return true
        }
        if (value >= from && value <= to)
            return true
        return false
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
        return try {
            val listType = object : TypeToken<List<TimetableData>>() {}.type
            Gson().fromJson(FileReader(filePath + fileName), listType)
        } catch (e: Exception) {
            listOf()
        }
    }


    fun getFilteredSubstitutionDataByQuery(
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


}