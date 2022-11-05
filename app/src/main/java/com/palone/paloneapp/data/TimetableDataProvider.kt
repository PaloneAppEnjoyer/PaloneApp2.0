package com.palone.paloneapp.data

import com.palone.paloneapp.data.models.responses.ttviewer.TtViewerRemoteDataResponse
import com.palone.paloneapp.data.network.RetrofitInstance
import java.util.*

class TimetableDataProvider {
    suspend fun getRemoteTtViewerData(): TtViewerRemoteDataResponse {
        RetrofitInstance.setBody(
            """{"__args":[null,${Calendar.getInstance().get(Calendar.YEAR)}],"__gsh":"00000000"}"""
        )
        return RetrofitInstance.api.getTtViewerData()
    }
}