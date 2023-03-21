package com.palone.paloneapp.timetable.data

import android.util.Log
import com.palone.paloneapp.timetable.data.models.TimetableData
import com.palone.paloneapp.timetable.domain.timetableDataManager.TimetableDataManager
import com.palone.paloneapp.utils.timetableDataResponseToListOfTimetableDataParser.TimetableDataResponseToListOfTimetableDataParserInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TimetableDataProvider {
    suspend fun getTimetableData(
        filePath: String,
        timetableDataManager: TimetableDataManager,
        timetableDataParser: TimetableDataResponseToListOfTimetableDataParserInterface
    ): Flow<List<TimetableData>> =
        flow {
            try {
                Log.i("Emiting json", "hyh")
                emit(
                    timetableDataManager.getTimetableDataFromLocalJsonFile(
                        filePath,
                        "latest_timetable_data.json"
                    )
                )
                val data = TimetableRemoteDataProvider().getRemoteTimetableData(
                    timetableDataParser,
                    TimetableRemoteDataProvider().getRemoteTtViewerData().response?.regular?.default_num?.toInt()
                        ?: 100
                )
                if (data.isNotEmpty()) {
                    timetableDataManager.saveTimetableDataToLocalJsonFile(
                        data,
                        filePath,
                        "latest_timetable_data.json"
                    )
                    Log.i("Emiting po update", "hyh")
                    emit(
                        timetableDataManager.getTimetableDataFromLocalJsonFile(
                            filePath,
                            "latest_timetable_data.json"
                        )
                    )
                }

            } catch (e: Exception) {
                emit(emptyList())
            }
        }
}
