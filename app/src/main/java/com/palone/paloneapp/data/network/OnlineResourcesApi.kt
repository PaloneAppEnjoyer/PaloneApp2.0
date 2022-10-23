package com.palone.paloneapp.data.network

import com.palone.paloneapp.data.models.SubstitutionsResponse
import retrofit2.http.POST

interface OnlineResourcesApi {
    @POST("/substitution/server/viewer.js?__func=getSubstViewerDayDataHtml")
    suspend fun getSubstitutions(): SubstitutionsResponse
}