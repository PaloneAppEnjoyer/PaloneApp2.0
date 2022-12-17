package com.palone.paloneapp.timetable.data

import com.palone.paloneapp.timetable.data.models.TimetableData
import com.palone.paloneapp.timetable.domain.timetableDataManager.TimetableDataManager
import com.palone.paloneapp.utils.timetableDataResponseToListOfTimetableDataParser.TimetableDataResponseToListOfTimetableDataParserInterface

class TimetableDataProvider {
    suspend fun getTimetableData(
        filePath: String,
        timetableDataManager: TimetableDataManager,
        timetableDataParser: TimetableDataResponseToListOfTimetableDataParserInterface
    ): List<TimetableData> {
        return try {
            val data = TimetableRemoteDataProvider().getRemoteTimetableData(
                timetableDataParser,
                TimetableRemoteDataProvider().getRemoteTtViewerData().response?.regular?.default_num?.toInt()
                    ?: 100
            )
            if (data.isNotEmpty())
                timetableDataManager.saveTimetableDataToLocalJsonFile(
                    data,
                    filePath,
                    "latest_timetable_data.json"
                )
            timetableDataManager.getTimetableDataFromLocalJsonFile(
                filePath,
                "latest_timetable_data.json"
            )
        } catch (e: Exception) {
            listOf()
        }
    }
}