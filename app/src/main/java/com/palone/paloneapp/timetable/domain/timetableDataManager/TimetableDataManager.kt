package com.palone.paloneapp.timetable.domain.timetableDataManager

import com.palone.paloneapp.timetable.data.models.TimetableData
import com.palone.paloneapp.utils.timetableDataResponseToListOfTimetableDataParser.TimetableDataResponseToListOfTimetableDataParserInterface
import kotlinx.coroutines.flow.Flow

interface TimetableDataManager {
    suspend fun getTimetableData(
        filePath: String,
        timetableDataParser: TimetableDataResponseToListOfTimetableDataParserInterface
    ): Flow<List<TimetableData>>

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