package com.palone.paloneapp.data

import com.palone.paloneapp.data.models.TimetableData
import com.palone.paloneapp.data.models.responses.ttviewer.TtViewerRemoteDataResponse
import com.palone.paloneapp.data.network.RetrofitInstance
import com.palone.paloneapp.domain.Parsers
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*

class TimetableRemoteDataProvider {
    suspend fun getRemoteTtViewerData(): TtViewerRemoteDataResponse {
        return try {
            RetrofitInstance.api.getTtViewerData(
                """{"__args":[null,${
                    Calendar.getInstance().get(Calendar.YEAR)
                }],"__gsh":"00000000"}""".toRequestBody()
            )
        } catch (e: Exception) {
            TtViewerRemoteDataResponse(response = null)
        }

    }

    suspend fun getRemoteTimetableData(timetableVersion: Int): List<TimetableData> {
        return try {
            Parsers().getTimetableDataResponseParsedToListOfTimetableData(
                RetrofitInstance.api.getTimetableData(
                    """{"__args":[null,"$timetableVersion"],"__gsh":"00000000"}""".toRequestBody()
                )
            )
        } catch (e: Exception) {
            listOf()
        }
    }

}