package com.palone.paloneapp.data.models.responses.substitutions

import com.google.gson.annotations.SerializedName

data class SubstitutionsRemoteDataResponse(
    @SerializedName("r")
    val response: String
)