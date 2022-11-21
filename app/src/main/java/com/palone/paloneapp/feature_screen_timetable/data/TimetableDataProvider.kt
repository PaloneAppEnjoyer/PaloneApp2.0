package com.palone.paloneapp.feature_screen_timetable.data

import com.palone.paloneapp.domain.timetableDataResponseToListOfTimetableDataParser.TimetableDataResponseToListOfTimetableDataParserInterface
import com.palone.paloneapp.feature_screen_timetable.data.models.TimetableData
import com.palone.paloneapp.feature_screen_timetable.domain.timetableDataManager.TimetableDataManagerInterface

class TimetableDataProvider {
    suspend fun getTimetableData(
        filePath: String,
        timetableDataManager: TimetableDataManagerInterface,
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