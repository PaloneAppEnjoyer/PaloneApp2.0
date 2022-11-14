package com.palone.paloneapp.data

import com.palone.paloneapp.data.models.TimetableData
import com.palone.paloneapp.domain.UseCases

class TimetableDataProvider {
    suspend fun getTimetableData(filePath: String): List<TimetableData> {
        return try {
            val data = TimetableRemoteDataProvider().getRemoteTimetableData(
                TimetableRemoteDataProvider().getRemoteTtViewerData().response?.regular?.default_num?.toInt()
                    ?: 100
            )
            if (data.isNotEmpty())
                UseCases().saveTimetableDataToLocalJsonFile(
                    data,
                    filePath,
                    "latest_timetable_data.json"
                )
            UseCases().getTimetableDataFromLocalJsonFile(filePath, "latest_timetable_data.json")
        } catch (e: Exception) {
            listOf()
        }
    }
}