package com.palone.paloneapp.data

import android.util.Log
import com.palone.paloneapp.data.models.TimetableData
import com.palone.paloneapp.data.models.responses.ttviewer.TtViewerRemoteDataResponse
import com.palone.paloneapp.data.network.RetrofitInstance
import com.palone.paloneapp.domain.Parsers
import java.util.*

class TimetableDataProvider {
    suspend fun getRemoteTtViewerData(): TtViewerRemoteDataResponse {
        try {
            RetrofitInstance.setBody(
                """{"__args":[null,${
                    Calendar.getInstance().get(Calendar.YEAR)
                }],"__gsh":"00000000"}"""
            )
            return RetrofitInstance.api.getTtViewerData()
        } catch (e: Exception) {
            return TtViewerRemoteDataResponse(response = null)
        }

    }

    suspend fun getRemoteTimetableData(timetableVersion: Int): List<TimetableData> {

        RetrofitInstance.setBody(
            """{"__args":[null,"$timetableVersion"],"__gsh":"00000000"}"""
        )
        val data =
            Parsers().getTimetableDataResponseParsedToListOfTimetableData(RetrofitInstance.api.getTimetableData())
        Log.i("halo", data.toString())
        return data


    }


}