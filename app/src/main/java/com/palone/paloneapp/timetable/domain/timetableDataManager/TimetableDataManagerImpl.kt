package com.palone.paloneapp.timetable.domain.timetableDataManager

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.palone.paloneapp.timetable.data.TimetableDataProvider
import com.palone.paloneapp.timetable.data.models.TimetableData
import com.palone.paloneapp.utils.timetableDataResponseToListOfTimetableDataParser.TimetableDataResponseToListOfTimetableDataParserInterface
import java.io.File
import java.io.FileReader

class TimetableDataManagerImpl : TimetableDataManager {
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