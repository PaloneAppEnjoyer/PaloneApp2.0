package com.palone.paloneapp.feature_screen_timetable.domain.timetableDataManager

import com.palone.paloneapp.domain.timetableDataResponseToListOfTimetableDataParser.TimetableDataResponseToListOfTimetableDataParserInterface
import com.palone.paloneapp.feature_screen_timetable.data.models.TimetableData

interface TimetableDataManagerInterface {
    suspend fun getTimetableData(
        filePath: String,
        timetableDataParser: TimetableDataResponseToListOfTimetableDataParserInterface
    ): List<TimetableData>

    fun saveTimetableDataToLocalJsonFile(
        timetableData: List<TimetableData>,
        filePath: String,
        fileName: String,
        onFinish: () -> Unit = {}
    )

    fun getTimetableDataFromLocalJsonFile(
        filePath: String,
        fileName: String,
    ): List<TimetableData>
}