package com.palone.paloneapp.data.models.BodyRequests.substitutionsrequestbody

import com.google.gson.annotations.SerializedName

data class SubstitutionsRequestBody(
    @SerializedName("")
    val n: String? = null,
    val __args: List<Arg>,
    val __gsh: String
)