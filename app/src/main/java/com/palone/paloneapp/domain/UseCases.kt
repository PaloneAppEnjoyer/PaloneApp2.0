package com.palone.paloneapp.domain

import com.palone.paloneapp.data.SubstitutionsDataProvider
import com.palone.paloneapp.data.models.SubstitutionData
import kotlinx.datetime.LocalDate

class UseCases {

    suspend fun getSubstitutionsDataWithLocalDate(localDate: LocalDate): List<SubstitutionData> {
        return SubstitutionsDataProvider().getRemoteData(localDate)
    }
}