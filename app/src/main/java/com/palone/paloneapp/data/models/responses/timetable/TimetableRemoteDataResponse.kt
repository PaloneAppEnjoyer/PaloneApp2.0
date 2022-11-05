package com.palone.paloneapp.data.models.responses.timetable

import com.google.gson.annotations.SerializedName
import com.palone.paloneapp.models.timetableLessonsResponseData.Ce
import com.palone.paloneapp.models.timetableLessonsResponseData.R

data class TimetableRemoteDataResponse(
    val ce: Ce,
    @SerializedName("r")
    val response: R
)