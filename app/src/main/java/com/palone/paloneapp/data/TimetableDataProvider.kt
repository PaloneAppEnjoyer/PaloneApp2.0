package com.palone.paloneapp.data

import com.palone.paloneapp.data.models.TimetableData
import com.palone.paloneapp.data.models.responses.ttviewer.TtViewerRemoteDataResponse
import com.palone.paloneapp.data.network.RetrofitInstance
import com.palone.paloneapp.domain.Parsers
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*

class TimetableDataProvider {
    suspend fun getRemoteTtViewerData(): TtViewerRemoteDataResponse {
        return try {
            val data = RetrofitInstance.api.getTtViewerData(
                """{"__args":[null,${
                    Calendar.getInstance().get(Calendar.YEAR)
                }],"__gsh":"00000000"}""".toRequestBody()
            )
            data
        } catch (e: Exception) {
            TtViewerRemoteDataResponse(response = null)
        }

    }

    suspend fun getRemoteTimetableData(timetableVersion: Int): List<TimetableData> {
        val data =
            Parsers().getTimetableDataResponseParsedToListOfTimetableData(
                RetrofitInstance.api.getTimetableData(
                    """{"__args":[null,"$timetableVersion"],"__gsh":"00000000"}""".toRequestBody()
                )
            )
        return data


    }


}