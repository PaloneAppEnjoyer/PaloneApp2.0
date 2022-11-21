package com.palone.paloneapp.domain.timetableDataResponseToListOfTimetableDataParser

import com.palone.paloneapp.feature_screen_timetable.data.models.TimetableData
import com.palone.paloneapp.feature_screen_timetable.data.models.responses.timetable_autogenerated.TimetableRemoteDataResponse

interface TimetableDataResponseToListOfTimetableDataParserInterface {
    fun getTimetableDataResponseParsedToListOfTimetableData(dataResponse: TimetableRemoteDataResponse): List<TimetableData>
}