package com.palone.paloneapp.data.network

import com.palone.paloneapp.data.models.responses.substitutions.SubstitutionsRemoteDataResponse
import com.palone.paloneapp.data.models.responses.timetable.TimetableRemoteDataResponse
import com.palone.paloneapp.data.models.responses.ttviewer.TtViewerRemoteDataResponse
import retrofit2.http.POST

interface OnlineResourcesApi {
    @POST("/substitution/server/viewer.js?__func=getSubstViewerDayDataHtml")
    suspend fun getSubstitutions(): SubstitutionsRemoteDataResponse

    @POST("/timetable/server/ttviewer.js?__func=getTTViewerData")
    suspend fun getTtViewerData(): TtViewerRemoteDataResponse

    @POST("/timetable/server/regulartt.js?__func=regularttGetData")
    suspend fun getTimetableData(): TimetableRemoteDataResponse
}