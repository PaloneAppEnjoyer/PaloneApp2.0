package com.palone.paloneapp.feature_screen_timetable.domain.timetableDataManager

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.palone.paloneapp.domain.timetableDataResponseToListOfTimetableDataParser.TimetableDataResponseToListOfTimetableDataParserInterface
import com.palone.paloneapp.feature_screen_timetable.data.TimetableDataProvider
import com.palone.paloneapp.feature_screen_timetable.data.models.TimetableData
import java.io.File
import java.io.FileReader

class TimetableDataManagerImpl : TimetableDataManagerInterface {
    override suspend fun getTimetableData(
        filePath: String,
        timetableDataParser: TimetableDataResponseToListOfTimetableDataParserInterface
    ): List<TimetableData> {
        return TimetableDataProvider().getTimetableData(filePath, this, timetableDataParser)
    }

    override fun saveTimetableDataToLocalJsonFile(
        timetableData: List<TimetableData>,
        filePath: String,
        fileName: String,
        onFinish: () -> Unit
    ) {
        val file = File(filePath + fileName)
        file.writeText(Gson().toJson(timetableData))
        onFinish()
    }

    override fun getTimetableDataFromLocalJsonFile(
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
}