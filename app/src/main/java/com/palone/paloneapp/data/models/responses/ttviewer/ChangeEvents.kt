package com.palone.paloneapp.data.models.responses.ttviewer

import com.google.gson.annotations.SerializedName

data class ChangeEvents(
    @SerializedName(value = "dbi:global_settings")
    val dbi_global_settings: Int
)